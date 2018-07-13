package org.oreframework.boot.seed;

import org.oreframework.boot.main.AbsOreBootStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author huangzz
 * 2017年3月18日
 */
@SpringBootApplication
@ServletComponentScan
public class SeedStarter extends AbsOreBootStarter
{
    
    @Override
    public void run(String[] args)
    {
        SpringApplication.run(SeedStarter.class, args);
    }
    
    public static void main(String[] args)
    {
    	SeedStarter starter = new SeedStarter();
        starter.run(args);
    }
    
}
