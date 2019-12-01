package com.epam.springmvc.task1.service.impl;

import com.epam.springmvc.task1.repository.PurchaseRepository;
import com.epam.springmvc.task1.domain.Purchase;
import com.epam.springmvc.task1.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements AppService<Purchase, Integer> {

    private static final String ASCENDING_ORDER = "ASC";
    private static final String DESCENDING_ORDER = "DESC";
    private static final String REQUEST_PARAM_ASCENDING = "asc";
    private static final String REQUEST_PARAM_DESCENDING = "desc";

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

    public List<Purchase> findPurchasesByUserId(Integer userPrimaryKey) {
        return purchaseRepository.findPurchasesByUserIdOrdered(userPrimaryKey, null);
    }

    public List<Purchase> findSortedPurchasesByUserId(Integer userPrimaryKey, String order) {
        String sortingOrder = null;
        switch (order) {
            case REQUEST_PARAM_ASCENDING:
                sortingOrder = ASCENDING_ORDER;
                break;
            case REQUEST_PARAM_DESCENDING:
                sortingOrder = DESCENDING_ORDER;
                break;
        }
        return purchaseRepository.findPurchasesByUserIdOrdered(userPrimaryKey, sortingOrder);
    }

}
