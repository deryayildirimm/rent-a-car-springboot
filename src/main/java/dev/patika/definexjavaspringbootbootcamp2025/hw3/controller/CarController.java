package dev.patika.definexjavaspringbootbootcamp2025.hw3.controller;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.Car;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.exception.NoSuchCarException;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/car")
public class CarController {
    private final CarService carService;
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable UUID id) {
        return handleExceptions(() -> ResponseEntity.ok(carService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.create(car));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable UUID id, @RequestBody Car car) {
        return handleExceptions(() -> ResponseEntity.status(HttpStatus.ACCEPTED).body(carService.update(id, car)));
    }

    private ResponseEntity<Car> handleExceptions(Supplier<ResponseEntity<Car>> supplier) {
        try {
            return supplier.get();
        } catch (NoSuchCarException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
