package com.cogent.adminservice.service.impl;

import com.cogent.adminservice.repository.UserRepository;
import com.cogent.adminservice.service.UserService;
import com.cogent.genericservice.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService<User, Long> {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(User user) {
        repository.save(user);

    }

    @Override
    public User getByID(Long t) {
        return repository.findById(t).get();
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
