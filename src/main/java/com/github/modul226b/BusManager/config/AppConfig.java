package com.github.modul226b.BusManager.config;

import com.github.modul226b.BusManager.datahandeling.JsonDataHandler;
import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.ServiceManager;
import com.github.modul226b.BusManager.manager.TripManager;
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
        return new DataManager(new JsonDataHandler("data.json"));
    }
    @Bean
    @Scope("singleton")
    public BusManager busManager() {
        return new BusManager(dataManager());
    }
    @Bean
    @Scope("singleton")
    public TripManager tripManager() {
        return new TripManager(dataManager(), busManager());
    }
    @Bean
    @Scope("singleton")
    public ServiceManager serviceManager() {
        return new ServiceManager(dataManager(), busManager(), tripManager());
    }
}
