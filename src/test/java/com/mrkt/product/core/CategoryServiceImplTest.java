package com.mrkt.product.core;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mrkt.product.model.Category;

/**
 * @ClassName	CategoryServiceImplTest
 * @Description
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/03/28 19:54:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

	@Autowired
	private ICategoryService categoryService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindAll() {
		List<Category> catList = categoryService.findAll();
		System.out.println(catList);
		/*
		 * [Category [id=1, name=书籍, createTime=null, updateTime=null], 
		 * Category [id=2, name=数码, createTime=null, updateTime=null], 
		 * Category [id=3, name=化妆饰品, createTime=null, updateTime=null], 
		 * Category [id=4, name=衣服鞋子, createTime=null, updateTime=null], 
		 * Category [id=5, name=日用品, createTime=null, updateTime=null], 
		 * Category [id=6, name=运动装备, createTime=null, updateTime=null], 
		 * Category [id=7, name=食品, createTime=null, updateTime=null], 
		 * Category [id=8, name=车辆, createTime=null, updateTime=null], 
		 * Category [id=9, name=其它, createTime=null, updateTime=null]]
		 */
	}

}
