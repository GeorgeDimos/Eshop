package com.spring.eshop.dao;

import com.spring.eshop.entity.Image;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDAO extends CrudRepository<Image, Integer> {
}
