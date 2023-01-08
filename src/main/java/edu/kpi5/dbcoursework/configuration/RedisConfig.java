package edu.kpi5.dbcoursework.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Configuration
@ComponentScan("edu.kpi5.dbcoursework")
@EnableRedisRepositories(basePackages = "edu.kpi5.dbcoursework.dbaccess.workdb")
@PropertySource("classpath:application.properties")
public class RedisConfig {
    @Bean
    JedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}

