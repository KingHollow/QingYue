package com.javaweb.qingyue.task;

import com.javaweb.qingyue.util.DBconn;

import java.util.Random;

public class sqlTask {
    public static void main(String[] args) {
        DBconn.init();
        Random random = new Random();
        for (int i = 1; i < 111; i++) {
            for (int j = 1; j < 21; j++){
                String sql = "insert into r_song_label(song_id, label_id, value) values(" + i + ", " + j + ", " + random.nextFloat() + ")";
                DBconn.addUpdDel(sql);
                System.out.println("song_id: "+ i + "; label_id: " + j);
            }

        }

    }
}
