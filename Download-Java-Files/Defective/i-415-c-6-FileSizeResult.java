package com.novoda.downloadmanager;

import android.support.annotation.NonNull;

public class FileSizeResult {

    private final FileSize fileSize;
    private final String failureMessage;

    public static FileSizeResult success(FileSize fileSize) {
        return new FileSizeResult(fileSize, null);
    }

    public static FileSizeResult failure(String failureMessage) {
        return new FileSizeResult(null, failureMessage);
    }

    private FileSizeResult(FileSize fileSize, String failureMessage) {
        this.fileSize = fileSize;
        this.failureMessage = failureMessage;
    }

    public FileSize fileSize() {
        if (fileSize == null) {
            throw new IllegalArgumentException("You must check if it's a success or failure before querying");
        }
        return fileSize;
    }

    public String failureMessage() {
        if (failureMessage == null) {
            throw new IllegalArgumentException("You must check if it's a success or failure before querying");
        }
        return failureMessage;
    }

    public boolean isSuccess() {
        return fileSize != null;
    }

    public boolean isFailure() {
        return failureMessage != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileSizeResult that = (FileSizeResult) o;

        if (fileSize != null ? !fileSize.equals(that.fileSize) : that.fileSize != null) {
            return false;
        }
        return failureMessage != null ? failureMessage.equals(that.failureMessage) : that.failureMessage == null;
    }

    @Override
    public int hashCode() {
        int result = fileSize != null ? fileSize.hashCode() : 0;
        result = 31 * result + (failureMessage != null ? failureMessage.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "FileSizeResult{"
                + "fileSize=" + fileSize
                + ", failureMessage='" + failureMessage + '\''
                + '}';
    }
}
