package com.mrlzy.shiro.dao.local.jpa;

import com.mrlzy.shiro.entity.Permission;
import com.mrlzy.shiro.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface PermJpa extends CrudRepository<Permission,String> {
}
