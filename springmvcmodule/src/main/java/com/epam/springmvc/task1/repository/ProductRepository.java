package com.epam.springmvc.task1.repository;

import com.epam.springmvc.task1.domain.Product;

import java.util.List;

public interface ProductRepository extends AppRepository<Product, Integer>  {

    List<Product> findAllByPage(int pageId, int total);

    Product findGoodByName(String name);

    boolean isExist(String name);

    int productCount();

}
