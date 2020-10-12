package ru.pvolan.archsample.logic.forecast;

import java.util.Calendar;

import ru.pvolan.archsample.entities.CityForecast;
import ru.pvolan.archsample.logic.exception.LogicException;
import ru.pvolan.archsample.network.exception.NetworkException;
import ru.pvolan.archsample.network.forecast.ForecastAPI;
import ru.pvolan.archsample.storage.forecast.ForecastStorage;

public class ForecastLogic {

    public static final int CACHE_LIFETIME_HOURS = 4;

    private ForecastStorage forecastStorage;
    private ForecastAPI forecastAPI;


    public ForecastLogic(ForecastStorage forecastStorage, ForecastAPI forecastAPI) {
        this.forecastStorage = forecastStorage;
        this.forecastAPI = forecastAPI;
    }

    /*
      Get forecast from cache or network
     */
    public CityForecast obtainCityForecast(String cityName) throws LogicException {

        ForecastStorage.CityForecastCache cache = forecastStorage.findCityForecast(cityName);

        if(cache != null){
            Calendar now = Calendar.getInstance();
            Calendar cacheUpdatedAt = cache.getLastUpdatedAt();
            Calendar cacheExpiresAt = ((Calendar)cacheUpdatedAt.clone());
            cacheExpiresAt.add(Calendar.HOUR, CACHE_LIFETIME_HOURS);

            if(cacheUpdatedAt.compareTo(now) < 0 &&
                    now.compareTo(cacheExpiresAt) < 0 ){
                return cache.getCityForecast();
            }
        }

        try {
            CityForecast downloadedForecast = forecastAPI.getForecast(cityName);
            forecastStorage.putCityForecast(downloadedForecast);
            return downloadedForecast;
        } catch (NetworkException e) {
            //Here I just pass an exception with the same message tu upper level,
            //but more specific processing is possible
            throw new LogicException(e.getMessage(), e);
        }

    }

}
