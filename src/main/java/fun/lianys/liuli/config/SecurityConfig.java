package fun.lianys.liuli.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import fun.lianys.liuli.common.ApiResponse;
import fun.lianys.liuli.constant.SecurityConstants;
import fun.lianys.liuli.exception.BaseException;
import fun.lianys.liuli.filters.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "fun.lianys.liuli.**.*")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    //    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;
//
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            response.getWriter().write(
                    JSONUtil.toJsonStr(new JSONObject(ApiResponse.ofException(new BaseException(401, accessDeniedException.getMessage())), false))
            );
        };
    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(authenticationManagerBean()).userDetailsService(userDetailService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                // ?????? CSRF
                .and().csrf().disable()
                // ???????????????????????????????????? AuthController#login
                .formLogin().disable()
                .httpBasic().disable()

                // ????????????
                .authorizeRequests()
                .antMatchers(SecurityConstants.SYSTEM_WHITELIST)
                .permitAll()
                .antMatchers(SecurityConstants.SWAGGER_WHITELIST)
                .permitAll()
                .anyRequest()
                .authenticated()

                // ???????????????????????????????????? AuthController#logout
                .and().logout().disable()
                // Session ??????
                .sessionManagement()
                // ???????????????JWT????????????????????????Session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // ????????????
//                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        // @formatter:on

        // ??????????????? JWT ?????????
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
