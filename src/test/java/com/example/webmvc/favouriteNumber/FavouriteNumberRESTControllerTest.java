package com.example.webmvc.favouriteNumber;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url=jdbc:h2:mem:testdb", "spring.jpa.hibernate.ddl-auto=create-drop"})
class FavouriteNumberRESTControllerTest {
    @Autowired
    private FavouriteNumberRESTController controller;

    @Autowired
    private FavouriteNumberRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    @Test
    public void contextLoads() throws AssertionError {
        assertThat(controller).isNotNull();
    }

    @Test
    void getPaginated() {
        repository.deleteAll();
        for (int i = 0; i < 7; i++) {
            createNumber();
        }

        var requestSize = 5;
        FavouriteNumberRESTDto[] response = this.restTemplate.getForObject(getURL() + "?page=0&result_count=" + requestSize,
                FavouriteNumberRESTDto[].class);
        assertThat(response.length).isEqualTo(requestSize);
    }

    @Test
    void getByID() {
        FavouriteNumber number = createNumber();

        var url = getURLWithID(number.getId());
        FavouriteNumberRESTDto response = this.restTemplate.getForObject(url, FavouriteNumberRESTDto.class);
        assertThat(response.getId()).isEqualTo(number.getId());
        assertThat(response.getNumber()).isEqualTo(number.getNumber());
        assertThat(response.getUsername()).isNull();
    }

    @Test
    void deleteByID() {
        FavouriteNumber number = createNumber();

        this.restTemplate.delete(getURLWithID(number.getId()));

        var findResult = repository.findById(number.getId());
        assertThat(findResult).isEmpty();
    }

    @Test
    void put() {
        FavouriteNumber number = createNumber();

        FavouriteNumberRESTDto newNumber = new FavouriteNumberRESTDto(number.getId(), number.getNumber(), "");
        newNumber.setNumber(ThreadLocalRandom.current().nextInt());
        String url = getURLWithID(number.getId());
        this.restTemplate.put(url, newNumber);

        var findResult = repository.findById(newNumber.getId());
        assertThat(findResult).isPresent();
        assertThat(findResult.get().getNumber()).isEqualTo(newNumber.getNumber());
    }

    @Test
    void post() {
        var randomNumber = ThreadLocalRandom.current().nextInt();
        var number = new FavouriteNumberRESTDto(randomNumber);

        var response = this.restTemplate.postForEntity(getURL(), number, FavouriteNumberRESTDto.class);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var findResult = repository.findById(response.getBody().getId());
        assertThat(findResult).isPresent();
        assertThat(findResult.get().getNumber()).isEqualTo(number.getNumber());
        assertThat(findResult.get().getId()).isEqualTo(response.getBody().getId());
        assertThat(findResult.get().getUser()).isNull();
    }

    private FavouriteNumber createNumber() {
        var number = ThreadLocalRandom.current().nextInt();
        return repository.saveAndFlush(new FavouriteNumber(number));
    }

    private String getURLWithID(Long id) {
        return getURL() + id;
    }

    private String getURL() {
        return String.format("http://localhost:%d/api/", port);
    }
}