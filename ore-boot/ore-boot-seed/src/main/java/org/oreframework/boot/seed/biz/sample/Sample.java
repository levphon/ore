package org.oreframework.boot.seed.biz.sample;

import org.oreframework.boot.seed.framework.config.StaticProperty;
import org.oreframework.context.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangzz
 * 2017年3月18日
 */
@RestController
public class Sample {
	
	private static Logger logger = LoggerFactory.getLogger(Sample.class);

	@Autowired
	private ConfigurableEnvironment env;

	@Autowired
	private StaticProperty staticProperty;

	@RequestMapping("/home")
	String home() {

		return staticProperty.getResource();
	}

	@RequestMapping("/env")
	String env() {

		return env.toString();
	}
	
	@RequestMapping("/getBean")
	String getBean() {
		return ((StaticProperty)SpringContext.getBean("staticProperty")).getResource();
	}
	
	
	@RequestMapping("/hello/{myName}")
	String index(@PathVariable String myName) {
	    logger.info(myName);
		return "Hello " + myName + "!!!";
	}
}