package com.git.hui.boot.security.service;

import com.git.hui.boot.security.entity.UserEntity;
import com.git.hui.boot.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by @author yihui in 15:46 19/12/24.
 */
@Component
public class H2UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        UserEntity guest = new UserEntity();
        guest.setUser("hui1");
        guest.setPassword(passwordEncoder.encode("123456"));
        guest.setRole("guest");
        userRepository.save(guest);

        UserEntity admin = new UserEntity();
        admin.setUser("hui2");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setRole("admin");
        userRepository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity po = userRepository.getFirstByUser(username);
        if (po == null) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return User.builder().username(po.getUser()).password(po.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_" + po.getRole())).build();
    }
}
