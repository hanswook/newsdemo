package com.hans.newslook.test.download.db;

/**
 * @author Hans
 * @create 2019/1/11
 * @Describe
 */
public class ThreadBean {
    private int id;
    private String url;
    private long start;
    private long end;
    private long loadedLen;

    public ThreadBean() {
    }

    public ThreadBean(int id, String url, long start, long end, long loadedLen) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.loadedLen = loadedLen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getLoadedLen() {
        return loadedLen;
    }

    public void setLoadedLen(long loadedLen) {
        this.loadedLen = loadedLen;
    }

    @Override
    public String toString() {
        return "ThreadBean{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", loadedLen=" + loadedLen +
                '}';
    }
}
