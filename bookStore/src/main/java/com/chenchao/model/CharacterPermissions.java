package com.chenchao.model;

import java.util.Date;

public class CharacterPermissions {
    private Integer characterId;
    private Integer permissionsId;
    private Date characterPermissionsCreated;
    private Date characterPermissionsUpdated;

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public void setPermissionsId(Integer permissionsId) {
        this.permissionsId = permissionsId;
    }

    public void setCharacterPermissionsCreated(Date characterPermissionsCreated) {
        this.characterPermissionsCreated = characterPermissionsCreated;
    }

    public void setCharacterPermissionsUpdated(Date characterPermissionsUpdated) {
        this.characterPermissionsUpdated = characterPermissionsUpdated;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public Integer getPermissionsId() {
        return permissionsId;
    }

    public Date getCharacterPermissionsCreated() {
        return characterPermissionsCreated;
    }

    public Date getCharacterPermissionsUpdated() {
        return characterPermissionsUpdated;
    }

}
