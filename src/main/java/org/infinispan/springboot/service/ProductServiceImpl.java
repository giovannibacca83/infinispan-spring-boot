package org.infinispan.springboot.service;

import org.infinispan.springboot.model.Product;
import org.infinispan.springboot.repository.ProductCacheRepository;
import org.infinispan.springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductCacheRepository productCacheRepository;

    @Autowired
    private ProductRepository productRepository;

    public Product find(Long itemId, boolean cacheOnly) {
        Product product = productCacheRepository.find(itemId);
        if(!cacheOnly) {
            if (null == product) {
                product = productRepository.findOne(itemId);
                productCacheRepository.insert(product);
            }
        }
        return product;
    }

    public Product insert(Product product) {
        Product savedProduct = productRepository.save(product);
        return productCacheRepository.insert(savedProduct);
    }

    public void delete(Long itemId, boolean cacheOnly) {
        if(!cacheOnly){
            productRepository.delete(itemId);
        }
        productCacheRepository.delete(itemId);
    }

    public Product insertWithTTL(Product product, Long ttl) {
        return productCacheRepository.insertWithTTL(product, ttl);
    }
}
