package ru.pvolan.archsample.entities;

import java.util.Calendar;
import java.util.List;

import ru.pvolan.tools.calendar.CalendarHelper;

public class CityForecast {

    private final City city;
    private final List<Forecast> forecasts;

    public CityForecast(City city, List<Forecast> forecasts) {
        this.city = city;
        this.forecasts = forecasts;
    }

    public City getCity() {
        return city;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public static class Forecast {

        private final Calendar localDateTime;
        private final float temperatureCelsius;

        public Forecast(Calendar localDateTime, float temperatureCelsius) {
            this.localDateTime = localDateTime;
            this.temperatureCelsius = temperatureCelsius;
        }

        public Calendar getLocalDateTime() {
            return localDateTime;
        }

        public float getTemperatureCelsius() {
            return temperatureCelsius;
        }

        @Override
        public String toString() {
            return "Forecast{" +
                    "localDateTime=" + CalendarHelper.toNiceString(localDateTime) +
                    ", temperatureCelsius=" + temperatureCelsius +
                    '}';
        }
    }

}
