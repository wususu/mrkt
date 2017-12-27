package com.mrkt.wx.dao;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mrkt.wx.model.WxUser;

@Repository
@Table(name="wx_usr")
@Qualifier("wxUserRepository")
public interface WxUserRepository extends JpaRepository<WxUser, String>{

}
