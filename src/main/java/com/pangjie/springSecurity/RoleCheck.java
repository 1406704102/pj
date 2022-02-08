package com.pangjie.springSecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @Author pangjie
 * @Description //TODO 权限验证 @PreAuthorize("@roleCheck.check('1:系统设置')")
 * @Date 下午2:26 8/2/2022
 * @Param 
 * @return 
 */
@Service
public class RoleCheck {
    public boolean check(String role) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> collect = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return collect.stream().anyMatch(role::contains);
    }
}
