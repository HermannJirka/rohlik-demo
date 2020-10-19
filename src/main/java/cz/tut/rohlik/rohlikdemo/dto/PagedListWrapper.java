package cz.tut.rohlik.rohlikdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedListWrapper<T> {
    private Long totalCount;
    private List<T> requestedPage;
}
