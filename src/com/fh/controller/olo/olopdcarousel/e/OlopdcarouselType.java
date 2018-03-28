package com.fh.controller.olo.olopdcarousel.e;


public enum OlopdcarouselType {
    TUBPIAN("图片", 1), FENGGE("风格", 2),FENLEI("分类",3),FENLEILUNBO("分类轮播",4);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private OlopdcarouselType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (OlopdcarouselType c : OlopdcarouselType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // 普通方法
    public static int getValue(String name) {
        for (OlopdcarouselType c : OlopdcarouselType.values()) {
            if (c.getName() == name) {
                return c.index;
            }
        }
        return -1;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
