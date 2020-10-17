package ru.pvolan.archsample.storage.forecast;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public abstract class ForecastDao {

    @Insert
    public abstract long insertCityForecast(CityForecastDTO forecastDTO);

    @Insert
    public abstract void insertForecasts(List<ForecastDTO> forecastDTOs);


    @Query("DELETE from CityForecastDTO where id = :id")
    public abstract void deleteCityForecast(long id);

    @Query("DELETE from ForecastDTO where cityForecastId = :cityForecastId")
    public abstract void deleteForecasts(long cityForecastId);

    @Query("DELETE from ForecastDTO")
    public abstract void deleteAll();


    @Query("SELECT * from CityForecastDTO where cityName = :cityName")
    public abstract @Nullable CityForecastDTO findForecast(String cityName);

    @Query("SELECT * from ForecastDTO where cityForecastId = :cityForecastId")
    public abstract List<ForecastDTO> getForecasts(long cityForecastId);

}
