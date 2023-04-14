package com.pangjie.springSecurity;

import com.pangjie.jpa.entity.MenuInfo;
import com.pangjie.jpa.entity.RoleInfo;
import com.pangjie.util.IpUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * @Author pangjie
 * @Description //TODO 权限验证 @PreAuthorize("@roleCheck.check('1:系统设置')")
 * @Date 下午3:16 8/2/2022
 * @Param 
 * @return 
 */
@Service
public class RoleCheck {
    public boolean check(String role) {
         final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        Set<RoleInfo> roleInfos = userDetails.getUserInfo().getRoleInfos();
        List<String> collect = new ArrayList<>();
        for (RoleInfo roleInfo : roleInfos) {
            for (MenuInfo menu : roleInfo.getMenus()) {
                collect.add(menu.getId() + ":" + menu.getTitle());
            }
        }
//        List<String> collect = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return collect.stream().anyMatch(role::contains);
    }

    public boolean checkIp() {
        HttpServletRequest request =
                ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String ipAddr = IpUtil.getIpAddr(request);
        System.out.println(ipAddr);
        return true;
    }
}
