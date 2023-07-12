package com.pieczykolan.apliakcjadoangielskiego.repo;

import com.pieczykolan.apliakcjadoangielskiego.Entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

    User getBynickName(String username);
    boolean existsBynickName(String username);

    User findAllById(int id);
    @Query("select u from User u where u.teacherGroup is null")
    List<User> getAllUsersWithoutGroup();
    @Query("select u from User u where u.teacherGroup.groupName = ?1")
    List<User> findAllOfGroup(String name);
    @Modifying
    @Query("update User u set u.teacherGroup.teacher.id = ?2 where u.nickName = ?1")
    void updateTeacherGroupIdByNickName(String nickname, int id);
}
