package ru.pvolan.archsample.network.forecast.fake;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import ru.pvolan.archsample.entities.City;
import ru.pvolan.archsample.entities.CityForecast;
import ru.pvolan.archsample.network.exception.NetworkException;
import ru.pvolan.archsample.network.forecast.IForecastAPI;

public class FakeForecastAPI implements IForecastAPI {

    Random r = new Random();

    @Override
    public CityForecast getForecast(String cityName) throws NetworkException {

        if(r.nextInt(10) < 2) throw new NetworkException("No fake connection, try again");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        City city = new City(cityName, 20, 30);

        int fakeForecastsCount = 20;
        List<CityForecast.Forecast> forecasts = new ArrayList<>(fakeForecastsCount);
        for (int i = 0; i < fakeForecastsCount; i++) {

            Calendar time = Calendar.getInstance();
            time.add(Calendar.HOUR, i*3);

            float temperature = cityName.length() + i%10;

            CityForecast.Forecast forecast = new CityForecast.Forecast(
                    time,
                    temperature
            );
            forecasts.add(forecast);
        }

        return new CityForecast(city, forecasts);
    }
}
