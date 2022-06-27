package com.itheima.config;

import com.itheima.bean.MyPostProcessor;
import com.itheima.bean.MyRegistrar;
import com.itheima.bean.MyRegistrar2;
import com.itheima.bean.service.impl.BookServiceImpl1;
import org.springframework.context.annotation.Import;

@Import({BookServiceImpl1.class, MyPostProcessor.class, MyRegistrar2.class, MyRegistrar.class})
public class SpringConfig8 {
}
