package ru.pvolan.archsample.logic.forecast;

import android.content.Context;

import java.util.Calendar;

import ru.pvolan.archsample.entities.CityForecast;
import ru.pvolan.archsample.logic.exception.CityNotFoundException;
import ru.pvolan.archsample.logic.exception.LogicException;
import ru.pvolan.archsample.network.exception.ErrorCodeRetrievedException;
import ru.pvolan.archsample.network.exception.NetworkException;
import ru.pvolan.archsample.network.forecast.IForecastAPI;
import ru.pvolan.archsample.storage.forecast.ForecastStorage;

public class ForecastLogic {

    public static final int CACHE_LIFETIME_HOURS = 4;

    private Context appContext;
    private ForecastStorage forecastStorage;
    private IForecastAPI forecastAPI;


    public ForecastLogic(Context appContext, ForecastStorage forecastStorage, IForecastAPI forecastAPI) {
        this.appContext = appContext;
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
            forecastStorage.putCityForecast(cityName, downloadedForecast);
            return downloadedForecast;
        } catch (ErrorCodeRetrievedException e){
            if(e.getErrorCode() == 404) throw new CityNotFoundException(appContext);
            else throw new LogicException(e.getMessage(), e);
        } catch (NetworkException e) {
            throw new LogicException(e.getMessage(), e); //Default - just pass to caller with same message
        }

    }


    public void clearCache() {
        forecastStorage.clear();
    }
}
