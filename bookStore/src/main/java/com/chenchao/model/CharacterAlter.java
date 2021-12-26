package com.chenchao.model;

import java.util.Date;

public class CharacterAlter {
    private Integer characterId;
    private String characterName;
    private String characterCode;
    private String characterDescription;
    private Date characterCreated;
    private Date characterUpdated;

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setCharacterCode(String characterCode) {
        this.characterCode = characterCode;
    }

    public void setCharacterDescription(String characterDescription) {
        this.characterDescription = characterDescription;
    }

    public void setCharacterCreated(Date characterCreated) {
        this.characterCreated = characterCreated;
    }

    public void setCharacterUpdated(Date characterUpdated) {
        this.characterUpdated = characterUpdated;
    }



    public Integer getCharacterId() {
        return characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getCharacterCode() {
        return characterCode;
    }

    public String getCharacterDescription() {
        return characterDescription;
    }

    public Date getCharacterCreated() {
        return characterCreated;
    }

    public Date getCharacterUpdated() {
        return characterUpdated;
    }


}
