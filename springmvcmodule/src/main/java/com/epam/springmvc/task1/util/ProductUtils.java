package com.epam.springmvc.task1.util;

import com.epam.springmvc.task1.domain.Product;
import com.epam.springmvc.task1.domain.Purchase;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ProductUtils {

    public static List<Product> getProducts(List<Purchase> purchaseList) {
        return purchaseList.stream()
                .map(Purchase::getProducts)
                .flatMap(Collection::stream)
                .collect(toList());
    }

}
