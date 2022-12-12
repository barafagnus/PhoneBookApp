package ru.vysokov.phonebook;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class FileReadWrite {
    static File fileDirectory = new File(Environment.getExternalStorageDirectory() + "/saves.txt");
    static String filePath;
    static LinkedList data = new LinkedList<>();

    public static void writeFile(String userData) throws Exception {
        filePath = fileDirectory.getPath();
        FileWriter fw = new FileWriter(fileDirectory, false);
        fw.write(userData);
        fw.flush();
        fw.close();
    }

    public static void readFile() throws Exception {
        FileReader fr = new FileReader(filePath);
        Scanner scan = new Scanner(fr);
        int i = 1;
        while (scan.hasNextLine()) {
            i++;
        }
        fr.close();
    }
}
