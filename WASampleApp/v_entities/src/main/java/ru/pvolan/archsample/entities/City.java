package ru.pvolan.archsample.entities;

public class City {
    private final String name;
    private final float lat;
    private final float lng;

    public City(String name, float lat, float lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }
}
