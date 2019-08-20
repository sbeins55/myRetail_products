package com.myretail.products.repository;

import com.myretail.products.valueobjects.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {
    Optional<Product> findByRedskyId(Long redskyId);
}
