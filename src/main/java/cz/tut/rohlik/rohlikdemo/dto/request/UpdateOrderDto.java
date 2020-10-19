package cz.tut.rohlik.rohlikdemo.dto.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateOrderDto {
    String itemId;
    Integer count;
}
