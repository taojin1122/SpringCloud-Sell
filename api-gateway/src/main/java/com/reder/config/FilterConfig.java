package com.reder.config;

import com.reder.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }
}
