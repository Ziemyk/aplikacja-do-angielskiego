package com.pieczykolan.apliakcjadoangielskiego.Services;

import com.pieczykolan.apliakcjadoangielskiego.Entity.LevelsEntity;
import com.pieczykolan.apliakcjadoangielskiego.Entity.Teacher;
import com.pieczykolan.apliakcjadoangielskiego.Entity.TeacherGroupEntity;
import com.pieczykolan.apliakcjadoangielskiego.Entity.User;
import com.pieczykolan.apliakcjadoangielskiego.repo.LevelsRepo;
import com.pieczykolan.apliakcjadoangielskiego.repo.TeacherGroupRepo;
import com.pieczykolan.apliakcjadoangielskiego.repo.TeacherRepo;
import com.pieczykolan.apliakcjadoangielskiego.repo.UserRepo;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final LevelsRepo levelsRepo;
    private final TeacherRepo teacherRepo;
    private final TeacherGroupRepo teacherGroupRepo;
    
    @Autowired
    public AuthService(UserRepo userRepo, LevelsRepo levelsRepo, TeacherRepo teacherRepo, TeacherGroupRepo teacherGroupRepo) {
        this.userRepo = userRepo;
        this.levelsRepo = levelsRepo;
        this.teacherRepo = teacherRepo;
        this.teacherGroupRepo = teacherGroupRepo;

    }

    public List<String> getAllGroupNameByTeacherId(int teacherId) {
        return (List<String>) teacherGroupRepo.getAllGroupNameByTeacherId(teacherId);
    }

    public void addUser(String userName, String password, byte [] imageBytes, String gender) {
        User user = new User(userName, password, imageBytes, gender);
        userRepo.save(user);

    }

    public void authenticateAsStudent(String userName, String password) throws AuthException {
        User user =  userRepo.getBynickName(userName);
        if(user != null && user.checkPassword(password) ){
            VaadinSession.getCurrent().setAttribute(User.class,user);
        }else {
            throw new AuthException();
        }
    }
    public boolean checkUnique(String username){

        if(userRepo.existsBynickName(username)){
            return true;
        }
        return false;
    }

    public void updateLevelsEntity( int userId, LevelsEntity levels) {
        levelsRepo.save(levels);

    }

    public User updateData(String userName) {
        User user =  userRepo.getBynickName(userName);
        VaadinSession.getCurrent().setAttribute(User.class,user);
        return user;
    }

    public Image setImageFormDatabase(int id) {
        StreamResource streamResource = new StreamResource("user", () -> {
           User user = userRepo.findAllById(id);
           return new ByteArrayInputStream(user.getImageBytes());
        });
        streamResource.setContentType("image/png");
        Image image = new Image(streamResource, "Profile Image");
        return image;
    }

    public Optional<LevelsEntity> getLevelsEntity(int user_id) {
        return levelsRepo.findById(user_id);
    }
    public List<User> getAllUsers() {
        return (List<User>) userRepo.findAll();
    }
    public List<User> getAllUsersOfGroup(String name) {
        return (List<User>) userRepo.findAllOfGroup(name);
    }

    public List<LevelsEntity> getAllLevels() {
        return (List<LevelsEntity>) levelsRepo.findAll();
    }

    public void authenticateAsTeacher(String userName, String password) throws AuthException{
        Teacher teacher =  teacherRepo.getByNickName(userName);
        if(teacher != null && teacher.checkPassword(password) ){
            VaadinSession.getCurrent().setAttribute(Teacher.class,teacher);
        }else {
            throw new AuthException();
        }
    }

    public void addTeacher(String userName, String password, byte[] imageBytes, String gender) {
        Teacher teacher = new Teacher(userName, password, imageBytes, gender);
        teacherRepo.save(teacher);
    }

    public List<User> getAllUsersWithoutGroup() {
        return userRepo.getAllUsersWithoutGroup();
    }

    public List<LevelsEntity> getAllLevelsOfGroup(List<Integer> userWithGroupId) {
        return (List<LevelsEntity>) levelsRepo.findAllById(userWithGroupId);
    }

    public void addTeacherGroupIdToUser(String nickname,int id, String groupName) {

       // user.setTeacherGroup();
        Teacher teacher = teacherRepo.getByID(id);
        TeacherGroupEntity teacherGroupEntity = new TeacherGroupEntity();
        teacherGroupEntity.setTeacherId(teacher);
        teacherGroupEntity.setGroupName(groupName);
        teacherGroupRepo.save(teacherGroupEntity);
        User user = userRepo.getBynickName(nickname);
        user.setTeacherGroup(teacherGroupEntity);
        userRepo.save(user);


    }

    public void addGroupForTeacher(String value, Teacher teacher) {
        TeacherGroupEntity teacherGroup = new TeacherGroupEntity();
        teacherGroup.setGroupName(value);
        teacherGroup.setTeacher(teacher);
        teacherGroupRepo.save(teacherGroup);
    }

    public class AuthException  extends Exception{
    }
}
