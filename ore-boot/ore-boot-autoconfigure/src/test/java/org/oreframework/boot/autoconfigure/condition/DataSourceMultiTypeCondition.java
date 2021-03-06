package org.oreframework.boot.autoconfigure.condition;

import org.oreframework.boot.ApplicationConstants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * #datasouce type config value=single/multi
 * ore.datasource.type=multi
 * ore.primary.base-package=org.oreframework.boot.sample.mybatis.multi.primary
 * ore.secondary.base-package=org.oreframework.boot.sample.mybatis.multi.secondary
 * @author huangzz 2017年3月18日
 */
public class DataSourceMultiTypeCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		if (null == context.getEnvironment().getProperty("ore.datasource.type")) {
			return false;
		} else {
			return context.getEnvironment().getProperty("ore.datasource.type")
					.equals(DataSourceType.MULTI.toString());
		}
	}

}
