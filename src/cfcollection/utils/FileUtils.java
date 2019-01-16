package com.cfcollection.utils;

public class FileUtils {
    public static String sourceExtension(String language) {
        if (language.contains("GNU C++")) {
            return ".cpp";
        }
        if (language.contains("GNU C")) {
            return ".c";
        }
        if (language.contains("C#")) {
            return ".cs";
        }
        if (language.contains("Pascal") || language.contains("FPC")) {
            return ".pas";
        }
        if (language.contains("JavaScript")) {
            return ".js";
        }
        if (language.contains("Java")) {
            return ".java";
        }
        if (language.contains("Py")) {
            return ".py";
        }
        return ".txt";
    }
}
