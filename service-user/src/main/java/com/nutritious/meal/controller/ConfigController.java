package com.nutritious.meal.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.nutritious.meal.config.Apple;
import com.nutritious.meal.config.Banana;
import com.nutritious.meal.config.Cat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/3/7 11:35
 **/
@RestController
@RequestMapping("config")
public class ConfigController {

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;
    @Value("${people:}")
    private String springEnable;

    @GetMapping(value = "/get")
    @ResponseBody
    public String get() {
        Apple apple = (a) -> a;
        apple.eat("a");
//        ConcurrentHashMap
        return useLocalCache + "-" + springEnable;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Banana b1 = new Banana();
        Cat c1 = new Cat();
        c1.setAge(1);
        c1.setName("c");
        b1.setCat(c1);
        Banana b2 = b1.clone();
        System.out.println(b1.getCat() == b2.getCat());
    }

}
