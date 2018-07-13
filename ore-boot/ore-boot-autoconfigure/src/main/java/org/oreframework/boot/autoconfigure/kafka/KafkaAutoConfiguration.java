package org.oreframework.boot.autoconfigure.kafka;

import java.util.Properties;

import org.oreframework.boot.autoconfigure.kafka.condition.EnAbleConsumerCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import kafka.consumer.ConsumerConfig;

/**
 * @author huangzz
 * 2017年3月18日
 */
@Configuration
@Conditional(EnAbleConsumerCondition.class)
@ConditionalOnClass({ConsumerConfig.class,KafkaProperties.class})
public class KafkaAutoConfiguration
{
    @Bean
    public ConsumerConfig consumerConfig(KafkaProperties kafkaProperties)
    {
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty("zookeeper.connect", kafkaProperties.getProperties().get("zookeeper.connect"));
        consumerProperties.setProperty("zookeeper.connection.timeout.ms", kafkaProperties.getProperties().get("zookeeper.connection.timeout.ms"));
        consumerProperties.setProperty("zookeeper.session.timeout.ms", kafkaProperties.getProperties().get("zookeeper.session.timeout.ms"));
        consumerProperties.setProperty("group.id", kafkaProperties.getConsumer().getGroupId());
        consumerProperties.setProperty("auto.commit.interval.ms", kafkaProperties.getProperties().get("auto.commit.interval.ms"));
        consumerProperties.setProperty("auto.offset.reset", kafkaProperties.getConsumer().getAutoOffsetReset());
        
        return new ConsumerConfig(consumerProperties);
        
    }
}
