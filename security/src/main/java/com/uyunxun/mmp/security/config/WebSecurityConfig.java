package com.uyunxun.mmp.security.config;

import com.uyunxun.mmp.security.filter.CorsFilter;
import com.uyunxun.mmp.security.filter.StatelessAuthenticationFilter;
import com.uyunxun.mmp.security.filter.StatelessLoginFilter;
import com.uyunxun.mmp.security.service.TokenAuthenticationService;
import com.uyunxun.mmp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Created by hu on 2017/2/6.
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserService userService;
    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    public WebSecurityConfig() {
        super(true);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/","/resources/**", "/static/**", "/**/*.html"
                ,"/**/*.css","/**/*.js","/**/*.png","/**/*.jpg", "/**/*.gif", "/**/*.svg", "/**/*.ttf"
                ,"/**/*.woff");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/alipay/**").permitAll()
        .antMatchers("/wxpay/**").permitAll();

        http.exceptionHandling().and()
            .anonymous().and().csrf().disable()
            .addFilterBefore(new StatelessLoginFilter("/user/login", tokenAuthenticationService, userService, authenticationManager()),
            UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
                    UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
        super.configure(http);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userService;
    }

    protected WebSecurityConfig(boolean disableDefaults) {
        super(disableDefaults);
    }
}
