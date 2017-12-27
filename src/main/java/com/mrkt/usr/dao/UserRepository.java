package com.mrkt.usr.dao;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrkt.usr.model.UserBase;

@Repository
@Table(name="wx_usr")
@Qualifier("wxUserRepository")
public interface UserRepository extends JpaRepository<UserBase, Long>{

}
