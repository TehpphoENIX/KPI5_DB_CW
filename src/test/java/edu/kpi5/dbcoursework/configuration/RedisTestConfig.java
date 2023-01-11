package edu.kpi5.dbcoursework.configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.test.context.TestPropertySource;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
@ComponentScan("edu.kpi5.dbcoursework")
@EnableRedisRepositories(basePackages = "edu.kpi5.dbcoursework.dbaccess.workdb")
@PropertySource("classpath:application.properties")
public class RedisTestConfig {
    @Value("${spring.redis.host}")
    private String redisHostName;
    @Value("${spring.redis.port}")
    private int redisPort;
    @Value("${spring.redis.database}")
    private int database;

    @Bean
    JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setDatabase(database);
        config.setHostName(redisHostName);
        config.setPort(redisPort);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public RedisServerCommands redisServerCommands() {
        return redisConnectionFactory().getConnection();
    }
}

