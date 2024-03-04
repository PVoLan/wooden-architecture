package ru.pvolan.archsample.usecases.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.pvolan.archsample.entities.CityForecast;
import ru.pvolan.archsample.logic.exception.LogicException;
import ru.pvolan.archsample.logic.forecast.ForecastLogic;
import ru.pvolan.archsample.storage.settings.SettingsStorage;
import ru.pvolan.archsample.usecases.exception.UCException;
import ru.pvolan.tools.calendar.CalendarHelper;

public class MainScreenUseCase {

    private ForecastLogic forecastLogic;
    private SettingsStorage settingsStorage;

    public MainScreenUseCase(ForecastLogic forecastLogic, SettingsStorage settingsStorage) {
        this.forecastLogic = forecastLogic;
        this.settingsStorage = settingsStorage;
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

        int minGoodTemperature = settingsStorage.readMinGoodTemperature();
        int maxGoodTemperature = settingsStorage.readMaxGoodTemperature();
        return prepareResult(forecast, minGoodTemperature, maxGoodTemperature);
    }


    private ForecastData prepareResult(CityForecast forecast, int minGoodTemperature, int maxGoodTemperature) {

        List<ForecastItem> items = new ArrayList<>(forecast.getForecasts().size());

        for (CityForecast.Forecast dayForecast : forecast.getForecasts()) {
            String dateTime = CalendarHelper.format(dayForecast.getLocalDateTime(), "dd MMM HH:mm", dayForecast.getLocalDateTime().getTimeZone());
            int temperatureInt = Math.round(dayForecast.getTemperatureCelsius());
            String temperatureString = String.format(Locale.getDefault(), "%+d\u00B0C", temperatureInt );

            boolean isWeatherGoodForRunning =
                    minGoodTemperature <= temperatureInt &&
                            temperatureInt <= maxGoodTemperature;
            items.add(new ForecastItem(dateTime, temperatureString, isWeatherGoodForRunning));
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
        private boolean isWeatherGoodForRunning;

        public ForecastItem(String dateTime, String temperature, boolean isWeatherGoodForRunning) {
            this.dateTime = dateTime;
            this.temperature = temperature;
            this.isWeatherGoodForRunning = isWeatherGoodForRunning;
        }

        public String getDateTime() {
            return dateTime;
        }

        public String getTemperature() {
            return temperature;
        }

        public boolean isWeatherGoodForRunning() { return isWeatherGoodForRunning; }
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
