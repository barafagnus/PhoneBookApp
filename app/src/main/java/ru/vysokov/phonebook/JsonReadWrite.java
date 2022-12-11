package ru.vysokov.phonebook;

import android.os.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonReadWrite {

    public static void writeJson(Object user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("saves.json"), user);
    }

    public static void readJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user1 = mapper.readValue("saves.json", User.class);
    }
}
