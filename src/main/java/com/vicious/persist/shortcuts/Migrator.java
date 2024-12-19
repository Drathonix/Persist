package com.vicious.persist.shortcuts;

import java.io.File;

public class Migrator {
    public static boolean migrate(NotationFormat format, String fileName) {
        String filenameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        File directory = new File(fileName).getParentFile();
        File[] options = directory.listFiles((dir, name) -> name.startsWith(filenameWithoutExtension));
        for (File option : options) {

        }
    }
}
