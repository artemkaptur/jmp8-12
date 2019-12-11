package com.epam.springboot.task1.repository;

import com.epam.springboot.task1.domain.Good;

import java.util.List;

public interface GoodRepository extends AppRepository<Good, Integer> {

    List<Good> findAllByPage(int pageId, int total);

}
