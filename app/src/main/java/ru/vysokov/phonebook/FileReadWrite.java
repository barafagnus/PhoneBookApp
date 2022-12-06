package ru.vysokov.phonebook;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class FileReadWrite {
    static File fileDirectory = new File(Environment.getExternalStorageDirectory() + "/saves.txt");
    static String filePath;

    public static void writeFile(String userData) throws Exception {
        System.out.println(fileDirectory.getPath());
        filePath = fileDirectory.getPath();
        FileWriter fw = new FileWriter(fileDirectory, false);
        fw.write(userData);
        fw.flush();
        fw.close();
        System.out.println("FILE WRITTEN");
    }

    public static void readFile() throws Exception {
        FileReader fr = new FileReader(filePath);
        Scanner scan = new Scanner(fr);
        int i = 1;
        while (scan.hasNextLine()) {
            System.out.println(i + " : " + scan.nextLine());
            i++;
        }
        fr.close();
    }
}
