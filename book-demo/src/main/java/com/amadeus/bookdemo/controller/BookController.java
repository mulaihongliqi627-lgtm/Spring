package com.amadeus.bookdemo.controller;


import com.amadeus.bookdemo.model.BookInfo;
import com.amadeus.bookdemo.model.PageRequest;
import com.amadeus.bookdemo.model.PageResponse;
import com.amadeus.bookdemo.model.Result;
import com.amadeus.bookdemo.service.BookService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BookController {

    @Autowired
    private BookService bookService;


    //获取图书列表信息
    @RequestMapping("getBookListByPage")
    public Result<PageResponse<BookInfo>> getBookListByPage(PageRequest pageRequest, HttpSession session){
        log.info("查询图书列表,pageRequest:{}", pageRequest);
        PageResponse<BookInfo> response = bookService.getListByPage(pageRequest);
        return Result.success(response);
    }


    //添加图书操作
    @RequestMapping("/addBook")
    public String addBook(BookInfo bookInfo){
        log.info("添加图书,bookInfo:{}", bookInfo);
        //对添加的图书进行参数校验
        if(!StringUtils.hasText(bookInfo.getBookName())
                || !StringUtils.hasText(bookInfo.getAuthor())
                || !StringUtils.hasText(bookInfo.getPublish())
                || bookInfo.getPrice() == null
                || bookInfo.getCount() == null
                || bookInfo.getStatus() == null){
            return "输入参数不合法,请重新校验!";
        }
        Integer result = bookService.addBook(bookInfo);
        bookService.addBook(bookInfo);
        return "";
    }

    @RequestMapping("/updateBook")
    public String updateBook(BookInfo bookInfo){
        log.info("修改图书,bookInfo:{}", bookInfo);
        //输入参数校验
        if(!StringUtils.hasText(bookInfo.getBookName())
                || !StringUtils.hasText(bookInfo.getAuthor())
                || !StringUtils.hasText(bookInfo.getPublish())
                || bookInfo.getPrice() == null
                || bookInfo.getCount() == null
                || bookInfo.getStatus() == null){
            return "输入参数不合法,请重新校验!";
        }
        try{
            Integer result = bookService.updateBook(bookInfo);
            return result == 1 ? "" : "修改图书失败";
        }catch (Exception e){
            log.error("修改图书异常,e:{}", e);
            return "修改图书失败";
        }
    }

    @RequestMapping("/deleteBookById")
    public String deleteBookById(Integer id){
        log.info("删除图书,id:{}", id);
        if(id == null || id <= 0){
            return "输入参数不合法,请重新校验!";
        }
        try{
            Integer result = bookService.deleteBookById(id);
            return result == 1 ? "" : "删除图书失败";
        }catch (Exception e){
            log.error("删除图书异常,e:{}", e);
            return "删除图书失败";
        }
    }

    @RequestMapping("/queryBookById")
    public BookInfo queryBookById(Integer id){
        log.info("查询图书,id:{}", id);
        if (id == null || id <= 0){
            return new BookInfo();
        }
        try{
            BookInfo bookInfo = bookService.queryById(id);
            return bookInfo;
        }catch (Exception e){
            log.error("查询图书异常,e:{}", e);
            return new BookInfo();
        }

    }


}
