package com.fh.controller.olo.olopdorder.e;

public enum OloOrderState {
   DSP("待审批", 1), YXD("已下单", 2), YFH("已发货", 3);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private OloOrderState(String name, int index) {
        this.name = name;
        this.index = index;
        
    }

    // 普通方法
    public static String getName(int index) {
        for (OloOrderState c : OloOrderState.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // 普通方法
    public static int getValue(String name) {
        for (OloOrderState c : OloOrderState.values()) {
            if (c.getName().equals(name)) {
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
