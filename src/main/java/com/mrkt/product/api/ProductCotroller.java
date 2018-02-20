package com.mrkt.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mrkt.product.core.IProductService;
import com.mrkt.product.model.Product;

/**
 * @ClassName	ProductCotroller	
 * @Description
 * @author		hdonghong
 * @version 	v1.0 
 * @since		2018/02/19 13:16:21
 */
@RestController
public class ProductCotroller {

	@Autowired
	private IProductService productService;
	
	/**
	 * 查询商品信息
	 */
	@RequestMapping(value="/product/{id}", method=RequestMethod.GET)
	public String getProduct(@PathVariable("id") Long id) {
		Product entity = productService.findOne(id);
		return JSON.toJSONString(entity);
	}
	
	/**
	 * 分页展示商品，搜索商品
	 */
	@RequestMapping(value="/product/all", method=RequestMethod.POST)
	public String getProducts(
			@RequestParam("curr_page") Integer currPage, 
			@RequestParam("type") String type, 
			@RequestParam("order_way") String orderWay, 
			@RequestParam("keywords") String keywords) {
		Page<Product> page = productService.findPage(currPage, type, orderWay, keywords);
		return JSON.toJSONString(page);
	}
	
	/**
	 * 点赞(likes == 1)或者取消赞(likes == -1)
	 */
	@RequestMapping(value="/product/{id}/likes", method=RequestMethod.PUT)
	public String addLikes(
			@PathVariable("id") Long id,
			@RequestParam("likes") Integer likes) {
		if (likes == 1 || likes == -1) {
			Product entity = productService.findOne(id);
			int currLikes = entity.getLikes() + likes;// 当前点赞数
			entity.setLikes(currLikes < 0 ? 0 : currLikes);
			productService.saveOrUpdate(entity);
		} else {
			throw new RuntimeException("点赞功能异常");
		}
		
		return "ok";
	}
	
	/**
	 * 收藏商品
	 */
	@RequestMapping(value="/product/{id}/collection", method=RequestMethod.PUT)
	public String addCollections(
			@PathVariable("id") Long id,
			@RequestParam("collection") Integer collection) {
		if (collection == 1 || collection == -1) {
			Product entity = productService.findOne(id);
			int currColl = entity.getCollection() + collection;// 当前点赞数
			entity.setCollection(currColl < 0 ? 0 : currColl);
			productService.saveOrUpdate(entity);
		} else {
			throw new RuntimeException("收藏数功能异常");
		}
		return "ok";
	}
	
	/**
	 * 浏览量
	 */
	@RequestMapping(value="/product/{id}/views", method=RequestMethod.PUT)
	public String addViews(@PathVariable("id") Long id) {
		Product entity = productService.findOne(id);
		entity.setViews(entity.getViews());
		productService.saveOrUpdate(entity);
		return "ok";
	}
	
	/**
	 * 上架新商品
	 */
	@RequestMapping(value="/product", method=RequestMethod.POST)
	public String addProduct(
			@RequestParam("name") String name,
			@RequestParam("desc") String desc,
			@RequestParam("price") Double price,
			@RequestParam("image") String image,
			@RequestParam("ptype") String ptype,
			@RequestParam("traWay") String traWay,
			@RequestParam("count") String count,
			@RequestParam("uid") Long uid
			) {
		
		return "ok";
	}
	
	/**
	 * 修改商品信息
	 */
	@RequestMapping(value="/product/{id}", method=RequestMethod.PUT)
	public String updateProduct(
			@PathVariable("id") Long id) {
		return "ok";
	}
	
	/**
	 * 下架商品
	 */
	@RequestMapping(value="/product/{id}", method=RequestMethod.DELETE)
	public String cancelProduct(@PathVariable("id") Long id) {
		productService.cancel(id);
		return "ok";
	}
	
	
}
