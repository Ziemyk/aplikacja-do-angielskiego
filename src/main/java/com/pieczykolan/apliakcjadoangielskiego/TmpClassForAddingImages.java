package com.pieczykolan.apliakcjadoangielskiego;

import com.pieczykolan.apliakcjadoangielskiego.View.Register;
import com.pieczykolan.apliakcjadoangielskiego.model.GameSetup;
import com.pieczykolan.apliakcjadoangielskiego.model.LevelOfWord;
import com.pieczykolan.apliakcjadoangielskiego.model.TypeOfWord;
import com.pieczykolan.apliakcjadoangielskiego.repo.GameSetupRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Route("tmp")
public class TmpClassForAddingImages extends VerticalLayout {
    private Upload upload;
    private final GameSetupRepo gameSetupRepo ;
    private ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
    private String imageName;
    private Path path = Paths.get("C:\\Users\\Przemko\\IdeaProjects\\apliakcja-do-angielskiego\\src\\main\\resources\\Images");
    private MultiFileMemoryBuffer memoryBuffer = new MultiFileMemoryBuffer();
    private String word;
    private ComboBox<TypeOfWord> comboBoxTypeOfWord;
    private ComboBox<LevelOfWord> comboBoxLevelOfWord;
    private TextField textField = new TextField("word");
    private TextField textFieldWithTranslate = new TextField("translate");
    private Button buttonConfirm = new Button("Confirm");
    @Autowired
    public TmpClassForAddingImages(GameSetupRepo gameSetupRepo) throws IOException{
        this.gameSetupRepo = gameSetupRepo;
        upload = new Upload(memoryBuffer);
        upload.setAcceptedFileTypes("image/jpeg","image/png","image/jpg");
        upload.addSucceededListener(e -> {
            imageName = e.getFileName();
            textField.setValue(imageName.replace(".jpg",""));
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
        comboBoxTypeOfWord = new ComboBox<>("Type", TypeOfWord.values());
        comboBoxLevelOfWord = new ComboBox<>("Level", LevelOfWord.values());
        buttonConfirm.addClickListener(e -> {
            GameSetup gameSetup = new GameSetup(textField.getValue(), pngContent.toByteArray(),
                    comboBoxTypeOfWord.getValue().toString(),textFieldWithTranslate.getValue(), comboBoxLevelOfWord.getValue());

            gameSetupRepo.save(gameSetup);
        });

        add(upload,textField,textFieldWithTranslate, comboBoxTypeOfWord,comboBoxLevelOfWord,buttonConfirm);

    }
    //TODO dodac zdjęcia , poprawić wygląd strony , poprawic wygląd wyboru levela i zrobić ta tablice wyników
}
