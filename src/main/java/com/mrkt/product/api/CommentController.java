package com.mrkt.product.api;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mrkt.authorization.annotation.Authorization;
import com.mrkt.product.core.ICommentService;
import com.mrkt.product.core.IProductService;
import com.mrkt.product.model.Comment;
import com.mrkt.product.model.Product;
import com.mrkt.usr.ThisUser;
import com.mrkt.usr.model.UserBase;

/**
 * 商品留言 控制器.
 * 
 */
@RestController
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private ICommentService commentService;
	
	/**
	 * 获取评论列表
	 * @param blogId
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String listComments(
			@RequestParam(value="productId",required=true) Long productId) {
		Product Product = productService.findOne(productId);
		Set<Comment> comments = Product.getComments();
		
		// 判断操作用户是否是评论的所有者
		UserBase user = ThisUser.get();
		if (user != null)
			for (Comment comment : comments) {
				comment.setBelongCurrUser(
						user.getUid().equals(comment.getUser().getUid()));
			}
		
		return JSON.toJSONString(comments);
	}
	
	/**
	 * 发表评论
	 * @param blogId
	 * @param commentContent
	 * @return
	 */
	@Authorization
	@RequestMapping(method=RequestMethod.POST)
	public String createComment(
			@RequestParam(value="productId") Long productId, 
			@RequestParam(value="commentContent") String commentContent) {
		try {
			productService.addComment(productId, commentContent);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		return "success";
	}
	
	/**
	 * 删除评论
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String delete(
			@PathVariable("id") Long id, 
			@RequestParam(value="productId") Long productId) {
		
		UserBase commentUser = commentService.getCommentById(id).getUser();
		try {
			// 判断操作用户是否是评论的所有者
			if (!commentUser.getUid().equals(ThisUser.get().getUid()))
				return "error";
				
			productService.removeComment(productId, id);
			commentService.removeComment(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}
}
