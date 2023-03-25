package com.pieczykolan.apliakcjadoangielskiego.Services;

import com.pieczykolan.apliakcjadoangielskiego.model.User;
import com.pieczykolan.apliakcjadoangielskiego.repo.UserRepo;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addUser(String userName, String password, String imageUrl, String gender) {
        User user = new User(userName, password, imageUrl, gender);
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

    public class AuthException  extends Exception{
    }
}
