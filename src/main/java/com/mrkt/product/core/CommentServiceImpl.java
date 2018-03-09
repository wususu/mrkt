package com.mrkt.product.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrkt.product.dao.CommentRepository;
import com.mrkt.product.model.Comment;

/**
 * @ClassName	CommentServiceImpl
 * @Description 商品留言的实现
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/02/23 16:29:54
 */
@Service(value="commentService")
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public void removeComment(Long id) throws Exception {
		if (id == null) {
			throw new Exception("评论id为空，无法确定具体评论");
		}
		commentRepository.delete(id);
	}

	@Override
	public Comment getCommentById(Long id) throws Exception {
		if (id == null) {
			throw new Exception("评论id为空，无法确定具体评论");
		}
		return commentRepository.findOne(id);
	}

}
