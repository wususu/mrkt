package com.mrkt.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mrkt.dto.ReturnModel;
import com.mrkt.product.core.ICategoryService;

/**
 * @ClassName	CategoryController
 * @Description 商品分类的控制器
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/03/28 14:57:13
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public ReturnModel findAll() {
		return ReturnModel.SUCCESS(categoryService.findAll());
	}
}
