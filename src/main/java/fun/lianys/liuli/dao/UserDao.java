package fun.lianys.liuli.dao;

import fun.lianys.liuli.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    public User getUserById(long id);
}
