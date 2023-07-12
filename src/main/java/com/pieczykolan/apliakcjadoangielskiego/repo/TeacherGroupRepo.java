package com.pieczykolan.apliakcjadoangielskiego.repo;

import com.pieczykolan.apliakcjadoangielskiego.Entity.TeacherGroupEntity;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherGroupRepo extends CrudRepository<TeacherGroupEntity, Integer> {
    @Query("select distinct t.groupName from TeacherGroupEntity t where t.teacher.id = ?1")
    List<String> getAllGroupNameByTeacherId(int id);

}
