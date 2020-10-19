package cz.tut.rohlik.rohlikdemo.mapper;

import cz.tut.rohlik.rohlikdemo.dto.ItemDto;
import cz.tut.rohlik.rohlikdemo.dto.request.CreateItemDto;
import cz.tut.rohlik.rohlikdemo.dto.request.UpdateItemDto;
import cz.tut.rohlik.rohlikdemo.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ItemMapper {
    ItemMapper MAPPER = Mappers.getMapper(ItemMapper.class);

    @Mapping(ignore = true, target = "itemCategory")
    Item toItem(CreateItemDto createItemDto);

    @Mapping(source = "itemCategory.id", target = "itemCategoryId")
    ItemDto toItemDto(Item item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itemCategory", ignore = true)
    Item updateItem(@MappingTarget Item item, UpdateItemDto updateItemDto);
}
