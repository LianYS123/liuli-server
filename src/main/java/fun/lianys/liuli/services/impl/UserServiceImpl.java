package fun.lianys.liuli.services.impl;

import fun.lianys.liuli.dao.UserDao;
import fun.lianys.liuli.pojo.User;
import fun.lianys.liuli.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao = null;

    @Override
    public void printUser(long id) {
        User user = userDao.getUserById(id);
        System.out.println(user);
    }
}
