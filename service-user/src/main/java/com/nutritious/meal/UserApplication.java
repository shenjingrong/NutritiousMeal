package com.nutritious.meal;

import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * description: 用户启动类
 *
 * @author shenjr
 * create_date: 2022/3/3 14:03
 **/
@SpringBootApplication
@RestController
@NacosPropertySource(dataId = "example", autoRefreshed = true)
@EnableNacosConfig
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") String id) {
        return "Hello World, " + id;
    }
}
