package com.example.demo.core.response;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public class PageSigep {

    public static <T> ResponseEntity<PageResponse<T>> ok(Page<T> page) {
        return ResponseEntity.ok(PageResponse.of(page));
    }

}

