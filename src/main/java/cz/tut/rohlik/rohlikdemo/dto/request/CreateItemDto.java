package cz.tut.rohlik.rohlikdemo.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateItemDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer count;
    private Boolean deleted;
    private String itemCategory;
}
