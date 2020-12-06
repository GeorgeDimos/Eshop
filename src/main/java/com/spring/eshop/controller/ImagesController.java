package com.spring.eshop.controller;

import com.spring.eshop.dao.ImageDAO;
import com.spring.eshop.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ImagesController {

	private final ImageDAO imageDAO;

	@Autowired
	public ImagesController(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}

	@GetMapping("/images/{id}")
	public ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable int id) {
		Optional<Image> img = imageDAO.findById(id);
		return img.map(image -> new ResponseEntity<>(image.getData(), null, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND));
	}
}
