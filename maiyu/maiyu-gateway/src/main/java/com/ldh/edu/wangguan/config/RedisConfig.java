package com.ldh.edu.wangguan.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> template(RedisConnectionFactory factory){
        //创建redisTemplate<String,Object>对象
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        //配置连接工厂
        template.setConnectionFactory(factory);
        //定义Jackson2JsonRedisSerializer序列化器
        Jackson2JsonRedisSerializer<Object> jacksonSerial = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        //指定要序列化的域，field，get和set，以及修饰符的范围，any是同时包括private与public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //指定序列化输入的类型，类必须是非final的，final修饰的入String，Integer类都会报异常
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSerial.setObjectMapper(objectMapper);
        StringRedisSerializer serializer = new StringRedisSerializer();
        //redis key 使用stringSerial
        template.setKeySerializer(serializer);
        //redis value 序列化使用jackson
        template.setValueSerializer(jacksonSerial);
        template.afterPropertiesSet();
        return template;

    }

}
