package com.example.karanmotreja.project2;

public class MyTY
{
    String nameText;
    String giftText;
    String confirmationText;
    String keyText;

    public String getKeyText() {
        return keyText;
    }

    public void setKeyText(String keyText) {
        this.keyText = keyText;
    }



    public MyTY()
    {

    }

    public MyTY(String nameText, String giftText, String confirmationText, String keyText) {
        this.nameText = nameText;
        this.giftText = giftText;
        this.confirmationText = confirmationText;
        this.keyText = keyText;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getGiftText() {
        return giftText;
    }

    public void setGiftText(String giftText) {
        this.giftText = giftText;
    }

    public String getConfirmationText() {
        return confirmationText;
    }

    public void setConfirmationText(String confirmationText) {
        this.confirmationText = confirmationText;
    }
}
