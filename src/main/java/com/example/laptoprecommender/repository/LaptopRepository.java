package com.example.laptoprecommender.repository;

import com.example.laptoprecommender.model.Laptop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopRepository extends MongoRepository<Laptop, String> {

    Laptop findByLaptopId(int laptopId);
}
