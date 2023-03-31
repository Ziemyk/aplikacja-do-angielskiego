package com.pieczykolan.apliakcjadoangielskiego.repo;

import com.pieczykolan.apliakcjadoangielskiego.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("update User u set u.level = ?1 where u.nickName = ?2")
    int updateLevelByNickName(int level, String nickName);


    User getBynickName(String username);
    boolean existsBynickName(String username);


    User findAllById(int id);

}
