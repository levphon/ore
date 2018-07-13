package org.oreframework.kafka.receiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

/**
 * kafka 消费消息抽象类
 * @author huangzz
 * 2017年1月16日
 */
public abstract class MessageReceiver
{
    
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);
    
    /**
     * consumerConfig
     */
    @Autowired
    protected ConsumerConfig consumerConfig;
    
    @Autowired
    private KafkaProperties kafkaProperties;
    
    /**
     * topic
     */
    protected String topic;
    
    /**
     * nThreads
     */
    protected String consumerThreads;
    
    /**
     * connector
     */
    protected kafka.javaapi.consumer.ConsumerConnector connector;
    
    /**
     * topicCountMap
     */
    protected Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
    
    /**
     * 实现方法
     * 
     * @param topic topic
     * @param message message
     */
    public abstract void processMessage(String topic, byte[] message);
    
    /**
     * init
     */
    @PostConstruct
    protected void init()
    {
        
        connector = Consumer.createJavaConsumerConnector(consumerConfig);
        int threadCnt = 1;
        consumerThreads = kafkaProperties.getProperties().get("nThreads");
        
        if (null != consumerThreads && Integer.parseInt(consumerThreads) > 0)
        {
            threadCnt = Integer.parseInt(consumerThreads);
        }
        topic = kafkaProperties.getProperties().get("topic");
        String[] topics = topic.trim().split(",");
        for (int i = 0; i < topics.length; i++)
        {
            topicCountMap.put(topics[i], threadCnt);
        }
        
        Map<String, List<KafkaStream<byte[], byte[]>>> streams = connector.createMessageStreams(topicCountMap);
        
        List<KafkaStream<byte[], byte[]>> list = new ArrayList<KafkaStream<byte[], byte[]>>();
        for (String ss : topics)
        {
            list.addAll(streams.get(ss));
        }
        
        final ExecutorService executor = Executors.newFixedThreadPool(threadCnt * topics.length);
        for (final KafkaStream<byte[], byte[]> kafkaStream : list)
        {
            executor.submit(new Runnable()
            {
                public void run()
                {
                    ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
                    while (it.hasNext())
                    {
                        MessageAndMetadata<byte[], byte[]> item = it.next();
                        byte[] message = item.message();
                        
                        try
                        {
                            processMessage(item.topic(), message);
                        }
                        catch (Exception e)
                        {
                            LOGGER.error(e.getMessage());
                        }
                        
                    }
                }
            });
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
        {
            public void run()
            {
                executor.shutdown();
            }
        }));
    }
    
    public ConsumerConfig getConsumerConfig()
    {
        return consumerConfig;
    }
    
    public void setConsumerConfig(ConsumerConfig consumerConfig)
    {
        this.consumerConfig = consumerConfig;
    }

    
    /**
     * 销毁
     */
    @PreDestroy
    public void destroy()
    {
        if (null != connector)
        {
            connector.shutdown();
        }
    }
}
