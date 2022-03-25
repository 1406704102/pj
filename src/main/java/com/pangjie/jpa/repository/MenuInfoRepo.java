package com.pangjie.jpa.repository;

import com.pangjie.jpa.entity.MenuInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MenuInfoRepo extends JpaRepository<MenuInfo, Integer>, JpaSpecificationExecutor<Integer> {

}
