package com.pieczykolan.apliakcjadoangielskiego.repo;

import com.pieczykolan.apliakcjadoangielskiego.Entity.Teacher;
import com.pieczykolan.apliakcjadoangielskiego.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepo extends CrudRepository<Teacher, Integer> {

    @Query("select t from Teacher t where t.nickName = ?1")
    Teacher getByNickName(String userName);
    @Query("select t from Teacher t where t.id =?1")
    Teacher getByID(int id);
}
