package ir.netrira.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Value("${core.redis.pass}")
    private String redisPassword;
    @Value("${core.redis.hostname}")
    private String redisHostname;
    @Value("${core.redis.port}")
    private Integer redisPort;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(redisHostname);
        jedisConFactory.setPort(redisPort);
        jedisConFactory.setPassword(redisPassword);
        jedisConFactory.setUsePool(Boolean.TRUE);
        jedisConFactory.getPoolConfig().setMaxIdle(30);
        jedisConFactory.getPoolConfig().setMaxTotal(200);
        jedisConFactory.getPoolConfig().setMinIdle(10);
        jedisConFactory.getPoolConfig().setBlockWhenExhausted(true);
        jedisConFactory.getPoolConfig().setMaxWaitMillis(1000);
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setEnableTransactionSupport(Boolean.TRUE);
        return template;
    }
}
