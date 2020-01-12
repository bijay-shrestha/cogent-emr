package com.cogent.adminservice.service.impl;

import com.cogent.adminservice.repository.UserRepository;
import com.cogent.adminservice.service.UserService;
import com.cogent.persistence.model.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService<Admin, Long> {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Admin user) {
        repository.save(user);

    }

    @Override
    public Admin getByID(Long t) {
        return repository.findById(t).get();
    }

    @Override
    public List<Admin> getAll() {
        return null;
    }
}
