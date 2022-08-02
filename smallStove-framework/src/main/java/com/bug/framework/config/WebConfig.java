package com.bug.framework.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: 设置springboot允许跨域
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 配置fastjson
     */
    @Bean
    public HttpMessageConverter<?> fastJsonHttpMessageConverter() {
        //1.需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置springboot允许跨域
        registry.addMapping("/**")
                // 允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 允许跨域请求的方法
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 允许cookie
                .allowCredentials(true)
                // 允许的请求头
                .allowedHeaders("*")
                // 跨域时间
                .maxAge(3600)
        ;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter());
    }

}
