package cz.tut.rohlik.rohlikdemo.mapper;

import cz.tut.rohlik.rohlikdemo.dto.OrderDto;
import cz.tut.rohlik.rohlikdemo.dto.OrderItemDto;
import cz.tut.rohlik.rohlikdemo.model.Order;
import cz.tut.rohlik.rohlikdemo.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    OrderDto toOrderDto(Order order);
    @Mapping(target = "itemName",source = "item.name")
    @Mapping(target = "itemDescription",source = "item.description")
    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
