package com.spring.eshop.controller;

import com.spring.eshop.dao.ImageDAO;
import com.spring.eshop.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/images")
public class ImagesController {

	private final ImageDAO imageDAO;

	@Autowired
	public ImagesController(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}

	@GetMapping("/{id}")
	public ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable int id) {
		Image img = imageDAO.findById(id).orElseThrow();
		return new ResponseEntity<>(img.getData(), null, HttpStatus.OK);
	}
}
