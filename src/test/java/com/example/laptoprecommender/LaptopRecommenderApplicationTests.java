package com.example.laptoprecommender;

import com.example.laptoprecommender.repository.LaptopRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class LaptopRecommenderApplicationTests {

    @MockitoBean
    private LaptopRepository laptopRepository;

    @Test
    void contextLoads() {
    }
}
