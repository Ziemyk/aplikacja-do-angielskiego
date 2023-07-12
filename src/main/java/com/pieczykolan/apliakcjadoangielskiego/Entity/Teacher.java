package com.pieczykolan.apliakcjadoangielskiego.Entity;

import com.pieczykolan.apliakcjadoangielskiego.model.Gender;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher {

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

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeacherGroupEntity> teacherGroupEntity = new ArrayList<>();

    public Teacher() {
        super();
    }

    public Teacher(String nickName, String password, byte[] imageBytes, String gender) {
        this.nickName = nickName;
        this.password = password;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);
        this.imageBytes = imageBytes;
        this.gender = Gender.valueOf(gender);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public Gender getGender() {
        return gender;
    }
    public boolean checkPassword(String password) {
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }

}