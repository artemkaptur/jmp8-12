package com.epam.springboot.task1.controller;

import com.epam.springboot.task1.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestOrderControllerTest {

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetOrder() throws URISyntaxException {
        URI uri = new URI("http://localhost:" + randomServerPort + "/rest/order/1");
        Order order = restTemplate.getForObject(uri, Order.class);
        assertEquals(1, order.getId());
    }

    @Test
    public void testDeleteOrder() throws URISyntaxException {
        URI uri = new URI("http://localhost:" + randomServerPort + "/rest/order/2");
        restTemplate.delete(uri);
    }

}
