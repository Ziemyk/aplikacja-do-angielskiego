package com.pieczykolan.apliakcjadoangielskiego.Services;

import com.pieczykolan.apliakcjadoangielskiego.model.User;
import com.pieczykolan.apliakcjadoangielskiego.repo.UserRepo;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class AuthService {

    private final UserRepo userRepo;
    
    @Autowired
    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /*public void authenticate(String username, String password) throws AuthException{
        userRepo.
    }*/
    public void addUser(String userName, String password, byte [] imageBytes, String gender) {
        User user = new User(userName, password, imageBytes, gender,1);
        userRepo.save(user);

    }

    public void authenticate(String userName, String password) throws AuthException {
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

    public void updateDatabase(int i, String nickName) {
        userRepo.updateLevelByNickName(i, nickName);
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

    public class AuthException  extends Exception{
    }
}
