package com.mrlzy.shiro.dao.local.jpa;

import com.mrlzy.shiro.entity.Menu;
import com.mrlzy.shiro.entity.Role;
import com.mrlzy.shiro.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RoleJpa extends CrudRepository<Role,String> {


    public List<Role> findByWeightsLike(String roleid);
}
