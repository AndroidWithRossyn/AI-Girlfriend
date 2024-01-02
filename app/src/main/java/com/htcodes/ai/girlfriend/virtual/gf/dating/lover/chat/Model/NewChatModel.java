package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model;


public class NewChatModel {
    String ansText;
    int id;
    String isFromHistory = "";
    int questionImage;
    String questionText;
    String questionType;
    String senderType;

    public NewChatModel() {
    }

    public NewChatModel(String str, int i, String str2, String str3, String str4) {
        this.questionText = str;
        this.questionImage = i;
        this.questionType = str2;
        this.senderType = str3;
        this.ansText = str4;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public void setQuestionText(String str) {
        this.questionText = str;
    }

    public int getQuestionImage() {
        return this.questionImage;
    }

    public void setQuestionImage(int i) {
        this.questionImage = i;
    }

    public String getQuestionType() {
        return this.questionType;
    }

    public void setQuestionType(String str) {
        this.questionType = str;
    }

    public String getSenderType() {
        return this.senderType;
    }

    public void setSenderType(String str) {
        this.senderType = str;
    }

    public String getAnsText() {
        return this.ansText;
    }

    public void setAnsText(String str) {
        this.ansText = str;
    }

    public String isFromHistory() {
        return this.isFromHistory;
    }

    public void setFromHistory(String str) {
        this.isFromHistory = str;
    }
}
