package com.epam.springboot.task1.service.impl;

import com.epam.springboot.task1.domain.Good;
import com.epam.springboot.task1.repository.GoodRepository;
import com.epam.springboot.task1.service.AppService;
//import io.micrometer.core.instrument.Counter;
//import io.micrometer.core.instrument.Meter;
//import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

//import static io.micrometer.core.instrument.Meter.Type.COUNTER;

@Service
public class GoodServiceImpl implements AppService<Good, Integer> {

//    @Bean
//    MeterRegistryCustomizer<MeterRegistry> addPersonRegistry() {
//        return registry -> registry.config().namingConvention().name("counter.good.delivered", COUNTER);
//    }
//
//    private final Counter goodDeliveredCounter;

    private GoodRepository goodRepository;

    @Autowired
    public GoodServiceImpl(GoodRepository goodRepository
            //, MeterRegistry registry
    ) {
        this.goodRepository = goodRepository;
        //this.goodDeliveredCounter = registry.counter("counter.good.delivered");
    }

    public boolean add(Good entity) {
        return goodRepository.add(entity);
    }

    public Good findById(Integer primaryKey) {
        return goodRepository.findById(primaryKey);
    }

    public List<Good> findAll() {
        return goodRepository.findAll();
    }

    public void update(Good entity) {
        goodRepository.update(entity);
        //goodDeliveredCounter.increment();
    }

    public void delete(Integer primaryKey) {
        goodRepository.delete(primaryKey);
    }

    public List<Good> findAllByPage(int pageId, int total) {
        return goodRepository.findAllByPage(pageId, total);
    }

}
