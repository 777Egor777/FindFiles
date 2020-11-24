package ru.job4j;

public class Mask {
    private String mask;

    public Mask(String mask) {
        this.mask = mask.substring(1);
    }

    public boolean accept(String name) {
        return name.endsWith(mask);
    }
}
