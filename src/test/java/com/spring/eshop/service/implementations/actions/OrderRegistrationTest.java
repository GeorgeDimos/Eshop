package com.spring.eshop.service.implementations.actions;

import com.spring.eshop.dao.OrderDAO;
import com.spring.eshop.dao.ProductDAO;
import com.spring.eshop.entity.Category;
import com.spring.eshop.entity.Product;
import com.spring.eshop.entity.User;
import com.spring.eshop.entity.UserInfo;
import com.spring.eshop.events.OrderReceivedEvent;
import com.spring.eshop.exceptions.NotEnoughStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderRegistrationTest {

	@Mock
	OrderDAO orderDAO;
	@Mock
	ProductDAO productDAO;
	@Mock
	ApplicationEventPublisher publisher;
	@InjectMocks
	OrderRegistration orderRegistration;

	Map<Product, Integer> shoppingCart;
	User user;

	@BeforeEach
	void setUp() {
		shoppingCart = Map.of(
				new Product(1, "p1", "d1", 2, 10, null, mock(Category.class)), 1,
				new Product(2, "p2", "d2", 4, 14, null, mock(Category.class)), 3
		);
		user = new User(1, "u", "p", true, null,
				new UserInfo(1, "f", "l", "email@mail.gr", null)
				, null);
	}

	@Test
	void execute() {
		given(productDAO.order(gt(0), gt(0))).willReturn(1);
		int result = orderRegistration.execute(user, shoppingCart);
		assertThat(result).isNotNull();
		verify(productDAO, times(shoppingCart.size())).order(gt(0), gt(0));
		verify(publisher).publishEvent(any(OrderReceivedEvent.class));
	}

	@Test
	void executeNotEnoughStock() {
		given(productDAO.order(gt(0), gt(0))).willReturn(0);

		assertThrows(NotEnoughStockException.class, () -> {
			orderRegistration.execute(user, shoppingCart);
		});

		verify(productDAO).order(gt(0), gt(0));
		then(productDAO).shouldHaveNoMoreInteractions();
		verify(publisher, never()).publishEvent(any(OrderReceivedEvent.class));
	}
}