package com.epam.springmvc.task1.repository;

import com.epam.springmvc.task1.domain.User;

public interface UserRepository extends AppRepository<User, Integer> {

    User findUserByLogin(String login);

    boolean isExist(User user);
}
