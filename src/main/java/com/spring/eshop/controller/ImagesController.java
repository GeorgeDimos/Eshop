package com.spring.eshop.controller;

import java.util.Optional;

import com.spring.eshop.dao.ImageDAO;
import com.spring.eshop.entity.Image;

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
		Optional<Image> img = imageDAO.findById(id);
		if (img.isPresent()) {
			return new ResponseEntity<>(img.get().getData(), null, HttpStatus.OK);
		}

		return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
	}

}
