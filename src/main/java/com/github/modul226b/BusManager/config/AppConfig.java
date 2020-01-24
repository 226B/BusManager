package com.github.modul226b.BusManager.config;

import com.github.modul226b.BusManager.manager.DataManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.github.modul226b.BusManager.manager")
public class AppConfig {
    @Bean
    @Scope("singleton")
    public DataManager dataManager() {
        return new DataManager();
    }
}
