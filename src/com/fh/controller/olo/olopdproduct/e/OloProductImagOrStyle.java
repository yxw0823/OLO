package com.fh.controller.olo.olopdproduct.e;
public enum OloProductImagOrStyle {
        TUBPIAN("图片", 1), FENGGE("风格", 5), SKUDTSX("SKU动态属性", 6), TJDPSP("推荐搭配的商品", 7);
        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private OloProductImagOrStyle(String name, int index) {
            this.name = name;
            this.index = index;
        }

        // 普通方法
        public static String getName(int index) {
            for (OloProductImagOrStyle c : OloProductImagOrStyle.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }
        // 普通方法
        public static int getValue(String name) {
            for (OloProductImagOrStyle c : OloProductImagOrStyle.values()) {
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