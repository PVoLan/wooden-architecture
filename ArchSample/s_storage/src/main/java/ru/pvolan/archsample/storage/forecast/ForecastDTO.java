package ru.pvolan.archsample.storage.forecast;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ForecastDTO {

    public ForecastDTO(long cityForecastId, @NonNull String localDateTime, float temperatureCelsius) {
        this.cityForecastId = cityForecastId;
        this.localDateTime = localDateTime;
        this.temperatureCelsius = temperatureCelsius;
    }

    @PrimaryKey(autoGenerate = true)
    public long _id;

    public long cityForecastId;

    @NonNull
    public String localDateTime;

    public float temperatureCelsius;

}
