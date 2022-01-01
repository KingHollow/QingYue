package com.javaweb.qingyue.task;

import java.sql.Date;
import java.sql.Timestamp;

public class timetask {
    public static void main(String[] args) {
        Timestamp timestamp = new Timestamp(10000000);
        System.out.println(timestamp.toString().substring(0, 19));
    }
}
