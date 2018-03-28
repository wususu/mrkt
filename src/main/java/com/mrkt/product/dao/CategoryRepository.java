package com.mrkt.product.dao;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrkt.product.model.Category;

/**
 * @ClassName	CategoryRepository
 * @Description 商品分类的持久层
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/03/28 16:13:52
 */
@Repository
@Table(name="mrkt_pro_cat")
@Qualifier("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
