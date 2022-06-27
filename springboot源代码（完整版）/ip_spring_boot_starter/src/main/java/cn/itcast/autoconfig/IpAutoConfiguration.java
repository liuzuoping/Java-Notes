package cn.itcast.autoconfig;

import cn.itcast.properties.IpProperties;
import cn.itcast.service.IpCountService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
//@EnableConfigurationProperties(IpProperties.class)
@Import(IpProperties.class)
public class IpAutoConfiguration {

    @Bean
    public IpCountService ipCountService(){
        return new IpCountService();
    }
}
