package org.example.carservise.repositories;

import org.example.carservise.entities.Maintenance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends MongoRepository<Maintenance, Long> {
}
