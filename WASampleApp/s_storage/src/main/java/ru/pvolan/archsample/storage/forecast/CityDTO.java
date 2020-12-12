package ru.pvolan.archsample.storage.forecast;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"cityName"}, unique = true)})
public class CityDTO {

    public CityDTO(long id, @NonNull String cityName, @NonNull String lastUpdated, float lat, float lng) {
        this.id = id;
        this.cityName = cityName;
        this.lastUpdated = lastUpdated;
        this.lat = lat;
        this.lng = lng;
    }

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    public String cityName;

    @NonNull
    public String lastUpdated;

    public float lat;

    public float lng;
}
