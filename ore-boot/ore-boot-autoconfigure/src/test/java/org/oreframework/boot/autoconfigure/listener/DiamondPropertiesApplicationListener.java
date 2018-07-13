package org.oreframework.boot.autoconfigure.listener;

import com.taobao.diamond.manager.ManagerListener;
import com.taobao.diamond.manager.impl.DefaultDiamondManager;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Created by linxy on 2017/3/3.
 */

public class DiamondPropertiesApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {

    private void loadProperties(ConfigurableEnvironment environment, String info) {
        StringReader reader = new StringReader(info);

        Properties properties = new Properties();
        try {
            properties.load(reader);
            PropertySource propertySource = new PropertiesPropertySource("configProperties", properties);
            environment.getPropertySources().addLast(propertySource);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();

        String dataId = environment.getProperty("diamond.dataId");
        String group = environment.getProperty("diamond.group");
        String env = environment.getProperty("diamond.env");

        DefaultDiamondManager manager1 = new DefaultDiamondManager(group, dataId+"-"+env, new ManagerListener() {
            public Executor getExecutor() {
                return null;
            }

            public void receiveConfigInfo(String configInfo) {
                loadProperties(environment, configInfo);
            }
        });

        String e = manager1.getAvailableConfigureInfomation(30000L);
        loadProperties(environment, e);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
