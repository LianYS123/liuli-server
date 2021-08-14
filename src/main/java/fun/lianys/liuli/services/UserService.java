package fun.lianys.liuli.services;

import com.github.pagehelper.PageInfo;
import fun.lianys.liuli.pojo.User;
import fun.lianys.liuli.vo.UserParams;

public interface UserService {
    public void printUser(long id);

    public PageInfo getUserList(Integer page, Integer pageSize);

    public int addUser(UserParams user);

    public int deleteUser(int id);

    public int updateUser(UserParams user);
}
