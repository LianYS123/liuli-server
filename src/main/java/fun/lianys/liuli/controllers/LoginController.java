package fun.lianys.liuli.controllers;

import fun.lianys.liuli.common.ApiResponse;
import fun.lianys.liuli.utils.JwtUtils;
import fun.lianys.liuli.vo.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    ApiResponse login(@RequestBody LoginRequest loginRequest ){
        System.out.println(loginRequest);
        // 获取未校验的Authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        // 校验
        Authentication authenticate = authenticationManager.authenticate(auth);

        // 签发token
        String token = jwtUtils.createJWT(authenticate);

        // 存入 context, 这样就能在以后的登录中被识别出来了
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return ApiResponse.ofSuccess(token);

    }
}
