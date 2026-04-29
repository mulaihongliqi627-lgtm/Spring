package com.amadeus.bookdemo.service;

import com.amadeus.bookdemo.enums.BookStatus;
import com.amadeus.bookdemo.mapper.BookInfoMapper;
import com.amadeus.bookdemo.model.BookInfo;
import com.amadeus.bookdemo.model.PageRequest;
import com.amadeus.bookdemo.model.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private static BookInfoMapper bookInfoMapper;
    public static PageResponse<BookInfo> getListByPage(PageRequest pageRequest) {
        //查询图书总数
        Integer count = bookInfoMapper.count();
        if (count==0){
            return new PageResponse<>(0);
        }
        //查询当前页的数据
        List<BookInfo> bookInfos = bookInfoMapper.getListByPage(pageRequest);
        //TODO  bookInfos 为空判断
        for (BookInfo bookInfo: bookInfos){
            bookInfo.setStatusCN(BookStatus.getNameByCode(bookInfo.getStatus()).getName());
        }
        return new PageResponse<>(count,bookInfos);

    }

    public BookInfo queryById(Integer id){
        return bookInfoMapper.queryById(id);
    }
    public Integer updateBook(BookInfo bookInfo){
        return bookInfoMapper.updateBook(bookInfo);
    }

    public Integer deleteBookById(Integer id){
        return bookInfoMapper.deleteBookById(id);
    }
}
