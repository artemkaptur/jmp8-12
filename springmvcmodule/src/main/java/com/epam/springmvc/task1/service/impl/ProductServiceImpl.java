package com.epam.springmvc.task1.service.impl;

import com.epam.springmvc.task1.repository.ProductRepository;
import com.epam.springmvc.task1.domain.Product;
import com.epam.springmvc.task1.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements AppService<Product, Integer> {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean add(Product entity) {
        return productRepository.add(entity);
    }

    public Product findById(Integer primaryKey) {
        return productRepository.findById(primaryKey);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void update(Product entity) {
        productRepository.update(entity);
    }

    public void delete(Integer primaryKey) {
        productRepository.delete(primaryKey);
    }

    public List<Product> findAllByPage(int pageId, int total) {
        return productRepository.findAllByPage(pageId, total);
    }

    public Product findByName(String name) {
        return productRepository.findGoodByName(name);
    }

    public boolean isExist(String name) {
        return productRepository.isExist(name);
    }

    public int productCount() {
        return productRepository.productCount();
    }

}
