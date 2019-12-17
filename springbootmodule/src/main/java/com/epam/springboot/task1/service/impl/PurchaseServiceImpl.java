package com.epam.springboot.task1.service.impl;

import com.epam.springboot.task1.repository.PurchaseRepository;
import com.epam.springboot.task1.service.AppService;
import com.epam.springboot.task1.domain.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements AppService<Purchase, Integer> {

    private PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public boolean add(Purchase entity) {
        return purchaseRepository.add(entity);
    }

    public Purchase findById(Integer primaryKey) {
        return purchaseRepository.findById(primaryKey);
    }

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public void update(Purchase entity) {
        purchaseRepository.update(entity);
    }

    public void delete(Integer primaryKey) {
        purchaseRepository.delete(primaryKey);
    }

    public List<Purchase> findByUserId(Integer userPrimaryKey) {
        return purchaseRepository.findByUserId(userPrimaryKey);
    }

}
