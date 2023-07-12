package com.pieczykolan.apliakcjadoangielskiego.Services;

import com.pieczykolan.apliakcjadoangielskiego.Entity.TeacherGroupEntity;
import com.pieczykolan.apliakcjadoangielskiego.repo.TeacherGroupRepo;
import com.pieczykolan.apliakcjadoangielskiego.repo.TeacherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepo teacherRepo;
    private final TeacherGroupRepo teacherGroupRepo;
//    public List<TeacherGroupEntity> getAllGroupNameByTeacherId(int teacherId) {
//        return (List<TeacherGroupEntity>) teacherGroupRepo.getAllGroupNameByTeacherId(teacherId);
//    }

}
