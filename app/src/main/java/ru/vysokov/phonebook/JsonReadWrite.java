package ru.vysokov.phonebook;

import android.os.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonReadWrite {
    static File fileDirectory = new File(Environment.getExternalStorageDirectory() + "/saves.json");
    static String filePath;

    public static void writeJson(Object user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(fileDirectory, user);
        System.out.println(fileDirectory.getPath());
        System.out.println("JSON WRITTEN");
    }

    public static void readJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user1 = mapper.readValue(fileDirectory, User.class);
        System.out.println("JSON DATA READ");
        System.out.println(user1.toString());
    }
}
