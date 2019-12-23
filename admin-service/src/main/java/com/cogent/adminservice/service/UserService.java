package com.cogent.adminservice.service;

import java.util.List;

public interface UserService<User,Long> {

    void save(User t);
    User getByID(Long t);
    List<User> getAll();
}

