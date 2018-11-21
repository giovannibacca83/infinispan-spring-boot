package org.infinispan.springboot.repository;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.springboot.cache.RemoteCacheManagerFactory;
import org.infinispan.springboot.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ProductCacheRepository {

	private static final String CACHE_NAME = "product";

	private RemoteCache<Long, Product> remoteCache;

	@Autowired
	public ProductCacheRepository(RemoteCacheManagerFactory rcm) throws IOException {
	    List<Class> classes = new ArrayList<Class>();
        classes.add(Product.class);

        rcm.setProtobufConfig("product", classes);
		this.remoteCache = rcm.getCache(CACHE_NAME);
	}

	public Product find(Long itemId){
		return remoteCache.get(itemId);
	}

	public Product insert(Product product){
		return remoteCache.put(product.getItemId(), product);
	}

	public void delete(Long itemId){
		remoteCache.remove(itemId);
	}

    public Product insertWithTTL(Product product, Long ttl){
        return remoteCache.put(product.getItemId(), product, ttl, TimeUnit.SECONDS);
    }
}
