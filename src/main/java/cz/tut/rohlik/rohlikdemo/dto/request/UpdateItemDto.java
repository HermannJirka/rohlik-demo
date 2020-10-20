package cz.tut.rohlik.rohlikdemo.dto.request;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class UpdateItemDto {
    String id;
    String name;
    String description;
    BigDecimal price;
    Integer count;
    Boolean deleted;
    String itemCategory;
}
