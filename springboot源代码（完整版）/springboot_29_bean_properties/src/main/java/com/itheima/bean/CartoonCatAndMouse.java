package com.itheima.bean;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

@Data
@ConditionalOnClass(name="org.springframework.data.redis.core.RedisOperations")
@EnableConfigurationProperties(CartoonProperties.class)
public class CartoonCatAndMouse implements ApplicationContextAware {
    private Cat cat;
    private Mouse mouse;

    private CartoonProperties cartoonProperties;

    public CartoonCatAndMouse(CartoonProperties cartoonProperties){
        this.cartoonProperties = cartoonProperties;
        cat = new Cat();
        cat.setName(cartoonProperties.getCat()!=null && StringUtils.hasText(cartoonProperties.getCat().getName()) ? cartoonProperties.getCat().getName() : "tom");
        cat.setAge(cartoonProperties.getCat()!=null && cartoonProperties.getCat().getAge()!=null ? cartoonProperties.getCat().getAge() : 3);
        mouse = new Mouse();
        mouse.setName(cartoonProperties.getMouse()!=null && StringUtils.hasText(cartoonProperties.getMouse().getName()) ? cartoonProperties.getMouse().getName() : "jerry");
        mouse.setAge(cartoonProperties.getMouse()!=null && cartoonProperties.getMouse().getAge()!=null ? cartoonProperties.getMouse().getAge() : 4);
    }

    public void play(){
        String[] beans = applicationContext.getBeanDefinitionNames();
        for (String bean : beans) {
            System.out.println(bean);
        }
        System.out.println(cat.getAge()+"岁的"+cat.getName()+"和"+mouse.getAge()+"岁的"+mouse.getName()+"打起来了");
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
