package com.spring.eshop.service;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SortingFilter implements Filter {

	private Set<String> invalidFields;

	public SortingFilter(Set<String> invalidFields) {
		this.invalidFields = invalidFields;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String[] parameters = request.getParameterValues("sort");
		if (parameters != null) {
			for (String parameter : parameters) {
				if (invalidFields.contains(parameter)) {
					throw new IOException("Invalid sort");
				}
			}
		}

		chain.doFilter(request, response);
	}

}
