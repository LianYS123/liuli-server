package fun.lianys.liuli;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class LiuliApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void  test(){
        BCryptPasswordEncoder crpt = new BCryptPasswordEncoder();
        System.out.println(crpt.encode("123456"));
    }

}
