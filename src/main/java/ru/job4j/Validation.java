package ru.job4j;

import java.io.File;

public class Validation {
    private final String[] args;
    private boolean correct = false;
    private String errorMsg;
    private String dir;
    private String outputFileName;
    private String type;
    private String name;
    private String mask;

    public boolean isCorrect() {
        return correct;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getDir() {
        return dir;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getMask() {
        return mask;
    }
    public Validation(String[] args) {
        this.args = args;
        if (!args[0].equals("-d")) {
            errorMsg = "First arg must be \"-d\"";
            return;
        }
        dir = args[1];
        if (!(new File(dir)).isDirectory()) {
            errorMsg = "Incorrect dir path";
            return;
        }
        if (!args[2].equals("-n")) {
            errorMsg = "Third arg must be \"-n\"";
            return;
        }
        if (!args[4].equals("-f") && !args[4].equals("-m")) {
            errorMsg = "5th arg must be \"-f\" or \"-m\"";
            return;
        }
        if (args[4].equals("-f")) {
            type = "name";
            name = args[3];
        } else {
            type = "mask";
            mask = args[3];
        }
        if (!args[5].equals("-o")) {
            errorMsg = "6th arg must be \"-o\"";
            return;
        }
        outputFileName = args[6];
        correct = true;
    }
}
