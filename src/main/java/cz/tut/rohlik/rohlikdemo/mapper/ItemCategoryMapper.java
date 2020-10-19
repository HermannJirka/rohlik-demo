package cz.tut.rohlik.rohlikdemo.mapper;

import cz.tut.rohlik.rohlikdemo.dto.ItemCategoryDto;
import cz.tut.rohlik.rohlikdemo.dto.request.CreateItemCategoryDto;
import cz.tut.rohlik.rohlikdemo.dto.request.UpdateItemCategoryDto;
import cz.tut.rohlik.rohlikdemo.model.ItemCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ItemCategoryMapper {
    ItemCategoryMapper MAPPER = Mappers.getMapper(ItemCategoryMapper.class);

    @Mapping(target = "items", ignore = true)
    ItemCategory toItemCategory(CreateItemCategoryDto createItemCategoryDto);

    ItemCategoryDto toItemCategoryDto(ItemCategory itemCategory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    ItemCategory updateItemCategory(@MappingTarget ItemCategory item, UpdateItemCategoryDto updateItemDto);
}
