package com.example.oro;

import com.example.oro.Entity.*;
import com.example.oro.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class OroApplicationTests {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PartRepository partRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	private UserModel userModel1;
	private UserModel userModel2;
	private Category category1;
	private Category category2;
	private Part part1;
	private Part part2;
	private Part part3;
	private Car car1;
	private Car car2;
	private Car car3;
	private Order order1;
	private Order order2;
	private Order order3;

	@BeforeEach
	void setup() {
		userModel1 = userRepository.save(UserModel.builder().name("Jan").email("kowalski@mail.po").build());
		userModel2 = userRepository.save(UserModel.builder().name("Maciej").email("nowak@mail.com").build());

		category1 = categoryRepository.save(Category.builder().name("Kategoria 1").build());
		category2 = categoryRepository.save(Category.builder().name("Kategoria 2").build());

		part1 = partRepository.save(Part.builder().title("part 1").category(category1).price(10000).build());
		part2 = partRepository.save(Part.builder().title("part 2").category(category2).price(90).build());
		part3 = partRepository.save(Part.builder().title("part 3").category(category1).price(150).build());

		List<Part> parts = new ArrayList<>();
		parts.add(part1);
		parts.add(part2);
		parts.add(part3);
		car1 = carRepository.save(Car.builder().brand("Marka 1").model("Model 1").releaseYear(2012).parts(parts).build());
		car2 = carRepository.save(Car.builder().brand("Marka 1").model("Model 2").releaseYear(2015).parts(parts).build());
		car3 = carRepository.save(Car.builder().brand("Marka 2").model("Model 1").releaseYear(2022).parts(parts).build());

		List<LineItem> lineItems = new ArrayList<>();
		lineItems.add(LineItem.builder().part(part1).quantity(2).build());
		lineItems.add(LineItem.builder().part(part3).quantity(8).build());

		order1 = orderRepository.save(
				Order.builder()
						.date(new Date())
						.paid(true)
						.buyer(userModel1)
						.lineItems(lineItems)
						.build()
		);

		List<LineItem> lineItems1 = new ArrayList<>();
		lineItems1.add(LineItem.builder().part(part1).quantity(2).build());
		lineItems1.add(LineItem.builder().part(part2).quantity(8).build());

		order2 = orderRepository.save(
				Order.builder()
						.date(new Date())
						.paid(false)
						.buyer(userModel1)
						.lineItems(lineItems1)
						.build()
		);

		List<LineItem> lineItems2 = new ArrayList<>();
		lineItems2.add(LineItem.builder().part(part2).quantity(2).build());
		lineItems2.add(LineItem.builder().part(part3).quantity(8).build());

		order3 = orderRepository.save(
				Order.builder()
						.date(new Date())
						.paid(true)
						.buyer(userModel2)
						.lineItems(lineItems2)
						.build()
		);
	}
	@Test
	void shouldFindAllOrders() {
		long orderCount = orderRepository.count();

		assertEquals(3, orderCount);
	}
	@Test
	void shouldFindOrdersByPart() {
		List<Order> orders = orderRepository.findByLineItems_Part(part2);
		for (Order order : orders){
			System.out.println(order);
		}

		assertEquals(2, orders.size());
	}

	@Test
	void shouldFindOrdersByUser() {
		List<Order> orders = orderRepository.findByBuyer(userModel1);

		assertEquals(2, orders.size());
	}

	@Test
	void shouldFindUserByEmail() {
		UserModel user = userRepository.findByEmail(userModel1.getEmail());

		assertEquals(userModel1, user);
	}

	@Test
	void shouldFindPartsByPriceRange() {
		List<Part> parts = partRepository.findByPriceBetween(0, 1000);

		assertEquals(2, parts.size());
	}

}
