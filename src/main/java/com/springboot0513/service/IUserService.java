package com.springboot0513.service;

import com.springboot0513.entity.UserDO;

import java.util.List;

public interface IUserService {
    public void insert(UserDO userDO);
    public List<UserDO> findAll();
    void update(UserDO user);
    void delete(Long id);
    UserDO get(Long id);
}
