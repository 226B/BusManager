package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.service.AbstractService;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ServiceManager {

    private HashMap<Class<? extends AbstractService>, AbstractService> serviceHashMap;

    public ServiceManager() {
        serviceHashMap = new HashMap<>();

        for (AbstractService loadService : loadServices()) {
            serviceHashMap.put(loadService.getClass(), loadService);
        }
    }

    private void addService(AbstractService service) {
        serviceHashMap.put(service.getClass(), service);
    }

    private List<AbstractService> loadServices() {
        List<AbstractService> result = new ArrayList<>();

        Reflections reflections = new Reflections("com.github.modul226b.BusManager.service.services");
        Set<Class<? extends AbstractService>> classes;
        classes = reflections.getSubTypesOf(AbstractService.class);
        for (Class<? extends AbstractService> c : classes) {
            try {
                AbstractService e = c.newInstance();
                result.add(e);

                if (e.startOnStartUp()) {
                    e.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public AbstractService get(Class<? extends AbstractService> clazz) {
        return serviceHashMap.getOrDefault(clazz, null);
    }
}
