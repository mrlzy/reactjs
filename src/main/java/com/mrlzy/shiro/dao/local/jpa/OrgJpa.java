package com.mrlzy.shiro.dao.local.jpa;

import com.mrlzy.shiro.entity.Menu;
import com.mrlzy.shiro.entity.Org;
import org.springframework.data.repository.CrudRepository;


public interface OrgJpa extends CrudRepository<Org,String> {
}
