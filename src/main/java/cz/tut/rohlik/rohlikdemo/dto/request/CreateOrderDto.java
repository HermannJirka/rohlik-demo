package cz.tut.rohlik.rohlikdemo.dto.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class CreateOrderDto {
    @NotNull
    public String itemId;
    @NotNull
    public Integer itemCount;
}
