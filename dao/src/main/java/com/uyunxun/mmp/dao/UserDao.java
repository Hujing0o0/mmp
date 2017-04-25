package com.uyunxun.mmp.dao;

import com.uyunxun.mmp.domain.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hu on 2017/4/21.
 */
@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    public User findByUserAccount(String userAccount);
}
