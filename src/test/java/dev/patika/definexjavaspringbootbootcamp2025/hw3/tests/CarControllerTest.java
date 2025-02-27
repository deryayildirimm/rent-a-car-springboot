package dev.patika.definexjavaspringbootbootcamp2025.hw3.tests;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.controller.CarController;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.Car;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.service.CarService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CarService carService;

    @Test
    void getAllCars_ShouldReturnOk() throws Exception {
        mockMvc.perform(
                get("/api/car/list"))
                .andExpect(status().isOk());
    }

    @Test
    void createCar_ShouldReturnCreated() throws Exception {
        UUID carId = UUID.randomUUID();
        Car newCar = Car.builder()
                .id(carId)
                .model("Audi A6")
                .brand("Audi")
                .year(2023)
                .licensePlate("06ABC123")
                .dailyRate(1800.0)
                .available(true)
                .build();

        Mockito.when(carService.create(Mockito.any(Car.class) )).thenReturn(newCar);

        String carJson = """
        {
            "id": "%s",
            "model": "Audi A6",
            "brand": "Audi",
            "year": 2023,
            "licensePlate": "06ABC123",
            "dailyRate": 1800.0,
            "available": true
        }
        """.formatted(carId);

        mockMvc.perform(post("/api/car")
                        .contentType("application/json")
                        .content(carJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.model").value("Audi A6"))
                .andExpect(jsonPath("$.brand").value("Audi"))
                .andExpect(jsonPath("$.dailyRate").value(1800.0));

    }

    @Test
    void updateCar_ShouldReturnAccepted() throws Exception {

        // Bu test, geçerli bir araba ID ile PUT /api/car/{id}
        // isteği attığımızda 202 Accepted döndüğünü kontrol eder.

        UUID carId = UUID.randomUUID();
        Car updatedCar = Car.builder()
                .id(carId)
                .model("Updated Model")
                .brand("Updated Brand")
                .year(2025)
                .licensePlate("34ZZZ123")
                .dailyRate(2500.0)
                .available(false)
                .build();

        Mockito.when(carService.update(Mockito.any(UUID.class), Mockito.any(Car.class)))
                .thenReturn(updatedCar);

        String updatedCarJson = """
        {
            "id": "%s",
            "model": "Updated Model",
            "brand": "Updated Brand",
            "year": 2025,
            "licensePlate": "34ZZZ123",
            "dailyRate": 2500.0,
            "available": false
        }
        """.formatted(carId);

        mockMvc.perform(put("/api/car/" + carId)
                        .contentType("application/json")
                        .content(updatedCarJson))
                .andExpect(status().isAccepted()) // 202 Accepted
                .andExpect(jsonPath("$.model").value("Updated Model"))
                .andExpect(jsonPath("$.brand").value("Updated Brand"));

    }

    @Test
    void updateCar_ShouldReturnBadRequest() throws Exception {

        String invalidUUID = "invalid-id";

        mockMvc.perform(put("/api/car/" + invalidUUID))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCar_ShouldReturnOk() throws Exception {

        UUID carId = UUID.randomUUID();
        Car mockCar = Car.builder()
                .id(carId)
                .model("Toyota Corolla")
                .brand("Toyota")
                .year(2022)
                .licensePlate("16ABC123")
                .dailyRate(900.0)
                .available(true)
                .build();


            Mockito.when(carService.getById(carId)).thenReturn(mockCar);

        mockMvc.perform(get("/api/car/" + carId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Toyota Corolla"))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.dailyRate").value(900.0));

    }

    @Test
    void getCar_ShouldReturnBadRequest() throws Exception {

        //  Geçersiz bir UUID formatı
        String invalidCarId = "invalid-uuid";

        mockMvc.perform(get("/api/car/" + invalidCarId))
                .andExpect(status().isBadRequest()); //  400 BAD REQUEST bekliyoruz
    }
}
