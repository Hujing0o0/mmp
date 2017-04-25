package com.uyunxun.mmp.service;

import com.uyunxun.mmp.domain.po.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by hu on 2017/4/21.
 */
public interface UserService extends UserDetailsService {
    User findByUserAccount(String userAccount);
}
