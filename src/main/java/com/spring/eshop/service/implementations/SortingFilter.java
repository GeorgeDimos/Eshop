package com.spring.eshop.service.implementations;

import javax.servlet.*;
import java.io.IOException;
import java.util.Set;

public class SortingFilter implements Filter {

	private final Set<String> invalidFields;

	public SortingFilter(Set<String> invalidFields) {
		this.invalidFields = invalidFields;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String[] parameters = request.getParameterValues("sort");
		if (parameters != null) {
			for (String parameter : parameters) {
				for (String field : invalidFields) {
					if (parameter.contains(field)) {
						throw new IOException("Invalid sort");  // TODO: this is awkward
					}
				}
			}
		}

		chain.doFilter(request, response);
	}
}
