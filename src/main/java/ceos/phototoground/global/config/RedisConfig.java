package ceos.phototoground.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories  // Redis를 사용하한 다고 명시해 주는 annotation
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;


    // yaml에 저장한 host, post를 연결
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {  // 이 객체는 Redis Java 클라이언트 라이브러리인 Lettuce를 사용해서 Redis 서버와 연결해줌
        return new LettuceConnectionFactory(host, port);
    }

    // serializer 설정으로 redis-cli를 통해 직접 데이터를 조회할 수 있도록 설정(Redis CLI를 사용해 Redis 데이터를 직접 조회할 때, Redis 데이터를 문자열로 반화해야하기 때문에 설정)
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {  // RedisTemplate 객체를 생성하여 반환 (RedisTemplate : Redis 데이터를 저장하고 조회하는 기능)
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        return redisTemplate;
    }
}
