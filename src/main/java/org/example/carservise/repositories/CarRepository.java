package org.example.carservise.repositories;

import org.example.carservise.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByEnginePowerBetween(Integer minEnginePower, Integer maxEnginePower);

    List<Car> findByEnginePowerGreaterThanEqual(Integer minEnginePower);

    List<Car> findByEnginePowerLessThanEqual(Integer maxEnginePower);
}
