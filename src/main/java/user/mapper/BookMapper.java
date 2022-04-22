package user.mapper;

import org.apache.ibatis.annotations.Mapper;
import user.pojo.Book;


@Mapper
public interface BookMapper extends tk.mybatis.mapper.common.Mapper<Book>{
}
