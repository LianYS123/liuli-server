package fun.lianys.liuli;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@EntityScan(basePackages = "fun.lianys.liuli.pojo")
@MapperScan(basePackages = "fun.lianys.liuli.dao", annotationClass = Repository.class)
public class LiuliApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiuliApplication.class, args);
    }

}
