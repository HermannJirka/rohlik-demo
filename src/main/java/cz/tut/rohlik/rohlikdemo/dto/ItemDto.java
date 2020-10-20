package cz.tut.rohlik.rohlikdemo.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ItemDto {
    String id;
    String name;
    String description;
    Integer count;
    BigDecimal price;
    String itemCategoryId;
}
