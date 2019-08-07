package com.pos.mahmoud.pos.models;

public class TransactionItem{
    private int name;
    private int color;
    private int type;
    private String path;
    private int printName;
    public TransactionItem(int name, int color,int type,String path) {
        this.name = name;
        this.color = color;
        this.type=type;
        this.path=path;
    }
    public TransactionItem(int name, int color,int type,String path,int printName) {
        this.name = name;
        this.color = color;
        this.type=type;
        this.path=path;
        this.printName=printName;
    }
    public int getPrintName() {
        return printName;
    }

    public void setPrintName(int printName) {
        this.printName = printName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
