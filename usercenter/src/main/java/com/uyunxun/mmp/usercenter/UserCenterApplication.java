package com.uyunxun.mmp.usercenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by hu on 2017/4/25.
 */
@ComponentScan(basePackages = "com.uyunxun.mmp")
@SpringBootApplication
@NoRepositoryBean
@EnableJpaRepositories(basePackages = "com.uyunxun.mmp")
@EntityScan("com.uyunxun.mmp.domain.po")
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }
}
