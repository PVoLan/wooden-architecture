package ru.pvolan.archsample.usecases.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.pvolan.archsample.entities.CityForecast;
import ru.pvolan.archsample.logic.exception.LogicException;
import ru.pvolan.archsample.logic.forecast.ForecastLogic;
import ru.pvolan.archsample.usecases.exception.UCException;
import ru.pvolan.tools.calendar.CalendarHelper;

public class MainScreenUseCase {

    private ForecastLogic forecastLogic;

    public MainScreenUseCase(ForecastLogic forecastLogic) {
        this.forecastLogic = forecastLogic;
    }


    public ForecastData getForecast(GetForecastInput input) throws UCException {
        CityForecast forecast;

        if(input.clearCache){
            forecastLogic.clearCache();
        }

        try {
            forecast = forecastLogic.obtainCityForecast(input.cityName);
        } catch (LogicException e) {
            //Here we just pass the exception to upper level with same message,
            //but more complicated handling possible, if needed
            throw new UCException(e.getMessage(), e);
        }

        return prepareResult(forecast);
    }


    private ForecastData prepareResult(CityForecast forecast) {

        List<ForecastItem> items = new ArrayList<>(forecast.getForecasts().size());

        for (CityForecast.Forecast dayForecast : forecast.getForecasts()) {
            String dateTime = CalendarHelper.format(dayForecast.getLocalDateTime(), "dd MMM HH:mm", dayForecast.getLocalDateTime().getTimeZone());
            String temperature = String.format(Locale.getDefault(), "%+d\u00B0C", (Math.round(dayForecast.getTemperatureCelsius())) );

            items.add(new ForecastItem(dateTime, temperature));
        }

        return new ForecastData(items);
    }



    public static class GetForecastInput {
        private String cityName;
        private boolean clearCache;

        public GetForecastInput(String cityName, boolean clearCache) {
            this.cityName = cityName;
            this.clearCache = clearCache;
        }
    }

    public static class ForecastItem {
        private String dateTime;
        private String temperature;

        public ForecastItem(String dateTime, String temperature) {
            this.dateTime = dateTime;
            this.temperature = temperature;
        }

        public String getDateTime() {
            return dateTime;
        }

        public String getTemperature() {
            return temperature;
        }
    }


    public static class ForecastData {
        private List<ForecastItem> items;

        public ForecastData(List<ForecastItem> items) {
            this.items = items;
        }

        public List<ForecastItem> getItems() {
            return items;
        }
    }
}
