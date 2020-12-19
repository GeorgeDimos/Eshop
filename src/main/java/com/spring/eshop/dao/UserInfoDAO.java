package com.spring.eshop.dao;

import com.spring.eshop.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDAO extends CrudRepository<UserInfo, Integer> {
}
