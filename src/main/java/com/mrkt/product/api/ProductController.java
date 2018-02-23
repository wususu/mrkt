package com.mrkt.product.api;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.mrkt.authorization.annotation.Authorization;
import com.mrkt.product.core.IProductService;
import com.mrkt.product.model.Image;
import com.mrkt.product.model.Product;
import com.mrkt.usr.ThisUser;
import com.mrkt.usr.model.UserBase;

/**
 * @ClassName	ProductCotroller	
 * @Description
 * @author		hdonghong
 * @version 	v1.0 
 * @since		2018/02/19 13:16:21
 */
@RestController
public class ProductController {

	@Autowired
	private IProductService productService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 查询商品信息
	 */
	@Authorization
	@RequestMapping(value="/products/{id}", method=RequestMethod.GET)
	public String getProduct(@PathVariable("id") Long id) {
		Product entity = productService.findOne(id);
		logger.info("thisUser: " + ThisUser.get());
		return JSON.toJSONString(entity);
	}
	
	/**
	 * 分页展示商品，搜索商品
	 * @param currPage
	 * @param type 商品类型
	 * @param orderWay tmCreated(按最新排序) or views(按浏览量排序)
	 * @param keywords 搜索的关键词
	 * @return
	 */
	@RequestMapping(value="/products/all", method=RequestMethod.GET)
	public String getProducts(
			@RequestParam("curr_page") Integer currPage, 
			@RequestParam("type") String type, 
			@RequestParam("order_way") String orderWay, 
			@RequestParam("keywords") String keywords) {
		Page<Product> page = productService.findPage(currPage, type, orderWay, keywords);
		return JSON.toJSONString(page);
	}
	
	/**
	 * 上架新商品
	 * @param name
	 * @param desc
	 * @param price
	 * @param images
	 * @param ptype
	 * @param traWay
	 * @param count
	 * @param uid
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/products", method=RequestMethod.POST)
	public String addProduct(HttpServletRequest request,
			@RequestParam("name") String name,
			@RequestParam("desc") String desc,
			@RequestParam("price") Double price,
			@RequestParam("images") MultipartFile[] images,
			@RequestParam("ptype") String ptype,
			@RequestParam("tra_way") String traWay,
			@RequestParam("count") Integer count
			) {
		Product entity = new Product();
		entity.setName(name);
		entity.setDesc(desc);
		entity.setPrice(price);
		entity.setPtype(ptype);
		entity.setTraWay(traWay);
		entity.setCount(count);
		entity.setMrktUser(ThisUser.get());
		// 处理图片
		String rootpath = request.getServletContext().getRealPath("/");// 根路径
		Set<Image> imageSet = new HashSet<>();
		for (MultipartFile image : images) {
			// 获取文件后缀名
			String fileName = image.getOriginalFilename();
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			// 随机字符串加文件后缀名作为保存的文件名
			String randomName = UUID.randomUUID() + suffixName;
			String subpath = "upload/products/" + randomName;// 用于保存到数据库中的路径
			
			File filepath = new File(rootpath + subpath);
			if (!filepath.getParentFile().exists())
				filepath.getParentFile().mkdirs();
			try {
				image.transferTo(new File(rootpath + subpath));
				imageSet.add(new Image(subpath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		entity.setImages(imageSet);
		productService.saveOrUpdate(entity);
		return "success";
	}
	
	/**
	 * 修改商品信息
	 */
	@Authorization
	@RequestMapping(value="/products/{id}", method=RequestMethod.PUT)
	public String updateProduct(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam("name") String name,
			@RequestParam("desc") String desc,
			@RequestParam("price") Double price,
			@RequestParam("images") MultipartFile[] images,
			@RequestParam("ptype") String ptype,
			@RequestParam("tra_way") String traWay,
			@RequestParam("count") Integer count) {
		Product entity = new Product();
		entity.setId(id);
		entity.setName(name);
		entity.setDesc(desc);
		entity.setPrice(price);
		entity.setPtype(ptype);
		entity.setTraWay(traWay);
		entity.setCount(count);
		// 处理图片
		String rootpath = request.getServletContext().getRealPath("/");// 根路径
		Set<Image> imageSet = new HashSet<>();
		for (MultipartFile image : images) {
			// 获取文件后缀名
			String fileName = image.getOriginalFilename();
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			// 随机字符串加文件后缀名作为保存的文件名
			String randomName = UUID.randomUUID() + suffixName;
			String subpath = "upload/products/" + randomName;// 用于保存到数据库中的路径
			
			File filepath = new File(rootpath + subpath);
			if (!filepath.getParentFile().exists())
				filepath.getParentFile().mkdirs();
			try {
				image.transferTo(new File(rootpath + subpath));
				imageSet.add(new Image(subpath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		entity.setImages(imageSet);
		productService.saveOrUpdate(entity);
		return "success";
	}
	
	/**
	 * 下架商品
	 */
	@Authorization
	@RequestMapping(value="/products/{id}", method=RequestMethod.DELETE)
	public String cancelProduct(@PathVariable("id") Long id) {
		productService.cancel(id);
		return "success";
	}
	
	
	/**
	 * 点赞
	 */
	@Authorization
	@RequestMapping(value="/products/{id}/likes", method=RequestMethod.POST)
	public String addLikes(@PathVariable("id") Long id) {
		productService.addLikes(id);
		return "success";
	}
	
	/**
	 * 取消点赞
	 */
	@Authorization
	@RequestMapping(value="/products/{id}/likes", method=RequestMethod.DELETE)
	public String removeLikes(@PathVariable("id") Long id) {
		productService.removeLikes(id);
		return "success";
	}
	
	/**
	 * 收藏商品
	 */
	@RequestMapping(value="/products/{id}/collection", method=RequestMethod.POST)
	public String addCollections(@PathVariable("id") Long id) {
		return "success";
	}
	
}
