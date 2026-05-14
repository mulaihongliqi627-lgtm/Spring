package com.amadeus.bookdemo.mapper;


import com.amadeus.bookdemo.model.BookInfo;
import com.amadeus.bookdemo.model.PageRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookInfoMapper {


    @Select("select count(1) from book_info where status<>0")
    Integer count();

    @Insert("insert into book_info (book_name,author,count, price, publish, status) " +
            "values (#{bookName}, #{author}, #{count}, #{price}, #{publish}, #{status})")
    Integer addBook(BookInfo bookInfo);

    @Select("select * from book_info where status<>0 limit #{offset} , #{pageSize}")
    List<BookInfo> getListByPage(PageRequest pageRequest);

    @Select("select * from book_info where id = #{id} and status<>0")
    BookInfo queryById(Integer id);

}
