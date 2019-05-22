package com.springboot0513.service.impl;

import com.springboot0513.entity.UserDO;
import com.springboot0513.mybatis.mapper.UserMapper;
import com.springboot0513.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    /*@Autowired
    private UserRepository userRepository;*/
  /*  @Override
    public void insert(UserDO userDO) {

        userRepository.save(userDO);
    }

    @Override
    public List<UserDO> findAll() {

        return (List<UserDO>) userRepository.findAll();
    }*/
    @Override
    public void insert(UserDO userDO) {

        userMapper.insert(userDO);
    }

    @Override
    public List<UserDO> findAll() {

        return userMapper.findAll();
    }

    @Override
    public void update(UserDO user) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserDO get(Long id) {
        return null;
    }

}
