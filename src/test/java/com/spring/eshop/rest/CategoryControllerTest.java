package com.spring.eshop.rest;

import com.spring.eshop.dao.CategoryDAO;
import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.*;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@WithMockUser(authorities = "admin")
class CategoryControllerTest {

	@Autowired
	MockMvc mockMvc;
	@MockBean
	private CategoryDAO categoryDAO;
	@SpyBean
	private CategoryModelAssembler assembler;

	@Test
	@WithAnonymousUser
	void accessWithoutUser() throws Exception {
		mockMvc.perform(get("/api/categories"))
						.andExpect(status().is4xxClientError());
	}

	@Nested
	@WithMockUser(authorities = "admin")
	class MultipleCategories {
		Category cat1;
		Category cat2;


		@BeforeEach
		void setUp() {
			cat1 = new Category(1, "cat1", Collections.emptyList());

			Product e1 = new Product(1, "product1", "description1", 4, 25, Collections.emptyList(), cat1);
			Product e2 = new Product(2, "product2", "description2", 3, 12, Collections.emptyList(), cat1);

			cat1.setProducts(List.of(e1, e2));

		}

		@Test
		void getCategories() throws Exception {
			cat2 = new Category(2, "cat2", Collections.emptyList());
			Product e3 = new Product(3, "product3", "description3", 1, 14, Collections.emptyList(), cat2);
			cat2.setProducts(List.of(e3));
			given(categoryDAO.findAll()).willReturn(List.of(cat1, cat2));
			mockMvc.perform(get("/api/categories"))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$[*]", hasSize(2)))
							.andExpect(jsonPath("$._embedded.categories[0].id", is(cat1.getId())))
							.andExpect(jsonPath("$._embedded.categories[0].name", is(cat1.getName())))
							.andExpect(jsonPath("$._embedded.categories[0].products", hasSize(cat1.getProducts().size())))
							.andExpect(jsonPath("$._embedded.categories[0].products[0].id", is(cat1.getProducts().get(0).getId())))
							.andExpect(jsonPath("$._embedded.categories[0].products[0].name", is(cat1.getProducts().get(0).getName())))
							.andExpect(jsonPath("$._embedded.categories[0].products[0].description", is(cat1.getProducts().get(0).getDescription())))
							.andExpect(jsonPath("$._embedded.categories[0].products[0].stock", is(cat1.getProducts().get(0).getStock())))
							.andExpect(jsonPath("$._embedded.categories[0].products[0].price", is(cat1.getProducts().get(0).getPrice())))
							.andExpect(jsonPath("$._embedded.categories[0].products[1].id", is(cat1.getProducts().get(1).getId())))
							.andExpect(jsonPath("$._embedded.categories[0].products[1].name", is(cat1.getProducts().get(1).getName())))
							.andExpect(jsonPath("$._embedded.categories[0].products[1].description", is(cat1.getProducts().get(1).getDescription())))
							.andExpect(jsonPath("$._embedded.categories[0].products[1].stock", is(cat1.getProducts().get(1).getStock())))
							.andExpect(jsonPath("$._embedded.categories[0].products[1].price", is(cat1.getProducts().get(1).getPrice())))
							.andExpect(jsonPath("$._embedded.categories[1].id", is(cat2.getId())))
							.andExpect(jsonPath("$._embedded.categories[1].name", is(cat2.getName())))
							.andExpect(jsonPath("$._embedded.categories[1].products", hasSize(cat2.getProducts().size())))
							.andExpect(jsonPath("$._embedded.categories[1].products[0].id", is(cat2.getProducts().get(0).getId())))
							.andExpect(jsonPath("$._embedded.categories[1].products[0].name", is(cat2.getProducts().get(0).getName())))
							.andExpect(jsonPath("$._embedded.categories[1].products[0].description", is(cat2.getProducts().get(0).getDescription())))
							.andExpect(jsonPath("$._embedded.categories[1].products[0].stock", is(cat2.getProducts().get(0).getStock())))
							.andExpect(jsonPath("$._embedded.categories[1].products[0].price", is(cat2.getProducts().get(0).getPrice())))
							.andExpect(jsonPath("$._links.self.href", containsString("/api/categories")))
							.andExpect(jsonPath("$._links.api.href", containsString("/api")))
			;
		}

		@Test
		void getCategory() throws Exception {
			given(categoryDAO.findById(gt(0))).willReturn(Optional.of(cat1));
			mockMvc.perform(get("/api/categories/{id}", 1))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$.id", is(cat1.getId())))
							.andExpect(jsonPath("$.name", is(cat1.getName())))
							.andExpect(jsonPath("$.products", hasSize(cat1.getProducts().size())))
							.andExpect(jsonPath("$.products[0].id", is(cat1.getProducts().get(0).getId())))
							.andExpect(jsonPath("$.products[0].name", is(cat1.getProducts().get(0).getName())))
							.andExpect(jsonPath("$.products[0].description", is(cat1.getProducts().get(0).getDescription())))
							.andExpect(jsonPath("$.products[0].stock", is(cat1.getProducts().get(0).getStock())))
							.andExpect(jsonPath("$.products[0].price", is(cat1.getProducts().get(0).getPrice())))
							.andExpect(jsonPath("$.products[0].categoryId", is(cat1.getProducts().get(0).getCategory().getId())))
							.andExpect(jsonPath("$.products[1].id", is(cat1.getProducts().get(1).getId())))
							.andExpect(jsonPath("$.products[1].name", is(cat1.getProducts().get(1).getName())))
							.andExpect(jsonPath("$.products[1].description", is(cat1.getProducts().get(1).getDescription())))
							.andExpect(jsonPath("$.products[1].stock", is(cat1.getProducts().get(1).getStock())))
							.andExpect(jsonPath("$.products[1].price", is(cat1.getProducts().get(1).getPrice())))
							.andExpect(jsonPath("$.products[1].categoryId", is(cat1.getProducts().get(1).getCategory().getId())))
			;
		}
	}

	@Nested
	@WithMockUser(authorities = "admin")
	class ValidCategory {

		Category category;

		@BeforeEach
		void setUp() {
			category = new Category(1, "Created Category", Collections.emptyList());
		}

		@Test
		void addCategory() throws Exception {
			String createdCategory = "{\"id\":1,\"name\":\"Created Category\", \"products\":[]}";
			given(categoryDAO.save(ArgumentMatchers.any(Category.class))).willReturn(category);

			mockMvc.perform(post("/api/categories")
							.contentType(MediaType.APPLICATION_JSON)
							.content(createdCategory)
			)
							.andDo(print())
							.andExpect(status().isCreated())
							.andExpect(MockMvcResultMatchers.content().json(createdCategory))
			;
		}

		@Nested
		@WithMockUser(authorities = "admin")
		class EditValidCategory {

			String editedCategory = "{\"id\":1,\"name\":\"Edited Category\", \"products\":[]}";

			@BeforeEach
			void setUp() {
				reset(categoryDAO);
			}

			@Test
			void editCategory() throws Exception {
				given(categoryDAO.findById(gt(0))).willReturn(Optional.of(category));
				Category edited = category;
				edited.setName("Edited Category");
				given(categoryDAO.save(ArgumentMatchers.any(Category.class))).willReturn(edited);

				mockMvc.perform(put("/api/categories/{id}", 1)
								.contentType(MediaType.APPLICATION_JSON)
								.content(editedCategory)
				)
								.andDo(print())
								.andExpect(status().isOk())
								.andExpect(MockMvcResultMatchers.content().json(editedCategory))
				;
				verify(categoryDAO).save(ArgumentMatchers.any(Category.class));
			}

			@Test
			void editCategoryInvalidId() {
				given(categoryDAO.findById(gt(0))).willReturn(Optional.empty());

				int invalidId = 1;

				assertThatThrownBy(() ->
								mockMvc.perform(put("/api/categories/{id}", invalidId)
												.contentType(MediaType.APPLICATION_JSON)
												.content(editedCategory)
								)
												.andExpect(status().isBadRequest())
				).hasCause(new CategoryController.CategoryNotFoundException(invalidId));

				verify(categoryDAO, never()).save(ArgumentMatchers.any(Category.class));

			}
		}
	}

	@Nested
	@WithMockUser(authorities = "admin")
	class InvalidCategory {

		String invalidCategory = "{\"id\":1,\"name\":\"\", \"products\":[]}";

		@Test
		void addInvalidCategoryEmptyName() throws Exception {
			verifyNoInteractions(categoryDAO);
			mockMvc.perform(post("/api/categories")
							.contentType(MediaType.APPLICATION_JSON)
							.content(invalidCategory)
			)
							.andDo(print())
							.andExpect(status().isBadRequest())
			;
		}

		@Test
		void editCategoryEmptyName() throws Exception {
			verifyNoMoreInteractions(categoryDAO);
			mockMvc.perform(put("/api/categories/{id}", 1)
							.contentType(MediaType.APPLICATION_JSON)
							.content(invalidCategory)
			)
							.andDo(print())
							.andExpect(status().isBadRequest())
			;
		}

	}

	@Test
	void deleteCategory() throws Exception {
		mockMvc.perform(delete("/api/categories/1")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
		)
						.andExpect(status().isResetContent());
	}
}