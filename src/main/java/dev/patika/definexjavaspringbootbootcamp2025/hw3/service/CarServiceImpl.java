package dev.patika.definexjavaspringbootbootcamp2025.hw3.service;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.Car;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.exception.NoSuchCarException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService{

    private final List<Car> carList = new ArrayList<>();

    @Override
    public List<Car> list() {
        return new ArrayList<>(carList);
    }
    @Override
    public Car getById(UUID id) throws NoSuchCarException {
        return carList.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst()
                .orElseThrow(NoSuchCarException::new);
    }

    @Override
    public Car create(Car car) {
        Car newCar = Car.builder()
                .id(UUID.randomUUID())
                .year(car.getYear())
                .brand(car.getBrand())
                .licensePlate(car.getLicensePlate())
                .model(car.getModel())
                .dailyRate(car.getDailyRate())
                .available(car.getAvailable() != null ? car.getAvailable() : true)
                .build();

        carList.add(newCar);
        return newCar;
    }

    @Override
    public Car update(UUID id, Car updatedCar) throws NoSuchCarException {

        Car existingCar = getById(id);

        existingCar.setModel(updatedCar.getModel());
        existingCar.setBrand(updatedCar.getBrand());
        existingCar.setYear(updatedCar.getYear());
        existingCar.setLicensePlate(updatedCar.getLicensePlate());
        existingCar.setDailyRate(updatedCar.getDailyRate());
        existingCar.setAvailable(updatedCar.getAvailable());

        return existingCar;
    }
}
