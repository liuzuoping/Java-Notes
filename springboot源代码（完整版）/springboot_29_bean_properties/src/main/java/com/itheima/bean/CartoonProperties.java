package com.itheima.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cartoon")
@Data
public class CartoonProperties {
    private Cat cat;
    private Mouse mouse;
}
