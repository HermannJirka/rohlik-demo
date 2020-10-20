package cz.tut.rohlik.rohlikdemo.dto.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
@Builder
public class CreateItemDto {
    @NotNull
    String name;
    @NotNull
    String description;
    @NotNull
    BigDecimal price;
    @NotNull
    Integer count;
    @NotNull
    String itemCategory;
}
