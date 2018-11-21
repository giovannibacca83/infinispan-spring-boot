package org.infinispan.springboot.repository;

import org.infinispan.springboot.model.Product;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, Long> {

}