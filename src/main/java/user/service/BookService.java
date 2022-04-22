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
