package cz.tut.rohlik.rohlikdemo.controller;

import cz.tut.rohlik.rohlikdemo.dto.ItemCategoryDto;
import cz.tut.rohlik.rohlikdemo.dto.request.CreateItemCategoryDto;
import cz.tut.rohlik.rohlikdemo.dto.request.UpdateItemCategoryDto;
import cz.tut.rohlik.rohlikdemo.service.ItemCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/item-category")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemCategoryDto> addItemCategory(
            @RequestBody CreateItemCategoryDto request) {
        return ok(itemCategoryService.addItemCategory(request));
    }

    @PutMapping(path = "/{itemCategoryId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemCategoryDto> updateItemCategory(
            @PathVariable("itemCategoryId") String itemCategoryId,
            @RequestBody UpdateItemCategoryDto updateItemCategoryDto) {
        return ok(itemCategoryService.updateItemCategory(itemCategoryId, updateItemCategoryDto));
    }

    @DeleteMapping(path = "/{itemCategoryId}")
    public ResponseEntity<Void> deleteItemCategory(
            @PathVariable("itemCategoryId") String itemCategoryId) {
        itemCategoryService.deleteItemCategory(itemCategoryId);
        return ok().build();
    }

    @GetMapping(path = "/{itemCategoryId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemCategoryDto> getItemCategory(
            @PathVariable("itemCategoryId") String itemCategoryId) {
        return ok(itemCategoryService.getItemCategory(itemCategoryId));
    }

    @GetMapping
    public ResponseEntity<List<ItemCategoryDto>> getAllItemCategories() {
        return ok(itemCategoryService.getAllItemCategories());
    }
}
