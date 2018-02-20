package com.mrkt.product.dao;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrkt.product.model.Product;

@Repository
@Table(name="mrkt_product")
@Qualifier("productRepository")
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	Product findByIdAndState(Long id, Integer state);
	
	Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}
