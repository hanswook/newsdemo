package com.hans.newslook.test.download;

import java.io.Serializable;

/**
 * @author Hans
 * @create 2019/1/11
 * @Describe
 */
public class FileBean implements Serializable {

    private long id;
    private String url;
    private long length;
    private long loadedLength;
    private String fileName;

    public FileBean() {
    }

    public FileBean(long id, String url, long length, long loadedLength, String fileName) {
        this.id = id;
        this.url = url;
        this.length = length;
        this.loadedLength = loadedLength;
        this.fileName = fileName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getLoadedLength() {
        return loadedLength;
    }

    public void setLoadedLength(long loadedLength) {
        this.loadedLength = loadedLength;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public String toString() {
        return "FileBean{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", length=" + length +
                ", loadedLength=" + loadedLength +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
