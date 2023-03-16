package com.pieczykolan.apliakcjadoangielskiego.repo;

import com.pieczykolan.apliakcjadoangielskiego.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

    User getBynickName(String username);
    boolean existsBynickName(String username);



}
