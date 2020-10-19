package cz.tut.rohlik.rohlikdemo.service;

import cz.tut.rohlik.rohlikdemo.dto.ItemDto;
import cz.tut.rohlik.rohlikdemo.dto.PagedListWrapper;
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

    public ItemDto updateItem(String itemId, UpdateItemDto updateItemDto) {
        Item item = itemRepository.findById(itemId)
                                  .orElseThrow(() -> new NotFoundException("Item not found"));
        MAPPER.updateItem(item, updateItemDto);
        Item savedItem = itemRepository.save(item);
        return MAPPER.toItemDto(savedItem);
    }

    public ItemDto getItem(String itemId) {
        Item item = itemRepository.findById(itemId)
                                  .orElseThrow(() -> new NotFoundException("Item not found"));
        return MAPPER.toItemDto(item);
    }

    public void deleteItem(String itemId) {
        Item item = itemRepository.findById(itemId)
                                  .orElseThrow(() -> new NotFoundException("Item not found"));
        item.setDeleted(true);
        itemRepository.save(item);
    }

    public PagedListWrapper<ItemDto> getItems(Pageable pageable) {
        Page<Item> items = itemRepository.findAllAvailableItems(pageable);
        return new PagedListWrapper<>(
                items.getTotalElements(),
                items.stream().map(MAPPER::toItemDto).collect(Collectors.toList())
        );
    }
}
