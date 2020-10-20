package cz.tut.rohlik.rohlikdemo.controller;

import cz.tut.rohlik.rohlikdemo.dto.ItemDto;
import cz.tut.rohlik.rohlikdemo.dto.PageableDto;
import cz.tut.rohlik.rohlikdemo.dto.request.CreateItemDto;
import cz.tut.rohlik.rohlikdemo.dto.request.UpdateItemDto;
import cz.tut.rohlik.rohlikdemo.service.ItemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/item")
@Log4j2
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDto> addItem(@RequestBody CreateItemDto request) {
        return ok(itemService.addItem(request));
    }

    @PutMapping(path = "/{itemId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDto> updateItem(@PathVariable("itemId") String itemId,
                                              @RequestBody UpdateItemDto updateItemDto) {
        return ok(itemService.updateItem(itemId, updateItemDto));
    }

    @GetMapping(path = "/{itemId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDto> getItem(@PathVariable("itemId") String itemId) {
        return ok(itemService.getItem(itemId));
    }

    @GetMapping
    public ResponseEntity<PageableDto<ItemDto>> getAllItems(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        Pageable pageable;
        if (page == null && size == null) {
            pageable = Pageable.unpaged();
        } else {
            if (page == null){
                page = 1;
            }
            if (size == null){
                size = 100;
            }
            pageable = PageRequest.of(page - 1, size);
        }
        return ok(itemService.getItems(pageable));
    }

    @DeleteMapping(path = "/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable("itemId") String itemId) {
        itemService.deleteItem(itemId);
        return ok().build();
    }
}
