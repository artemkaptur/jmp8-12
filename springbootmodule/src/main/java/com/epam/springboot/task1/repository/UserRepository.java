package com.epam.springboot.task1.repository;

import com.epam.springboot.task1.domain.User;

public interface UserRepository extends AppRepository<User, Integer> {

    User findUserByLogin(String login);

}
