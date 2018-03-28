package com.mrkt.product.api;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrkt.authorization.annotation.Authorization;
import com.mrkt.config.StatusCodeConf;
import com.mrkt.dto.ReturnModel;
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
	public ReturnModel listComments(
			@RequestParam(value="productId",required=true) Long productId) throws Exception {
		Product Product = productService.findOne(productId);
		Set<Comment> comments = Product.getComments();
		
		// 判断操作用户是否是评论的所有者
		UserBase user = ThisUser.get();
		if (user != null)
			for (Comment comment : comments) {
				comment.setBelongCurrUser(
						user.getUid().equals(comment.getUser().getUid()));
			}
		
		return ReturnModel.SUCCESS(comments);
	}
	
	/**
	 * 发表评论
	 * @param blogId
	 * @param commentContent
	 * @return
	 */
	@Authorization
	@RequestMapping(method=RequestMethod.POST)
	public ReturnModel createComment(
			@RequestParam(value="productId") Long productId, 
			@RequestParam(value="commentContent") String commentContent) throws Exception {
		productService.addComment(productId, commentContent);
		return ReturnModel.SUCCESS();
	}
	
	/**
	 * 删除评论
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ReturnModel delete(
			@PathVariable("id") Long id, 
			@RequestParam(value="productId") Long productId) throws Exception {
		
		UserBase commentUser = commentService.getCommentById(id).getUser();
		// 判断操作用户是否是评论的所有者
		if (!commentUser.getUid().equals(ThisUser.get().getUid()))
			return new ReturnModel(StatusCodeConf.ForbiddenCode, "该评论不属于当前用户", null);
			
		productService.removeComment(productId, id);
		commentService.removeComment(id);
		
		return ReturnModel.SUCCESS();
	}
}
