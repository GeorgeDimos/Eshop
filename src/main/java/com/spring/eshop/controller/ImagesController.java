package com.spring.eshop.controller;

import com.spring.eshop.dao.ImageDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImagesController {
    @Autowired
    private ImageDAO imageDAO;

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable int id) {

        return new ResponseEntity<>(imageDAO.findById(id).get().getData(), null, HttpStatus.OK);
    }

}
