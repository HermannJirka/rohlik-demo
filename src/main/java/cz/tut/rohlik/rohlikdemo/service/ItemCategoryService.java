package cz.tut.rohlik.rohlikdemo.service;

import cz.tut.rohlik.rohlikdemo.dto.ItemCategoryDto;
import cz.tut.rohlik.rohlikdemo.dto.request.CreateItemCategoryDto;
import cz.tut.rohlik.rohlikdemo.dto.request.UpdateItemCategoryDto;
import cz.tut.rohlik.rohlikdemo.exceptions.NotFoundException;
import cz.tut.rohlik.rohlikdemo.model.ItemCategory;
import cz.tut.rohlik.rohlikdemo.repository.ItemCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static cz.tut.rohlik.rohlikdemo.mapper.ItemCategoryMapper.MAPPER;

@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public ItemCategoryDto addItemCategory(CreateItemCategoryDto request) {
        ItemCategory itemCategory = MAPPER.toItemCategory(request);
        ItemCategory savedItemCategory = itemCategoryRepository.save(itemCategory);
        return MAPPER.toItemCategoryDto(savedItemCategory);
    }

    public ItemCategoryDto updateItemCategory(String itemCategoryId,
                                              UpdateItemCategoryDto updateItemCategoryDto) {
        ItemCategory itemCategory = itemCategoryRepository.findById(itemCategoryId)
                                                          .orElseThrow(() -> new NotFoundException(
                                                                  "Item category not found"));
        MAPPER.updateItemCategory(itemCategory, updateItemCategoryDto);
        ItemCategory savedItemCategory = itemCategoryRepository.save(itemCategory);
        return MAPPER.toItemCategoryDto(savedItemCategory);
    }

    public ItemCategoryDto getItemCategory(String itemCategoryId) {
        ItemCategory itemCategory = itemCategoryRepository.findById(itemCategoryId)
                                                          .orElseThrow(() -> new NotFoundException(
                                                                  "Item category not found"));
        return MAPPER.toItemCategoryDto(itemCategory);
    }

    public List<ItemCategoryDto> getAllItemCategories() {
        return itemCategoryRepository.findAll()
                                     .stream()
                                     .map(MAPPER::toItemCategoryDto)
                                     .collect(Collectors.toList());
    }

    public void deleteItemCategory(String itemCategoryId) {
        itemCategoryRepository.deleteById(itemCategoryId);
    }
}

