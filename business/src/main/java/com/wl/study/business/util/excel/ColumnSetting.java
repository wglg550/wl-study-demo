package com.wl.study.business.util.excel;

public class ColumnSetting implements Comparable<ColumnSetting> {

    private String header;
    private String getMethodName;
    private int width;
    private int index;

    public ColumnSetting(String header, String getMethodName, int width, int index) {
        this.header = header;
        this.getMethodName = getMethodName;
        this.width = width;
        this.index = index;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getGetMethodName() {
        return getMethodName;
    }

    public void setGetMethodName(String getMethodName) {
        this.getMethodName = getMethodName;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    @Override
    public int compareTo(ColumnSetting columnSetting) {
        if (index < columnSetting.getIndex()) {
            return -1;
        }
        if (index > columnSetting.getIndex()) {
            return 1;
        }
        return 0;
    }
}
