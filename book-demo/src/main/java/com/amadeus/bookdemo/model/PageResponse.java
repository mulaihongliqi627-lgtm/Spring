package com.amadeus.bookdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class PageResponse<T> {
    private final Integer total;//图书总数
    private List<T> records;//图书列表
}
