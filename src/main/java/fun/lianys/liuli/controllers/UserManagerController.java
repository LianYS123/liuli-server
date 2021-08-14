package fun.lianys.liuli.controllers;

import fun.lianys.liuli.common.ApiResponse;
import fun.lianys.liuli.pojo.User;
import fun.lianys.liuli.services.UserService;
import fun.lianys.liuli.vo.UserParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserManagerController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ApiResponse getUserList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return ApiResponse.ofSuccess(userService.getUserList(page, pageSize));
    }

    @PostMapping
    public ApiResponse addUser(@RequestBody UserParams user){
        System.out.println(user);
        return ApiResponse.ofSuccess(userService.addUser(user));
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteUser(@PathVariable("id") int id) {
        return ApiResponse.ofSuccess(userService.deleteUser(id));
    }

    @PutMapping("/{id}")
    public ApiResponse updateUser(@RequestBody UserParams user, @PathVariable Integer id) {
        System.out.println(user);
        user.setId(id);
        return ApiResponse.ofSuccess(userService.updateUser(user));
    }

}
