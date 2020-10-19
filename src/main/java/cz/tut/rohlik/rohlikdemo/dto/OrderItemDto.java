package cz.tut.rohlik.rohlikdemo.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class OrderItemDto {
    String id;
    String itemName;
    String itemDescription;
    Integer count;
    BigDecimal itemPrice;
    BigDecimal itemPriceSum;
}
