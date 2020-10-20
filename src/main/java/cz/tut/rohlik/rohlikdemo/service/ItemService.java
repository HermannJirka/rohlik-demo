package cz.tut.rohlik.rohlikdemo.service;

import cz.tut.rohlik.rohlikdemo.dto.ItemDto;
import cz.tut.rohlik.rohlikdemo.dto.PageableDto;
import cz.tut.rohlik.rohlikdemo.dto.request.CreateItemDto;
import cz.tut.rohlik.rohlikdemo.dto.request.UpdateItemDto;
import cz.tut.rohlik.rohlikdemo.exceptions.NotFoundException;
import cz.tut.rohlik.rohlikdemo.model.Item;
import cz.tut.rohlik.rohlikdemo.model.ItemCategory;
import cz.tut.rohlik.rohlikdemo.repository.ItemCategoryRepository;
import cz.tut.rohlik.rohlikdemo.repository.ItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cz.tut.rohlik.rohlikdemo.mapper.ItemMapper.MAPPER;

@Service
@Log4j2
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    public ItemService(ItemRepository itemRepository,
                       ItemCategoryRepository itemCategoryRepository) {
        this.itemRepository = itemRepository;
        this.itemCategoryRepository = itemCategoryRepository;
    }

    @Transactional
    public ItemDto addItem(CreateItemDto request) {
        Item item = MAPPER.toItem(request);

        ItemCategory itemCategory = itemCategoryRepository.findById(request.getItemCategory())
                                                          .orElseThrow(() -> new NotFoundException(
                                                                  "Item category  not found!"));
        item.setItemCategory(itemCategory);
        Item savedItem = itemRepository.save(item);
        log.info("Item was add");
        return MAPPER.toItemDto(savedItem);
    }

    @Transactional
    public ItemDto updateItem(String itemId, UpdateItemDto updateItemDto) {
        Item item = itemRepository.findById(itemId)
                                  .orElseThrow(() -> new NotFoundException("Item not found"));
        MAPPER.updateItem(item, updateItemDto);
        ItemCategory itemCategory = itemCategoryRepository.findById(updateItemDto.getItemCategory())
                                                          .orElseThrow(() -> new NotFoundException(
                                                                  "Item category  not found!"));
        item.setItemCategory(itemCategory);
        Item savedItem = itemRepository.save(item);
        return MAPPER.toItemDto(savedItem);
    }

    @Transactional(readOnly = true)
    public ItemDto getItem(String itemId) {
        Item item = itemRepository.findByIdAndDeletedIsFalse(itemId)
                                  .orElseThrow(() -> new NotFoundException("Item not found"));
        return MAPPER.toItemDto(item);
    }

    @Transactional
    public void deleteItem(String itemId) {
        Item item = itemRepository.findById(itemId)
                                  .orElseThrow(() -> new NotFoundException("Item not found"));
        item.setDeleted(true);
        itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public PageableDto<ItemDto> getItems(Pageable pageable) {
        Page<Item> pageableItems = itemRepository.findAllAvailableItems(pageable);

        List<ItemDto> items =
                pageableItems.stream().map(MAPPER::toItemDto).collect(Collectors.toList());

        return PageableDto.<ItemDto>builder()
                .items(items)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(pageableItems.getTotalElements())
                .totalPages(pageableItems.getTotalPages())
                .build();
    }
}
