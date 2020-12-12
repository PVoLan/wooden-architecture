package ru.pvolan.archsample.network.forecast;

import ru.pvolan.archsample.entities.CityForecast;
import ru.pvolan.archsample.network.exception.NetworkException;

public interface IForecastAPI {
    CityForecast getForecast(String cityName) throws NetworkException;
}
