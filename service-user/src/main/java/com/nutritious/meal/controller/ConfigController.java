package com.nutritious.meal.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
        return useLocalCache + "-" + springEnable;
    }
}
