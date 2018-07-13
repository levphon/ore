package org.oreframework.boot.autoconfigure.redis;

import org.oreframework.redis.service.IRedisService;
import org.oreframework.redis.service.impl.RedisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author huangzz 2017年3月18日
 */
@Configuration
@ConditionalOnClass(RedisConnectionFactory.class)
public class RedisAutoConfiguration
{
    private final Logger logger = LoggerFactory.getLogger(RedisAutoConfiguration.class);
    
    @Bean
    @ConfigurationProperties(prefix = "ore.redis")
    public JedisPoolConfig getRedisConfig()
    {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }
    
    @Bean
    @ConfigurationProperties(prefix = "ore.redis")
    public JedisConnectionFactory getConnectionFactory()
    {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        logger.info("JedisConnectionFactory bean init success.");
        return factory;
    }
    
    @Bean
    public RedisTemplate<?, ?> getRedisTemplate()
    {
        RedisTemplate<?, ?> template = new StringRedisTemplate(getConnectionFactory());
        return template;
    }
    
    @Bean
    public IRedisService redisService(RedisTemplate<String, ?> redisTemplate)
    {
        return new RedisServiceImpl(redisTemplate);
    }
}
