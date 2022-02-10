package com.pangjie.jpa.service.impl;

import com.pangjie.jpa.service.MenuInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuInfoServiceImpl implements MenuInfoService {
    public PasswordEncoder passwordEncoder;
    public UserDetailsService userDetailsService;
}
