package com.mrlzy.shiro.dao.local.jpa;

import com.mrlzy.shiro.entity.Menu;
import com.mrlzy.shiro.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserJpa extends CrudRepository<User,String> {


    public  User findByAccount(String account);

    @Query("select o from User o where  o.account=?1 and o.user_type=?2 ")
    public  User findByAccountAndType(String account,int type);


}
