package ru.job4j;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Validation {
    private final String[] args;
    private boolean correct = true;
    private String errorMsg;
    private String dir;
    private String outputFileName;
    private String type;
    private String name;
    private String mask;
    private final List<Function<String, Boolean>> dispatch = new LinkedList<>();
    private final int numberOfArgs = 7;

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
        if (args.length < numberOfArgs) {
            errorMsg = "Must be " + numberOfArgs + " args";
            correct = false;
            return;
        }
        initDispatch();
        for (int i = 0; i < numberOfArgs; ++i) {
            if (!dispatch.get(i).apply(args[i])) {
                correct = false;
                break;
            }
        }
    }

    private void initDispatch() {
        dispatch.add(valid0Arg());
        dispatch.add(validDir());
        dispatch.add(valid2Arg());
        dispatch.add(validNameOrMaskArg());
        dispatch.add(validTypeArg());
        dispatch.add(valid5Arg());
        dispatch.add(validOutputFileName());
    }

    public Function<String, Boolean> valid0Arg() {
        return arg -> {
            boolean result = true;
            if (!arg.equals("-d")) {
                errorMsg = "First arg must be \"-d\"";
                result = false;
            }
            return result;
        };
    }

    public Function<String, Boolean> validDir() {
        return arg -> {
            boolean result = true;
            dir = arg;
            if (!(new File(arg)).isDirectory()) {
                errorMsg = "Incorrect dir path";
                result = false;
            }
            return result;
        };
    }

    public Function<String, Boolean> valid2Arg() {
        return arg -> {
            boolean result = true;
            if (!arg.equals("-n")) {
                errorMsg = "Third arg must be \"-n\"";
                result = false;
            }
            return result;
        };
    }

    public Function<String, Boolean> validNameOrMaskArg() {
        return arg -> {
            name = arg;
            mask = arg;
            return true;
        };
    }

    public Function<String, Boolean> validTypeArg() {
        return arg -> {
            boolean result = true;
            if (!arg.equals("-f") && !arg.equals("-m")) {
                errorMsg = "5th arg must be \"-f\" or \"-m\"";
                result = false;
            } else if (arg.equals("-f")) {
                type = "name";
            } else {
                type = "mask";
            }
            return result;
        };
    }

    public Function<String, Boolean> valid5Arg() {
        return arg -> {
            boolean result = true;
            if (!arg.equals("-o")) {
                errorMsg = "6th arg must be \"-o\"";
                result = false;
            }
            return result;
        };
    }

    public Function<String, Boolean> validOutputFileName() {
        return arg -> {
            outputFileName = arg;
            return true;
        };
    }
}
