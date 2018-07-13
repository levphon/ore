package org.oreframework.boot.autoconfigure.kafka.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author huangzz 2017年3月18日
 */
public class EnAbleConsumerCondition implements Condition
{
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata)
    {
        if (null == context.getEnvironment().getProperty("spring.kafka.consumer.group-id"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
}
