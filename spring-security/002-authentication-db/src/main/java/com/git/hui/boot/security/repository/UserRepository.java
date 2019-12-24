package com.git.hui.boot.security.repository;

import com.git.hui.boot.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by @author yihui in 15:46 19/12/24.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity getFirstByUser(String user);

}
