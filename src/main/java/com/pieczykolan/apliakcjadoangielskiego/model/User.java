package com.pieczykolan.apliakcjadoangielskiego.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;

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
    private int level;
    public User() {
        super();
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

    public User(String nickName, String password, byte [] imageBytes, String gender, int level) {

        this.nickName = nickName;
        this.password = password;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);
        this.imageBytes = imageBytes;
        this.gender = Gender.valueOf(gender);
        this.level = level;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean checkPassword(String password) {
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }
}
