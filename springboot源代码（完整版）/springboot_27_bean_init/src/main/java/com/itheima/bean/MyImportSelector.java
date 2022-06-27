package com.itheima.bean;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
//        System.out.println("================");
//        System.out.println("提示："+metadata.getClassName());
//        System.out.println(metadata.hasAnnotation("org.springframework.context.annotation.Configuration"));
//        Map<String, Object> attributes = metadata.getAnnotationAttributes("org.springframework.context.annotation.ComponentScan");
//        System.out.println(attributes);
//        System.out.println("================");

        //各种条件的判定，判定完毕后，决定是否装在指定的bean
        boolean flag = metadata.hasAnnotation("org.springframework.context.annotation.Configuration");
        if(flag){
            return new String[]{"com.itheima.bean.Dog"};
        }
        return new String[]{"com.itheima.bean.Cat"};
    }
}
