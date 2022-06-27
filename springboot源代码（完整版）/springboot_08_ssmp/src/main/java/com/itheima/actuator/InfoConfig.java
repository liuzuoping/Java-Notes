package com.itheima.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InfoConfig implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("runTime",System.currentTimeMillis());
        Map infoMap = new HashMap();
        infoMap.put("buildTime","2006");
        builder.withDetails(infoMap);
    }
}
