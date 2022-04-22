# 运行截图和资源下载
![在这里插入图片描述](https://img-blog.csdnimg.cn/694735091b814542834f5e385b9f526e.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5rGq56iL5bqP54y_,size_20,color_FFFFFF,t_70,g_se,x_16)
![在这里插入图片描述](https://img-blog.csdnimg.cn/daceb3a7388d4b41b59da033114d8f87.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5rGq56iL5bqP54y_,size_20,color_FFFFFF,t_70,g_se,x_16)
视频演示：
[https://www.bilibili.com/video/BV1jS4y1a7ke/](https://www.bilibili.com/video/BV1jS4y1a7ke/)
[https://live.csdn.net/v/201258](https://live.csdn.net/v/201258)
资源下载：
[https://download.csdn.net/download/m0_61504367/85199130](https://download.csdn.net/download/m0_61504367/85199130)
[https://github.com/WangWenShuai529/easyBook](https://github.com/WangWenShuai529/easyBook)
![在这里插入图片描述](https://img-blog.csdnimg.cn/1606cd4d2b934142816987dc33e6392d.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5rGq56iL5bqP54y_,size_20,color_FFFFFF,t_70,g_se,x_16)


# 前端(vue)
基于html，在里面写vue
![在这里插入图片描述](https://img-blog.csdnimg.cn/1d1c5ed258404e5f9ec8fdb769035994.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5rGq56iL5bqP54y_,size_20,color_FFFFFF,t_70,g_se,x_16)

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <style>
      .grid {
        margin: auto;
        width: 800px;
        text-align: center;
      }
      .grid table {
        border-top: 1px solid #ced888;
        width: 100%;
        border-collapse: collapse;
      }
      .grid th,
      .grid td {
        border: 1px dashed #ced888;
        height: 40px;
        line-height: 40px;
        padding: 10px;
      }
      .grid th {
        background-color: #ced888;
      }
      .grid .inputLine {
        padding-top: 5px;
        padding-bottom: 10px;
        background-color: #ced888;
      }
    </style>
  </head>
  <body>
    <div id="app">
      <div class="grid">
        <h2>图书管理</h2>
        <div class="inputLine">
          书名：<input type="text" name="" id="" v-model="bookInfo.name" />
          <input
            type="button"
            value="提交"
            @click="submit"
            :disabled="isDisable"
          />
          搜索书名：
          <input type="text" name="" id="" v-model="searchName" v-focus />
        </div>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>书名</th>
              <th>创建时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item,index) in bookInfo" :key="item.id">
              <td>{{item.id}}</td>
              <td>{{item.name}}</td>
              <td>{{item.data}}</td>
              <td>
                <a href="" @click.prevent="delBook(item.id)">删除</a>|<a
                  href=""
                  @click.prevent="toEditBook(index)"
                  >修改</a
                >
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/vue@2/dist/vue.js"></script>
	<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
	<!-- 导入axios -->
    <script>
      Vue.directive("focus", {
        inserted: function (el) {
          el.focus();
        },
      });
      Vue.filter("dateFormat", function (val, args = "") {
        var dt = new Date(val);
        var y = dt.getFullYear();
        var m = (dt.getMonth() + 1).toString().padStart(2, "0");
        var d = dt.getDate().toString().padStart(2, "0");
        if (args === "yyyy-MM-dd") {
          return `${y}-${m}-${d}`;
        } else {
          var h = dt.getHours().toString().padStart(2, "0");
          var mm = dt.getMinutes().toString().padStart(2, "0");
          var s = dt.getSeconds().toString().padStart(2, "0");
          return `${y}-${m}-${d} ${h}:${mm}:${s}`;
        }
      });
      var vm = new Vue({
        created() {
			this.getAllBooks()
        },
        el: "#app",
        data: {
          bookInfo: {
            name: "",
          },
          searchName: "",
          currentId: 0,
          isDisable: true,
          books: [],
          modifyBookInfo:{
            name: "",
            id:""
          },
          bookDetail:{}

        },
        watch: {
          "bookInfo.name": function (val) {
            // 是否重名
            this.isDisable = this.books.some((item) => item.name === val);
          },
        },
        methods: {

		async getAllBooks(){
          axios.defaults.headers.post['Content-Type'] = 'application/json'
          var a =  await axios.get("http://localhost/book/all/list")
        this.bookInfo = a.data.data

          this.books = a.data.data;
		// console.log(this.bookInfo[0].name)
         // console.log(a.data.data)

        },
          // 图书检索
          searchBook(searchName) {
            return this.books.filter(
              (item) => item.name.indexOf(searchName) != -1
            );
          },
          // 最大的ID
          getId() {
            var ids = this.books.map((item) => item.id);
            return Math.max(...ids);
          },
          //添加图书
          async addBook() {
            if (this.bookInfo.name) {
              //获取ID
              var id = this.getId() + 1;
              var book = {
                id: id,
                name: this.bookInfo.name,
                cDate: new Date(),
              };
              this.books.push(book);

              axios.defaults.headers.post['Content-Type'] = 'application/json'
             await axios.get('http://localhost:80/book/add/'+book.name)
                   .then(res=>{ console.log(res) })
              this.getAllBooks()
            }
          },
          // 编辑图书
          async modifyBook() {
             this.isDisable = true;
             var id = this.currentId;
            console.log(this.currentId+"  "+this.bookInfo.name)
            this.delBook(this.currentId)
            this.addBook(this.bookInfo.name)
          },
          toEditBook(index) {
            console.log("toEditBook");
            this.isDisable = true;
            // console.log(this.books[index].name)
            // console.log(this.books[index].id)
            this.bookInfo.name = this.books[index].name
            this.currentId = this.books[index].id
            this.isDisable = false
            // console.log(this.bookInfo.name)
            // console.log( this.currentId )
            // this.getAllBooks();
          },
          // 提交
           submit() {
            if (this.currentId === 0) {
              //添加
              this.addBook();

            } else {
              //编辑
              this.modifyBook();
              this.currentId = 0;
            }
            this.bookInfo.name = "";
          },
          // 删除
         async delBook(id) {
            this.books = this.books.filter((item) => item.id !== id);
			// "http://localhost:80/books"
			await  axios.get(`http://localhost:80/book/delete/`+id);
			this.getAllBooks();
          },
        },
      });
    </script>
  </body>
</html>

```
# 后端(SSMP)
## 项目结构
![在这里插入图片描述](https://img-blog.csdnimg.cn/94afa6f73ca44b9698938085cc39d6d7.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5rGq56iL5bqP54y_,size_20,color_FFFFFF,t_70,g_se,x_16)

## 配置
application.xml
```xml
server.port=80
# 设置org.springframework包的日志级别为debug
logging.level.org.springframework=debug

# 连接四大参数
spring.datasource.url=jdbc:mysql://localhost:3306/easybook
spring.datasource.username=root
spring.datasource.password=root
# 可省略，SpringBoot自动推断
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.hikari.idle-timeout=60000
spring.datasource.hikari.maximum-pool-size=30
spring.datasource.hikari.minimum-idle=10


# mybatis 别名扫描
mybatis.type-aliases-package=user.mapper.pojo
# mapper.xml文件位置,如果没有映射文件，请注释掉
mybatis.mapper-locations=classpath:mappers/*.xml

# 开发阶段关闭thymeleaf的模板缓存
spring.thymeleaf.cache=false

```
CrosConfig(跨域)，端口号：63342
```java
package user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CrosConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:63342")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}

```

相应http的数据封装
```java
package user.config;

import user.config.HttpStatus;

import java.util.HashMap;

/**
 * 响应消息结果，使用Map存储
 */
public class ResponseResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    //状态码的key
    private static final String CODE = "code";

    //操作结果信息key
    private static final String MESSAGE = "msg";

    //返回数据的key
    private static final String DATA = "data";

    /**
     * 空的响应消息结果
     */
    public ResponseResult() {
    }

    /**
     * 不包含数据的响应消息
     *
     * @param code
     * @param msg
     */
    public ResponseResult(int code, String msg) {
        super.put(CODE, code);
        super.put(MESSAGE, msg);
    }

    /**
     * 构造响应消息
     *
     * @param code 状态码
     * @param msg  响应消息
     * @param data 响应数据
     */
    public ResponseResult(int code, String msg, Object data) {
        super.put(CODE, code);
        super.put(MESSAGE, msg);
        if (data != null) {
            super.put(DATA, data);
        }
    }

    /* 返回成功消息 */
    public static user.config.ResponseResult success() {
        return success("操作成功");
    }

    public static user.config.ResponseResult success(String msg) {
        return success(msg, null);
    }

    public static user.config.ResponseResult success(Object data) {
        return success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg  响应消息
     * @param data 响应数据
     * @return 响应消息对象
     */
    public static user.config.ResponseResult success(String msg, Object data) {
        return new user.config.ResponseResult(HttpStatus.SUCCESS, msg, data);
    }


    /* 返回错误消息 */
    public static user.config.ResponseResult error() {
        return error("操作失败");
    }

    public static user.config.ResponseResult error(String msg) {
        return error(msg, null);
    }

    /**
     * 指定返回错误状态码的错误消息
     *
     * @param code
     * @param msg
     * @return
     */
    public static user.config.ResponseResult error(int code, String msg) {
        return new user.config.ResponseResult(code, msg, null);
    }

    /**
     * 默认类型错误消息(500)
     *
     * @param msg
     * @param data
     * @return
     */
    public static user.config.ResponseResult error(String msg, Object data) {
        return new user.config.ResponseResult(HttpStatus.ERROR, msg, data);
    }

}

```

## controller
```java
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

```

对于id采用随机7位数字
```java
public int addById(String name){

    String uid = "";
    int  length = 7;
    for (Integer i = 0; i < length; i++) {
        String randChar = String.valueOf(Math.round(Math.random() * 9));
        uid = uid.concat(randChar);
    }
    long c = (long)(Double.parseDouble(uid));

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Book book =new Book(c,name,df.format(System.currentTimeMillis()));
    return this.bookMapper.insert(book);
}
```
## 服务层
```java
package user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.mapper.BookMapper;
import user.pojo.Book;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    public Book queryById(Long id){
        return this.bookMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public void deleteById(Long id){
        this.bookMapper.deleteByPrimaryKey(id);
    }

    public int addById(String name){

        String uid = "";
        int  length = 7;
        for (Integer i = 0; i < length; i++) {
            String randChar = String.valueOf(Math.round(Math.random() * 9));
            uid = uid.concat(randChar);
        }
        long c = (long)(Double.parseDouble(uid));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Book book =new Book(c,name,df.format(System.currentTimeMillis()));
        return this.bookMapper.insert(book);
    }
    public Book modifyById(Long id){
        return this.bookMapper.selectByPrimaryKey(id);
    }

    public List<Book> queryAll() {
        return  this.bookMapper.selectAll();
    }

//    String 转化为 long
    public static void main(String[] args)  {
        String uid = "";
        int  length = 7;
        for (Integer i = 0; i < length; i++) {
            String randChar = String.valueOf(Math.round(Math.random() * 9));
            uid = uid.concat(randChar);
        }
        long c = (long)(Double.parseDouble(uid));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(System.currentTimeMillis()));
//        System.out.println(c);
    }
}

```
## 数据层
```java
package user.mapper;

import org.apache.ibatis.annotations.Mapper;
import user.pojo.Book;


@Mapper
public interface BookMapper extends tk.mybatis.mapper.common.Mapper<Book>{
}

```

## 实体层
```java
package user.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Name;

    private String Data;

    public Book() {
    }

    public Book(Long id, String name, String data) {
        this.id = id;
        Name = name;
        Data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Data='" + Data + '\'' +
                '}';
    }
}

```
