package com.example.usk.glotus_final.SuperviserApp.ReceptionFiles;

import java.io.File;
import java.io.Serializable;

public class PdfInfo implements Serializable {
    private File file;
    private String path;
    private String fileName;

    public PdfInfo() {
    }

    public PdfInfo(File file, String path, String fileName) {
        this.file = file;
        this.path = path;
        this.fileName=fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
