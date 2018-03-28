package com.mrkt.product.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrkt.product.dao.CategoryRepository;
import com.mrkt.product.model.Category;

/**
 * @ClassName	CategoryServiceImpl
 * @Description
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/03/28 16:17:56
 */
@Service(value="categoryService")
public class CategoryServiceImpl implements ICategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

}
