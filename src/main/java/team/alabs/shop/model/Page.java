package team.alabs.shop.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class Page {
    private int totalElements;
    private int totalPages;
    private int page;
    private int size;
    private List<Map<String, Object>> content;
}
