package cz.tut.rohlik.rohlikdemo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDto {
    private String id;
    private String name;
    private String description;
    private Integer count;
    private BigDecimal price;
    private String itemCategoryId;
}
