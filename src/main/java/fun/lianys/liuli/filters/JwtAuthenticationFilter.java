package fun.lianys.liuli.filters;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import fun.lianys.liuli.common.ApiResponse;
import fun.lianys.liuli.constant.SecurityConstants;
import fun.lianys.liuli.exception.BaseException;
import fun.lianys.liuli.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkIgnores(request)) {
            filterChain.doFilter(request, response);
            System.out.println("放行");
            return;
        }
        String jwt = jwtUtil.getJwtFromRequest(request);
        try {
            if (!StrUtil.isBlank(jwt)) {
                String username = jwtUtil.getUsernameFromJWT(jwt);
                System.out.println(username);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 获取安全对象上下文, 修改authentication
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                throw new SecurityException("no token");
            }
        } catch (SecurityException exception) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);

            // FIXME: hutool 的 BUG：JSONUtil.toJsonStr()
            //  将JSON转为String的时候，忽略null值的时候转成的String存在错误
            response.getWriter().write(
                    JSONUtil.toJsonStr(new JSONObject(ApiResponse.ofException(new BaseException(401, exception.getMessage())), false))
            );
        }
    }

    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        String method = request.getMethod();

        Set<String> ignores = new HashSet<>();
        ignores.add("/auth/login");
        ignores.addAll(Arrays.asList(SecurityConstants.SWAGGER_WHITELIST));
        for (String ignore : ignores) {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
            if (matcher.matches(request)) {
                return true;
            }
        }

        return false;
    }
}
