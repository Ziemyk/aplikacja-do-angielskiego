package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.model.Gender;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;


@Route("Register")
public class Register extends VerticalLayout {

    private final AuthService authService ;

    private RouterLink routerLinkLogin;
    private TextField textFieldNick ;
    private PasswordField fieldPassword;
    private PasswordField confirmPasswordField;
    private ComboBox<Gender> comboBoxGender;

    private String imageName;

    private Upload upload;

    private Path path = Paths.get("C:\\Users\\Przemko\\IdeaProjects\\apliakcja-do-angielskiego\\src\\main\\resources\\Images");
    private MultiFileMemoryBuffer memoryBuffer = new MultiFileMemoryBuffer();

    private MultipartFile multipartFile;
    @Autowired
    public Register(AuthService authService) throws IOException {
        this.authService = authService;
        Label labelNameRegister = new Label("Register");
        setHorizontalComponentAlignment(Alignment.CENTER, labelNameRegister);
        textFieldNick = new TextField("Nick");
        fieldPassword = new PasswordField("Password");
        confirmPasswordField = new PasswordField("Confirm Password");
        comboBoxGender = new ComboBox<>("Gender",Gender.values());
        comboBoxGender.setPlaceholder("Choose Gender");
        upload = new Upload(memoryBuffer);
        upload.setAcceptedFileTypes("image/jpeg","image/png","image/jpg");
        upload.addSucceededListener(e -> {
            imageName = e.getFileName();

            if(!Files.exists(path)){
                try {
                    Files.createDirectories(path);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try (InputStream inputStream = memoryBuffer.getInputStream(imageName)){
                Path filePath = path.resolve(imageName);
                Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException ex) {
                try {
                    throw new IOException("Nie udało sie",ex);
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });

        Button buttonConfirmRegister = new Button("Register");
        routerLinkLogin = new RouterLink("Login", Login.class);
        routerLinkLogin.setVisible(false);
        buttonConfirmRegister.addClickListener(buttonClickEvent -> register(textFieldNick.getValue(),fieldPassword.getValue(),confirmPasswordField.getValue(),
               imageName ,comboBoxGender.getValue().toString()));
        add(labelNameRegister,textFieldNick, fieldPassword,confirmPasswordField,comboBoxGender,upload, buttonConfirmRegister,routerLinkLogin);
    }

    //TODO zdjęcia avatara przenieść do bazy danych bo w pliku nie oddaje

    private void register(String userName, String password, String confirmPassword, String imageUrl, String gender) {
        if(userName.trim().isEmpty()) {
            Notification.show("Enter Username");
        } else if(authService.checkUnique(userName)){
            Notification.show("This name is already taken");
        }else if(password.isEmpty()){
            Notification.show("Enter Password");
        }else if(!confirmPassword.equals(password)){
            Notification.show("Password don't match");
        }else if(gender.isEmpty()){
            Notification.show("Choose Gender");
        } else {
            authService.addUser(userName,password, imageUrl, gender);
            Notification.show("Registration successed");
            routerLinkLogin.setVisible(true);
        }
    }

}
