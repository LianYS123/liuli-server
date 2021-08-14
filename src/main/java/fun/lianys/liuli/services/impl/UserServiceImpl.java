package fun.lianys.liuli.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.lianys.liuli.dao.UserDao;
import fun.lianys.liuli.pojo.User;
import fun.lianys.liuli.services.UserService;
import fun.lianys.liuli.vo.UserParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao = null;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void printUser(long id) {
        User user = userDao.getUserById(id);
        System.out.println(user);
    }

    @Override
    public PageInfo getUserList(Integer page, Integer pageSize) {
        Page helper = PageHelper.startPage(page, pageSize);
        List<User> userList = userDao.getUserList();
        return new PageInfo(helper.getResult());
    }

    @Override
    public int addUser(UserParams user) {
        user.setPassword(this.encoder.encode(user.getPassword()));
        return userDao.insertUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return userDao.deleteUser(id);
    }

    @Override
    public int updateUser(UserParams user) {
        user.setPassword(this.encoder.encode(user.getPassword()));
        return userDao.updateUser(user);
    }
}
