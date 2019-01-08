package com.ritian.common.util;

/**
 * 常量
 * @author ritian.Zhang
 * @date 2019/01/07
 **/
public class Constant {
    public static final String SUPER_ADMIN = "admin";

    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
