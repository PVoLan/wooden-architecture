package ru.pvolan.archsample.network.tools;

public class PParam {

    private String name;
    private String value;


    public PParam(String name, Integer value) {
        this.name = name;
        this.value = Integer.toString(value);
    }

    public PParam(String name, Long value) {
        this.name = name;
        this.value = Long.toString(value);
    }

    public PParam(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }


    public String getValue() {
        return value;
    }
}
