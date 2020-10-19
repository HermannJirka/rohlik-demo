package cz.tut.rohlik.rohlikdemo.controller;

import cz.tut.rohlik.rohlikdemo.dto.OrderDto;
import cz.tut.rohlik.rohlikdemo.dto.request.CreateOrderDto;
import cz.tut.rohlik.rohlikdemo.dto.request.UpdateOrderDto;
import cz.tut.rohlik.rohlikdemo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/{order_id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> getOrder(@PathVariable("order_id") String id) {
        return ok(orderService.getOrder(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces =
            APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> createOrder(@RequestBody List<CreateOrderDto> createOrderDto) {
        return ok(orderService.createOrder(createOrderDto));
    }

    @PutMapping(path = "/{order_id}", consumes = APPLICATION_JSON_VALUE,
                produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> updateOrderItems(@PathVariable("order_id") String id,
                                                     @RequestBody List<UpdateOrderDto> orders) {
        return ok(orderService.updateOrderItems(id, orders));
    }

    @PostMapping("/{order_id}/complete")
    public ResponseEntity<Void> completeOrder(@PathVariable("order_id") String id) {
        orderService.completeOrder(id);
        return ok().build();
    }

    @PostMapping("/{order_id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable("order_id") String id) {
        orderService.cancelOrder(id);
        return ok().build();
    }

    @DeleteMapping("/{order_id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("order_id") String id) {
        orderService.deleteOrder(id);
        return ok().build();
    }
}
