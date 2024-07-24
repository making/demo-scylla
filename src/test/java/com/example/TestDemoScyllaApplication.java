package com.example;

import org.springframework.boot.SpringApplication;

public class TestDemoScyllaApplication {

	public static void main(String[] args) {
		SpringApplication.from(DemoScyllaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
