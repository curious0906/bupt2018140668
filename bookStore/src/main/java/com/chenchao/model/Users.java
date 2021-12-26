package com.chenchao.model;

import java.io.Serializable;
import java.util.Date;
public class Users implements Serializable {
    private int userId; //id
    private String userUsername;    //姓名
    private String userNickname;
    private String userPassword;   //用户密码
    private String userGender; //id
    private String userEmail;
    private String userPhone;
    private String userPostalCode;
    private String userAddress;
    private String userDetailAddress;
    private String userIdentity;
    private String whetherOrNoActive;
    private String userCode;
    private Date userUpdated;
    private Date userCreated;
    
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserPostalCode(String userPostalCode) {
        this.userPostalCode = userPostalCode;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setUserDetailAddress(String userDetailAddress) {
        this.userDetailAddress = userDetailAddress;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public void setWhetherOrNoActive(String whetherOrNoActive) {
        this.whetherOrNoActive = whetherOrNoActive;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setUserUpdated(Date userUpdated) {
        this.userUpdated = userUpdated;
    }

    public void setUserCreated(Date userCreated) {
        this.userCreated = userCreated;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserPostalCode() {
        return userPostalCode;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserDetailAddress() {
        return userDetailAddress;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public String getWhetherOrNoActive() {
        return whetherOrNoActive;
    }

    public String getUserCode() {
        return userCode;
    }

    public Date getUserUpdated() {
        return userUpdated;
    }

    public Date getUserCreated() {
        return userCreated;
    }

}

