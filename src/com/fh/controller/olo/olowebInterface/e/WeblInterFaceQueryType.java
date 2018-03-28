package com.fh.controller.olo.olowebInterface.e;

public enum WeblInterFaceQueryType {
        TM("台面", 2), MB("门板", 0), SP("商品", 1);
        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private WeblInterFaceQueryType(String name, int index) {
            this.name = name;
            this.index = index;
        }

        // 普通方法
        public static String getName(int index) {
            for (WeblInterFaceQueryType c : WeblInterFaceQueryType.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }
        // 普通方法
        public static int getValue(String name) {
            for (WeblInterFaceQueryType c : WeblInterFaceQueryType.values()) {
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