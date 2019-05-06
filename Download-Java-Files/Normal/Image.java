package com.example.usk.glotus_final.SuperviserApp.ReceptionFiles;

import java.io.Serializable;

public class Image implements Serializable {
    private String imageName;
    private String imagePath;

    public Image() {
    }

    public Image(String imageName, String imagePath) {
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
