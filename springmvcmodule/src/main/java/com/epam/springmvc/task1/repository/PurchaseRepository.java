package com.epam.springmvc.task1.repository;

import com.epam.springmvc.task1.domain.Purchase;

import java.util.List;

public interface PurchaseRepository extends AppRepository<Purchase, Integer>  {


    List<Purchase> findPurchasesByUserIdOrdered(Integer userPrimaryKey, String nullableSortingOrder);
}
