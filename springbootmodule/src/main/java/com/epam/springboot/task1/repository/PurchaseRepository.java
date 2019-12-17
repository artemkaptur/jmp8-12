package com.epam.springboot.task1.repository;

import com.epam.springboot.task1.domain.Purchase;

import java.util.List;

public interface PurchaseRepository extends AppRepository<Purchase, Integer> {

    List<Purchase> findByUserId(Integer userPrimaryKey);

}
