package user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import user.config.ResponseResult;
import user.pojo.Book;
import user.service.BookService;


import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("all/list")
    public ResponseResult all() {
        // 查询所有的
        List<Book> book = this.bookService.queryAll();
        System.out.println(book.get(0).toString());
        return getResult(book);
    }


    @GetMapping("delete/{id}")
    public ResponseResult deleteBookById(@PathVariable("id")Long id){
        System.out.println("controller id:"+id);
        bookService.deleteById(id);
        return getResult(1);
    }

    @GetMapping("add/{name}")
    public ResponseResult addBook(@PathVariable("name")String name){
        bookService.addById(name);
        return getResult(1);
    }

//    @GetMapping("hello")
//    public ResponseResult test(){
//        // test
//        List<Book> book = this.bookService.queryAll();
//        return getResult(book);
////        return getResult(this.bookService.queryById((long) 1));
//    }



    /**
     * 根据修改行数返回响应消息
     * @param rows
     * @return
     */
    public ResponseResult getResult(int rows) {
        return rows == 0 ? ResponseResult.error() : ResponseResult.success();
    }
    /**
     * 对象类型响应消息
     * @param data
     * @return
     */
    public ResponseResult getResult(Object data) {
        return ResponseResult.success(data);
    }

}
