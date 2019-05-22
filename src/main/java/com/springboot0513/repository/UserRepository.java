package com.springboot0513.repository;

import com.springboot0513.entity.UserDO;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<UserDO,Long> {
}
