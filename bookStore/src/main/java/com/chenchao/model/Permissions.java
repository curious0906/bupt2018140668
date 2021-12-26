package com.chenchao.model;

import java.util.Date;

public class Permissions {
    private Integer permId;
    private String permName;
    private String permCode;
    private String permUrl;
    private Integer permParentId;
    private Date permCreated;
    private Date permUpdated;
    private Integer permIsParent;

    public void setPermId(Integer permId) {
        this.permId = permId;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }

    public void setPermUrl(String permUrl) {
        this.permUrl = permUrl;
    }

    public void setPermParentId(Integer permParentId) {
        this.permParentId = permParentId;
    }

    public void setPermCreated(Date permCreated) {
        this.permCreated = permCreated;
    }

    public void setPermUpdated(Date permUpdated) {
        this.permUpdated = permUpdated;
    }

    public void setPermIsParent(Integer permIsParent) {
        this.permIsParent = permIsParent;
    }
    public Integer getPermId() {
        return permId;
    }

    public String getPermName() {
        return permName;
    }

    public String getPermCode() {
        return permCode;
    }

    public String getPermUrl() {
        return permUrl;
    }

    public Integer getPermParentId() {
        return permParentId;
    }

    public Date getPermCreated() {
        return permCreated;
    }

    public Date getPermUpdated() {
        return permUpdated;
    }

    public Integer getPermIsParent() {
        return permIsParent;
    }


}
