package cz.tut.rohlik.rohlikdemo.service;

import cz.tut.rohlik.rohlikdemo.dto.OrderDto;
import cz.tut.rohlik.rohlikdemo.dto.request.CreateOrderDto;
import cz.tut.rohlik.rohlikdemo.dto.request.UpdateOrderDto;
import cz.tut.rohlik.rohlikdemo.exceptions.CancelOrderException;
import cz.tut.rohlik.rohlikdemo.exceptions.ItemCountException;
import cz.tut.rohlik.rohlikdemo.exceptions.NotFoundException;
import cz.tut.rohlik.rohlikdemo.model.Item;
import cz.tut.rohlik.rohlikdemo.model.Order;
import cz.tut.rohlik.rohlikdemo.model.OrderItem;
import cz.tut.rohlik.rohlikdemo.model.OrderStatus;
import cz.tut.rohlik.rohlikdemo.repository.ItemRepository;
import cz.tut.rohlik.rohlikdemo.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cz.tut.rohlik.rohlikdemo.mapper.OrderMapper.MAPPER;

@Service
@Log4j2
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public OrderService(OrderRepository orderRepository,
                        ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    public OrderDto getOrder(String id) {
        Order order = orderRepository.findByIdAndDeletedIsFalse(id)
                                     .orElseThrow(() -> new NotFoundException("Order not found!"));

        checkAndSetCancelOrder(order);

        return MAPPER.toOrderDto(order);
    }

    public OrderDto createOrder(List<CreateOrderDto> createOrderDtos) {
        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        BigDecimal priceSum = BigDecimal.ZERO;

        List<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderDto createOrderDto : createOrderDtos) {
            Item item = itemRepository.findById(createOrderDto.getItemId())
                                      .orElseThrow(() -> new NotFoundException("Item not found!"));
            OrderItem orderItem = new OrderItem();
            if (item.getCount() < createOrderDto.getItemCount()) {
                throw new ItemCountException("Item has smaller capacity than count in request!");
            }
            orderItem.setCount(createOrderDto.getItemCount());
            orderItem.setItem(item);
            item.setCount(item.getCount() - createOrderDto.getItemCount());
            orderItem.setOrder(order);
            orderItem.setItemPrice(item.getPrice());
            orderItem.setItemPriceSum(item.getPrice()
                                          .multiply(BigDecimal.valueOf(createOrderDto.getItemCount())));
            priceSum = priceSum.add(orderItem.getItemPriceSum());
            orderItems.add(orderItem);
            item.setCount(item.getCount() - createOrderDto.getItemCount());
        }
        order.setItems(orderItems);
        order.setPriceSum(priceSum);
        Order updatedOrder = orderRepository.save(order);

        return MAPPER.toOrderDto(updatedOrder);
    }

    public OrderDto updateOrderItems(String id, List<UpdateOrderDto> items) {
        Order order = orderRepository.findByIdAndDeletedIsFalse(id)
                                     .orElseThrow(() -> new NotFoundException("Order not found!"));


        Map<String, OrderItem> orderItemMap = order.getItems().stream().collect(Collectors.toMap(
                oi -> oi.getItem().getId(),
                oi -> oi
        ));

        BigDecimal priceSum = BigDecimal.ZERO;
        List<OrderItem> updatedItems = new ArrayList<>();
        priceSum = updateOrderItems(items, order, orderItemMap, priceSum, updatedItems);

        order.setPriceSum(priceSum);
        order.updateItems(updatedItems);
        Order savedOrder = orderRepository.save(order);

        return MAPPER.toOrderDto(savedOrder);
    }

    private BigDecimal updateOrderItems(List<UpdateOrderDto> items,
                                        Order order,
                                        Map<String, OrderItem> orderItemMap,
                                        BigDecimal priceSum,
                                        List<OrderItem> updatedItems) {
        for (UpdateOrderDto updateOrderDto : items) {
            OrderItem orderItem;
            Item item = itemRepository.findById(updateOrderDto.getItemId())
                                      .orElseThrow(() -> new NotFoundException("Item not found!"));
            if (orderItemMap.containsKey(updateOrderDto.getItemId())) {

                orderItem = orderItemMap.get(updateOrderDto.getItemId());
                checkItemCapacity(updateOrderDto.getCount(), item.getCount());
                orderItem.setCount(updateOrderDto.getCount());
                orderItem.setItemPriceSum(orderItem.getItemPrice()
                                                   .multiply(BigDecimal.valueOf(updateOrderDto.getCount())));
            } else {
                orderItem = new OrderItem();

                checkItemCapacity(updateOrderDto.getCount(), item.getCount());

                orderItem.setItem(item);
                orderItem.setOrder(order);
                orderItem.setItemPriceSum(item.getPrice()
                                              .multiply(BigDecimal.valueOf(updateOrderDto.getCount())));
                orderItem.setItemPrice(item.getPrice());

            }
            priceSum = priceSum.add(orderItem.getItemPriceSum());
            item.setCount(item.getCount() - updateOrderDto.getCount());
            itemRepository.save(item);
            updatedItems.add(orderItem);
        }
        return priceSum;
    }

    private void checkItemCapacity(Integer count, Integer itemCount) {
        if (itemCount < count) {
            throw new ItemCountException("Item has smaller capacity than count in " +
                    "request!");
        }
    }

    public void completeOrder(String id) {
        Order order = orderRepository.findByIdAndDeletedIsFalse(id)
                                     .orElseThrow(() -> new NotFoundException("Order not found!"));

        checkAndSetCancelOrder(order);

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }

    private void checkAndSetCancelOrder(Order order) {
        Duration duration = getDuration(order);
        if (duration.toMinutes() > 30) {
            order.setStatus(OrderStatus.CANCELED);
            orderRepository.save(order);
            throw new CancelOrderException("Order has been canceled!");
        }
    }

    private Duration getDuration(Order order) {
        LocalDateTime actualDate = LocalDateTime.ofInstant(
                new Date().toInstant(),
                ZoneId.systemDefault()
        );

        LocalDateTime createdAtDate =
                LocalDateTime.ofInstant(order.getCreatedAt().toInstant(), ZoneId.systemDefault());

        return Duration.between(createdAtDate, actualDate);
    }

    public void cancelOrder(String id) {
        Order order = orderRepository.findByIdAndDeletedIsFalse(id)
                                     .orElseThrow(() -> new NotFoundException("Order not found!"));
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    @Scheduled(fixedRate = 600000)
    public void schedulerTask() {
        List<Order> orders = orderRepository.findAllByDeletedIsFalse()
                                            .stream()
                                            .filter(order -> !OrderStatus.CANCELED.equals(order.getStatus()))
                                            .collect(
                                                    Collectors.toList());
        for (Order order : orders) {
            if (getDuration(order).toMinutes() > 30) {
                order.setStatus(OrderStatus.CANCELED);
                order.setDeleted(true);
            }
        }
        orderRepository.saveAll(orders);
    }

    public void deleteOrder(String id) {
        Order order = orderRepository.findByIdAndDeletedIsFalse(id)
                                     .orElseThrow(() -> new NotFoundException("Order not found!"));
        order.setDeleted(true);
        orderRepository.save(order);
    }
}
