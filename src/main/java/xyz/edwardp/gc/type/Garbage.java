package xyz.edwardp.gc.type;


import xyz.edwardp.gc.execption.UnkownGarbageCategoryException;

import java.io.Serializable;

public class Garbage implements Serializable {
    private int categoryid;
    private String category;
    private String name;

    public Garbage() {
        categoryid = 0;
        category = "";
        name = "";
    }

    public Garbage(String name, int category) throws UnkownGarbageCategoryException {
        this.name = name;
        this.categoryid = category;
        switch (category) {
            case 1:
                this.category = "可回收垃圾";
                break;
            case 2:
                this.category = "有害垃圾";
                break;
            case 4:
                this.category = "湿垃圾";
                break;
            case 8:
                this.category = "干垃圾";
                break;
            case 16:
                this.category = "大件垃圾";
                break;
            default:
                throw new UnkownGarbageCategoryException();
        }
    }

    public int getCategoryid() {
        return categoryid;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void setCategory() throws UnkownGarbageCategoryException {
        switch (categoryid) {
            case 0:
                this.category = "";
                break;
            case 1:
                this.category = "可回收垃圾";
                break;
            case 2:
                this.category = "有害垃圾";
                break;
            case 4:
                this.category = "湿垃圾";
                break;
            case 8:
                this.category = "干垃圾";
                break;
            case 16:
                this.category = "大件垃圾";
                break;
            default:
                throw new UnkownGarbageCategoryException();
        }
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
        try {
            setCategory();
        } catch (UnkownGarbageCategoryException e) {
            e.printStackTrace();
        }
    }
}
