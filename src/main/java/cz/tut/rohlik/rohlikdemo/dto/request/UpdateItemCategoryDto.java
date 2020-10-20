package cz.tut.rohlik.rohlikdemo.dto.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class UpdateItemCategoryDto {
    @NotNull
    String categoryName;
    String description;
}
