package dev.patika.definexjavaspringbootbootcamp2025.hw3.service;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.Booking;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.Car;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.exception.InvalidDateRangeException;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.exception.InvalidPriceRangeException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService{
    private final CarService carService;
    private final BookingService bookingService;
    public SearchServiceImpl(CarService carService, BookingService bookingService) {
        this.carService = carService;
        this.bookingService = bookingService;
    }
    @Override
    public List<Car> findAvailableCars(LocalDateTime startTime, LocalDateTime endTime) throws InvalidDateRangeException {

        checkCondition(startTime.isAfter(endTime), InvalidDateRangeException::new);
        return carService.list().stream()
                .filter(Car::getAvailable)
                .filter(car -> isCarAvailableDuringPeriod(car, startTime, endTime))
                .collect(Collectors.toList());
    }

    private boolean isCarAvailableDuringPeriod(Car car, LocalDateTime startDate, LocalDateTime endDate) {
        List<Booking> bookings = bookingService.list();

        return bookings.stream()
                .filter(booking -> booking.getCarId().equals(car.getId()))
                .noneMatch(booking ->
                        (booking.getStartDate().isBefore(endDate) && booking.getEndDate().isAfter(startDate))
                );
    }
    @Override
    public List<Car> searchByPriceRange(double minPrice, double maxPrice) throws InvalidPriceRangeException {

        checkCondition(minPrice < 0 || maxPrice < 0 || minPrice > maxPrice, InvalidPriceRangeException::new);

        return carService.list().stream()
                .filter(car -> car.getDailyRate() >= minPrice && car.getDailyRate() <= maxPrice)
                .collect(Collectors.toList());
    }

    private <T extends RuntimeException> void checkCondition(boolean condition, Supplier<T> exceptionSupplier) {
        if (condition) {
            throw exceptionSupplier.get();
        }
    }

}

