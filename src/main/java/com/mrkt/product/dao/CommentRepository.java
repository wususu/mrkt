package com.mrkt.product.dao;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrkt.product.model.Comment;

/**
 * @ClassName	CommentRepository
 * @Description
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/02/23 16:24:42
 */
@Repository
@Table(name="mrkt_pro_comment")
@Qualifier("commentRepository")
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
