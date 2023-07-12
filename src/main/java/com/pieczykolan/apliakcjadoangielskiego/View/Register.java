package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.Layout.MainLayout;
import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.model.Gender;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;


@Route(value = "Register",layout = MainLayout.class)
@CssImport("themes/my-theme/register.css")
public class Register extends VerticalLayout {

    private final AuthService authService ;

    private Button buttonLogin;
    private TextField textFieldNick ;
    private PasswordField fieldPassword;
    private PasswordField confirmPasswordField;
    private ComboBox<Gender> comboBoxGender;

    private String imageName;

    private Upload upload;

    private Path path = Paths.get("C:\\Users\\Przemko\\IdeaProjects\\apliakcja-do-angielskiego\\src\\main\\resources\\Images");
    private MultiFileMemoryBuffer memoryBuffer = new MultiFileMemoryBuffer();

    private Checkbox checkboxTeacher = new Checkbox("Zarejestruj się jako nauczyciel");
    private ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
    private HorizontalLayout horizontalLayoutRegisterAndLogin = new HorizontalLayout();
    @Autowired
    public Register(AuthService authService) throws IOException {
        VerticalLayout verticalLayoutRegister = new VerticalLayout();
        verticalLayoutRegister.addClassName("verticalLayoutRegister");
        horizontalLayoutRegisterAndLogin.addClassName("horizontalRegisterAndLogin");
        this.authService = authService;
        Label labelNameRegister = new Label("Rejestracja");
        labelNameRegister.addClassName("labelName");
        setHorizontalComponentAlignment(Alignment.CENTER, labelNameRegister);
        checkboxTeacher.addClassName("checkboxTeacher");
        textFieldNick = new TextField("Nazwa użytkownika");
        textFieldNick.addClassName("textFieldNick");
        fieldPassword = new PasswordField("Hasło");
        fieldPassword.addClassName("textFieldPassword");
        confirmPasswordField = new PasswordField("Potwierdz hasło");
        confirmPasswordField.addClassName("textFieldConfirmPassword");
        comboBoxGender = new ComboBox<>("Płeć",Gender.values());
        comboBoxGender.setPlaceholder("Wybierz płeć");
        upload = new Upload(memoryBuffer);
        upload.addClassName("upload");
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
            try {
                BufferedImage inputImage = ImageIO.read(memoryBuffer.getInputStream(imageName));

                ImageIO.write(inputImage, "png", pngContent);

            } catch (IOException ex) {
                try {
                    throw new IOException("Nie udało sie",ex);
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });

        Button buttonConfirmRegister = new Button("Zarejestruj się");
        buttonConfirmRegister.addClassName("buttonRegister");
        //buttonConfirmRegister.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
        buttonLogin = new Button("Zaloguj się");
        buttonLogin.addClickListener( e ->  UI.getCurrent().getPage().setLocation("/"));
        buttonLogin.addClassName("buttonLogin");
        buttonConfirmRegister.addClickListener(buttonClickEvent -> register(textFieldNick.getValue(),fieldPassword.getValue(),confirmPasswordField.getValue(),
                pngContent.toByteArray() ,comboBoxGender.getValue().toString()));
        setHorizontalComponentAlignment(Alignment.CENTER,checkboxTeacher,labelNameRegister,textFieldNick,fieldPassword,confirmPasswordField,comboBoxGender,upload,buttonConfirmRegister,buttonLogin);
        horizontalLayoutRegisterAndLogin.add(buttonConfirmRegister,buttonLogin);
        verticalLayoutRegister.add(labelNameRegister,checkboxTeacher,textFieldNick, fieldPassword,confirmPasswordField,comboBoxGender,upload,horizontalLayoutRegisterAndLogin);
        add(verticalLayoutRegister);
    }
    private void register(String userName, String password, String confirmPassword, byte [] imageBytes, String gender) {
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
            if(!checkboxTeacher.getValue()) {
                authService.addUser(userName, password, imageBytes, gender);
            }else{
                authService.addTeacher(userName, password, imageBytes, gender);
            }
            Notification.show("Registration successed");

        }
    }

}
