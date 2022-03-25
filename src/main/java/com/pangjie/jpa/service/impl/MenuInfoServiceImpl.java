package com.pangjie.jpa.service.impl;

import com.pangjie.jpa.entity.MenuInfo;
import com.pangjie.jpa.repository.MenuInfoRepo;
import com.pangjie.jpa.service.MenuInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuInfoServiceImpl implements MenuInfoService {
    public MenuInfoRepo menuInfoRepo;

    @Override
    public MenuInfo findById(Integer valueOf) {
        return menuInfoRepo.findById(valueOf).get();
    }

}
