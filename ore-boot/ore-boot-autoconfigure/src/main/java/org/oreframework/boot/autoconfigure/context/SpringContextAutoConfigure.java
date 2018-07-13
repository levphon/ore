package org.oreframework.boot.autoconfigure.context;

import org.oreframework.context.SpringContext;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * @author huangzz 2017年3月18日
 */
@Configurable
public class SpringContextAutoConfigure
{
    
    @Bean
    public SpringContext springContext()
    {
        return new SpringContext();
    }
    
}
