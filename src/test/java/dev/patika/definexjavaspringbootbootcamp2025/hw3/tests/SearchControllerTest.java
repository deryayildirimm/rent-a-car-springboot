package dev.patika.definexjavaspringbootbootcamp2025.hw3.tests;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.controller.SearchController;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.Car;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.exception.InvalidDateRangeException;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.exception.InvalidPriceRangeException;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.service.SearchService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SearchService searchService;

    @Test
    void searchAvailableCars_ShouldReturnOk() throws Exception{
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(5);

        List<Car> availableCars = List.of(
                Car.builder()
                        .id(UUID.randomUUID())
                        .brand("Toyota")
                        .model("Corolla")
                        .year(2022)
                        .licensePlate("34XYZ123")
                        .dailyRate(1000.0)
                        .available(true)
                        .build()
        );

        Mockito.when(searchService.findAvailableCars(startDate, endDate))
                .thenReturn(availableCars);

        mockMvc.perform(get("/api/search/available")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].brand").value("Toyota"))
                .andExpect(jsonPath("$[0].model").value("Corolla"));
    }

    @Test
    void searchAvailableCars_ShouldReturnBadRequest() throws Exception{
        LocalDateTime startDate = LocalDateTime.now().plusDays(5);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        Mockito.when(searchService.findAvailableCars(startDate, endDate))
                .thenThrow(new InvalidDateRangeException());

        mockMvc.perform(get("/api/search/available")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchByPriceRange_ShouldReturnOk() throws Exception{
        Double minPrice = 500.0;
        Double maxPrice = 1500.0;

        List<Car> cars = List.of(
                Car.builder()
                        .id(UUID.randomUUID())
                        .brand("Mercedes")
                        .model("C-Class")
                        .year(2021)
                        .licensePlate("34ABC456")
                        .dailyRate(1200.0)
                        .available(true)
                        .build()
        );

        Mockito.when(searchService.searchByPriceRange(minPrice, maxPrice))
                .thenReturn(cars);

        mockMvc.perform(get("/api/search/priceRange")
                        .param("minPrice", minPrice.toString())
                        .param("maxPrice", maxPrice.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].brand").value("Mercedes"))
                .andExpect(jsonPath("$[0].model").value("C-Class"));
    }

    @Test
    void searchByPriceRange_ShouldReturnBadRequest() throws Exception{
        Double minPrice = 2000.0;
        Double maxPrice = 500.0;

        Mockito.when(searchService.searchByPriceRange(minPrice, maxPrice))
                .thenThrow(new InvalidPriceRangeException());

        mockMvc.perform(get("/api/search/priceRange")
                        .param("minPrice", minPrice.toString())
                        .param("maxPrice", maxPrice.toString()))
                .andExpect(status().isBadRequest());
    }
}
