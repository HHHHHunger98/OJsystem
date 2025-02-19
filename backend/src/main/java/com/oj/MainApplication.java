package com.oj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * MainApplication Class (project entrance)
 *
 * @author <a href="https://github.com/HHHHHunger98">hhhhhunger</a>
 * @from <a href="https://github.com/HHHHHunger98/OJsystem">OJ_system</a>
 *
 * based on <a href="https://github.com/LURENYUANSHI/springboot-init-master">springboot-init-master</a>
 * by <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@MapperScan("com.oj.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
