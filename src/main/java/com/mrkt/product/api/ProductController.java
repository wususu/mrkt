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

import com.mrkt.authorization.annotation.Authorization;
import com.mrkt.dto.ReturnModel;
import com.mrkt.product.core.IProductService;
import com.mrkt.product.model.Image;
import com.mrkt.product.model.Product;
import com.mrkt.usr.ThisUser;

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
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/products/{id}", method=RequestMethod.GET)
	public ReturnModel getProduct(@PathVariable("id") Long id) throws Exception {
		Product entity = productService.findOne(id);
		logger.info("thisUser: " + ThisUser.get());
		return ReturnModel.SUCCESS(entity);
	}
	
	/**
	 * 分页展示商品，搜索商品
	 * @param currPage 当前页码
	 * @param cat_id 商品类型编号
	 * @param orderWay tmCreated(按最新排序) or views(按浏览量排序)
	 * @param keywords 搜索的关键词
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/products/all", method=RequestMethod.GET)
	public ReturnModel getProducts(
			@RequestParam("curr_page") Integer currPage, 
			@RequestParam(value="type", required=false) Long catId, 
			@RequestParam(value="order_way", required=false) String orderWay, 
			@RequestParam(value="keywords", required=false) String keywords) throws Exception {
		Page<Product> page = productService.findPage(currPage, catId, orderWay, keywords);
		return ReturnModel.SUCCESS(page);
	}
	
	/**
	 * 上架新商品
	 * @param name
	 * @param desc
	 * @param price
	 * @param images
	 * @param catId
	 * @param traWay
	 * @param count
	 * @param uid
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/products", method=RequestMethod.POST)
	public ReturnModel addProduct(HttpServletRequest request,
			@RequestParam(value="name") String name,
			@RequestParam(value="desc", required=false, defaultValue="") String desc,
			@RequestParam(value="price") Double price,
			@RequestParam(value="images", required=false, defaultValue="") MultipartFile[] images,
			@RequestParam(value="catId", required=false, defaultValue="9") Long catId,
			@RequestParam(value="tra_way", required=false, defaultValue="当面交易") String traWay,
			@RequestParam(value="count") Integer count
			) throws Exception {
		Product entity = new Product();
		entity.setName(name);
		entity.setDesc(desc);
		entity.setPrice(price);
		entity.setCatId(catId);
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
		return ReturnModel.SUCCESS();
	}
	
	/**
	 * 修改商品信息
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/products/{id}", method=RequestMethod.PUT)
	public ReturnModel updateProduct(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam(value="name") String name,
			@RequestParam(value="desc") String desc,
			@RequestParam(value="price") Double price,
			@RequestParam(value="images", required=false) MultipartFile[] images,
			@RequestParam(value="catId") Long catId,
			@RequestParam(value="tra_way", required=false, defaultValue="当面交易") String traWay,
			@RequestParam(value="count") Integer count) throws Exception {
		Product entity = new Product();
		entity.setId(id);
		entity.setName(name);
		entity.setDesc(desc);
		entity.setPrice(price);
		entity.setCatId(catId);
		entity.setTraWay(traWay);
		entity.setCount(count);
		// 处理图片
		String rootpath = request.getServletContext().getRealPath("/");// 根路径
		Set<Image> imageSet = new HashSet<>();
		if (images != null)
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
		return ReturnModel.SUCCESS();
	}
	
	/**
	 * 下架商品
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/products/{id}", method=RequestMethod.DELETE)
	public ReturnModel cancelProduct(@PathVariable("id") Long id) throws Exception {
		productService.cancel(id);
		return ReturnModel.SUCCESS();
	}
	
	
	/**
	 * 点赞
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/products/{id}/likes", method=RequestMethod.POST)
	public ReturnModel addLikes(@PathVariable("id") Long id) throws Exception {
		productService.addLikes(id);
		return ReturnModel.SUCCESS();
	}
	
	/**
	 * 取消点赞
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/products/{id}/likes", method=RequestMethod.DELETE)
	public ReturnModel removeLikes(@PathVariable("id") Long id) throws Exception {
		productService.removeLikes(id);
		return ReturnModel.SUCCESS();
	}
	
	/**
	 * 收藏商品
	 * @throws Exception 
	 */
	@RequestMapping(value="/products/{id}/collection", method=RequestMethod.POST)
	public ReturnModel addCollections(@PathVariable("id") Long id) throws Exception {
		productService.addCollection(id);
		return ReturnModel.SUCCESS();
	}
	
	/**
	 * 取消收藏
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/products/{id}/collection", method=RequestMethod.DELETE)
	public ReturnModel removeCollection(@PathVariable("id") Long id) throws Exception {
		productService.removeCollection(id);
		return ReturnModel.SUCCESS();
	}
	
	/**
	 * 获取我发布的商品
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/products/mine", method=RequestMethod.GET)
	public ReturnModel getMine() throws Exception {
		return ReturnModel.SUCCESS(productService.getMine());
	}
	
	/**
	 * 获取我收藏的商品
	 * @return
	 * @throws Exception 
	 */
	@Authorization
	@RequestMapping(value="/products/collection", method=RequestMethod.GET)
	public ReturnModel getCollection() throws Exception {
		return ReturnModel.SUCCESS(productService.getCollection());
	}
}
