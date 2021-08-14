package fun.lianys.liuli.dao;

import fun.lianys.liuli.pojo.User;
import fun.lianys.liuli.vo.UserParams;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    User getUserById(long id);

    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
    })
    @Select("select * from user")
    List<User> getUserList();

    @Select("select * from user where username=#{username}")
    User getUserByUsername(String username);


    @Insert("insert into user(username, password, phone, email) values(#{username}, #{password}, #{phone}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(UserParams user);

    @Delete("delete from user where id = #{id}")
    int deleteUser(int id);

    @Update("update user set username=#{username},password=#{password},phone=#{phone},email=#{email} where id=#{id}")
    int updateUser(UserParams user);
}
