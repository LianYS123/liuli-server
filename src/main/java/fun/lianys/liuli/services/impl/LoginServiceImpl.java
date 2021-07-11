package fun.lianys.liuli.services.impl;

import fun.lianys.liuli.exception.JsonException;
import fun.lianys.liuli.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class LoginServiceImpl {
    @Autowired
    private RedisService redisService;

    public Map login(String username, String password) {
        if (Objects.equals("lian", username) &&
                Objects.equals("tb1766318380", password)) {
            String token = UUID.randomUUID().toString();
            redisService.set(token, username);
            HashMap<String, String> map = new HashMap<>();
            map.put("username", username);
            map.put("password", password);
            return map;
        } else {
            throw new JsonException(1234, "用户名或密码错误");
        }
    }

    public String logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        Boolean delete = redisService.delete(token);
        if (!delete) {
            return "注销失败，请检查是否登录！";
        }
        return "注销成功！";
    }
}
