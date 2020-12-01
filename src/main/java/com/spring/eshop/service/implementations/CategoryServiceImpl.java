package com.spring.eshop.service.implementations;

import java.util.Set;

import com.spring.eshop.entity.Category;
import com.spring.eshop.service.CategoryService;

import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BasicServicesImpl<Category, Integer> implements CategoryService {

    private final static Set<String> fields = Set.of("products");

    @Override
    public final Set<String> getClassFields() {
        return fields;
    }

}
