package cz.tut.rohlik.rohlikdemo.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemCategoryDto {
    String id;
    String name;
}
