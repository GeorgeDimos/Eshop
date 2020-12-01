package com.spring.eshop.dao;

import com.spring.eshop.entity.Category;

import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDAO extends ItemDAO<Category, Integer> {
}
