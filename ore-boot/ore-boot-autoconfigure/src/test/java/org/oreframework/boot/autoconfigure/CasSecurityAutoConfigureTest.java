package org.oreframework.boot.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author huangzz 2017年3月19日
 */
@Configuration
@EnableWebSecurity
public class CasSecurityAutoConfigureTest extends WebSecurityConfigurerAdapter
{
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception
    {
        auth.inMemoryAuthentication().withUser("user").password("8").roles("USER");
    }
}