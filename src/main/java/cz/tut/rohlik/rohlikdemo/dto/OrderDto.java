package cz.tut.rohlik.rohlikdemo.dto;

import cz.tut.rohlik.rohlikdemo.model.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class OrderDto {
    String id;
    OrderStatus status;
    BigDecimal priceSum;
    List<OrderItemDto> items;
}
