package com.i18n.translator.model.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class PageableDto<T> {
    Page page;
    T data;

    @Data
    @AllArgsConstructor
    public static class Page {
        Integer page;
        Integer size;
        Long totalRecords;
    }
}


