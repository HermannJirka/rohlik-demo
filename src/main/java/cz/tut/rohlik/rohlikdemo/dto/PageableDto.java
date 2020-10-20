package cz.tut.rohlik.rohlikdemo.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PageableDto<T> {

    int page;
    int size;
    int totalPages;
    long totalElements;
    List<T> items;
}
