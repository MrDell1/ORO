package com.example.oro;

import com.example.oro.Entity.Car;
import com.example.oro.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OroApplicationTests {

	@Autowired
	private CarRepository carRepository;

	@Test
	void contextLoads() {
		Car car = new Car();
		carRepository.save(car);

		List<Car> cars = carRepository.findAll();

		for (Car car1:cars
		) {
			System.out.println(car1);
		}
	}

}
