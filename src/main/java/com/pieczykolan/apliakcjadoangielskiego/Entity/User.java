package com.pieczykolan.apliakcjadoangielskiego.Entity;

import com.pieczykolan.apliakcjadoangielskiego.model.Gender;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String nickName;
    private String password;
    private String passwordSalt;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte [] imageBytes;
    private Gender gender;
    private String passwordHash;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private LevelsEntity levelsEntity;

    @OneToOne
    private TeacherGroupEntity teacherGroup;
    public User() {
        super();
    }

    public TeacherGroupEntity getTeacherGroup() {
        return teacherGroup;
    }

    public void setTeacherGroup(TeacherGroupEntity teacherGroup) {
        this.teacherGroup = teacherGroup;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public User(String nickName, String password, byte [] imageBytes, String gender) {

        this.nickName = nickName;
        this.password = password;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);
        this.imageBytes = imageBytes;
        this.gender = Gender.valueOf(gender);

    }
    @PostPersist
    public void createLevelsEntity() {
        this.levelsEntity = LevelsEntity.builder()
                .levelOfVerb(1)
                .levelOfNoun(1)
                .levelOfAdjective(1)
                .levelOfAdverbial(1)
                .userId(this.id)
                .build();
    }
    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public byte [] getImageBytes() {
        return imageBytes;
    }

    public Gender getGender() {
        return gender;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImageBytes(byte [] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public void setGender(String gender) {
        this.gender = Gender.valueOf(gender);
    }

    public boolean checkPassword(String password) {
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }
}
