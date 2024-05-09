package com.example.demo.configuration;

import com.example.demo.util.ConvertUtil;
import org.aspectj.asm.IModelFilter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public <T,R> R convertData(T t, R r){
        ConvertUtil.convertObject(t, object -> {
           modelMapper().map(t, r);
           return r;
        });
        return null;
    }
}
