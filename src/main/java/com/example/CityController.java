package com.example;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.util.IdGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

	private final CityRepository cityRepository;

	private final IdGenerator idGenerator;

	public CityController(CityRepository cityRepository, IdGenerator idGenerator) {
		this.cityRepository = cityRepository;
		this.idGenerator = idGenerator;
	}

	@GetMapping(path = "/cities")
	public List<City> getCities() {
		return this.cityRepository.findAll();
	}

	@PostMapping(path = "/cities")
	@ResponseStatus(HttpStatus.CREATED)
	public City postCities(@RequestBody City city) {
		City created = new City(this.idGenerator.generateId(), city.name());
		return cityRepository.save(created);
	}

}