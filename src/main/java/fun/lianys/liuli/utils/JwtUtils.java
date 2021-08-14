package fun.lianys.liuli.utils;

import fun.lianys.liuli.pojo.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JwtUtils {
    /**
     * token 过期时间, 单位: 秒. 这个值表示 30 天
     */
    private final long TOKEN_EXPIRED_TIME = 30 * 24 * 60 * 60;
    /**
     * jwt 加密解密密钥(可自行填写)
     */
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final String REDIS_JWT_KEY_PREFIX = "security:jwt:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 创建JWT
     */
    public String createJWT(Map<String, Object> claims, String jwtId, String subject, Long time) {
        Date now = new Date(System.currentTimeMillis());

        Key secretKey = generalKey();
        System.out.println("create: " + claims.toString() + subject);
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setSubject(subject)
                .setId(jwtId)                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           //iat: jwt的签发时间
                .signWith(secretKey);//设置签名使用的签名算法和签名使用的秘钥
        if (time >= 0) {
            long expMillis = nowMillis + time;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);     //设置过期时间
        }

        String jwt = builder.compact();
        // 将生成的JWT保存至Redis
        stringRedisTemplate.opsForValue().set(REDIS_JWT_KEY_PREFIX + subject, jwt, TOKEN_EXPIRED_TIME, TimeUnit.MILLISECONDS);
        return jwt;
    }

    /**
     * 创建JWT
     *
     * @param authentication 用户认证信息
     * @return JWT
     */
    public String createJWT(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userPrincipal.getRoles());
        claims.put("authorities", userPrincipal.getAuthorities());
        String jwt = createJWT(claims, userPrincipal.getId().toString(), userPrincipal.getUsername(), TOKEN_EXPIRED_TIME);
        System.out.println("created jwt: " + jwt);
        return jwt;
    }


    /**
     * 验证jwt
     */
    public Claims verifyJwt(String token) {
        //签名秘钥，和生成的签名的秘钥一模一样
        Key key = generalKey();
        Claims claims = Jwts.parserBuilder()  // (1)
                .setSigningKey(key)         // (2)
                .build()                    // (3)
                .parseClaimsJws(token).getBody(); // (4)
        return claims;
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt) {
        String TOKEN_EXPIRED = "token已过期";
        String TOKEN_PARSE_ERROR = "token解析错误";
        try {
            System.out.println("parse jwt: " + jwt);
            Claims claims = verifyJwt(jwt);

            String username = claims.getSubject();
            String redisKey = REDIS_JWT_KEY_PREFIX + username;

            // 校验redis中的JWT是否存在
//            Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
//            if (Objects.isNull(expire) || expire <= 0) {
//                throw new SecurityException(TOKEN_EXPIRED);
//            }
//
//            // 校验redis中的JWT是否与当前的一致，不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
//            String redisToken = stringRedisTemplate.opsForValue().get(redisKey);
//            if (jwt == redisToken) {
//                throw new SecurityException(TOKEN_EXPIRED);
//            }
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new SecurityException(TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new SecurityException(TOKEN_PARSE_ERROR);
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new SecurityException(TOKEN_PARSE_ERROR);
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
            System.out.println(e);
            throw new SecurityException(TOKEN_PARSE_ERROR);
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new SecurityException(TOKEN_PARSE_ERROR);
        }
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public Key generalKey() {
//        String stringKey = JWT_SECRET;
        return key;
    }

    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */
    public String getUsernameFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        System.out.println("parsed claims: " + claims.toString());
        return claims.getSubject();
    }

    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println(bearerToken);
        if (bearerToken != "" && bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
