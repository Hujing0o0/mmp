package com.uyunxun.mmp.service.impl;

import com.uyunxun.mmp.dao.UserDao;
import com.uyunxun.mmp.domain.po.User;
import com.uyunxun.mmp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by hu on 2017/4/21.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = Optional.of(userDao.findByUserAccount(username));
        final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
        user.ifPresent(detailsChecker::check);
        return user.orElseThrow(() -> new UsernameNotFoundException("user not found."));
    }

    @Override
    public User findByUserAccount(String userAccount) {
        return userDao.findByUserAccount(userAccount);
    }
}
