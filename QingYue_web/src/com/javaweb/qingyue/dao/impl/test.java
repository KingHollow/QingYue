package com.javaweb.qingyue.dao.impl;

public class test {
    public static void main(String[] args) {
        String test = "\\usr\\source\\headshot\\null.jpg";
        System.out.println(test);
        System.out.println(test.replaceAll("\\\\", "/"));
    }


}
