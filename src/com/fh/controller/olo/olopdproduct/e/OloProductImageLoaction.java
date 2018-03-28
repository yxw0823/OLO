package com.fh.controller.olo.olopdproduct.e;
public enum OloProductImageLoaction {
        A("A", 1), B("B", 2), C("C", 3),FG("风格",5);
        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private OloProductImageLoaction(String name, int index) {
            this.name = name;
            this.index = index;
            
        }

        // 普通方法
        public static String getName(int index) {
            for (OloProductImageLoaction c : OloProductImageLoaction.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }
        // 普通方法
        public static int getValue(String name) {
            for (OloProductImageLoaction c : OloProductImageLoaction.values()) {
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