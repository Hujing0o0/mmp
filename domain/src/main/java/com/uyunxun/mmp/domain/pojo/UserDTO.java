package com.uyunxun.mmp.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uyunxun.mmp.domain.po.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by hu on 2017/2/6.
 */
public class UserDTO {
    private static final String ROLE_USER = "ROLE_USER";

    private final String useraccount;
    @Size(min = 8, max = 100)
    private final String password;
    private final String name;

    public UserDTO(@JsonProperty("useraccount") String useraccount,
                   @JsonProperty("password") String password,
                   @JsonProperty("name") String name) {
        this.useraccount = useraccount;
        this.password = password;
        this.name = name;
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(useraccount);
    }

    public Optional<String> getEncodedPassword() {
        return Optional.ofNullable(password).map(p -> new BCryptPasswordEncoder().encode(p));
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public User toUser() {
        User user = new User();
        user.setUserAccount(useraccount);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setNickName(name);
        return user;
    }

    public UsernamePasswordAuthenticationToken toAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(useraccount, password, getAuthorities());
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> ROLE_USER);
    }

}
