package dev.patika.definexjavaspringbootbootcamp2025.hw3.controller;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.Car;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.service.SearchService;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @GetMapping("/available")
    public ResponseEntity<List<Car>> searchAvailableCars(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {

        return handleExceptions(() -> ResponseEntity.ok(searchService.findAvailableCars(startDate, endDate)));
    }

    @GetMapping("/priceRange")
    public ResponseEntity<List<Car>> searchByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {

        return handleExceptions(() -> ResponseEntity.ok(searchService.searchByPriceRange(minPrice, maxPrice)));
    }

    private ResponseEntity<List<Car>> handleExceptions(Supplier<ResponseEntity<List<Car>>> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
