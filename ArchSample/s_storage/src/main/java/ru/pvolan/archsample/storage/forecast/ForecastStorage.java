package ru.pvolan.archsample.storage.forecast;

import androidx.room.RoomDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.pvolan.archsample.entities.City;
import ru.pvolan.archsample.entities.CityForecast;
import ru.pvolan.archsample.storage.tools.db.DBInstance;
import ru.pvolan.tools.calendar.CalendarHelper;

public class ForecastStorage {

    private static final String lastUpdatedFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String forecastDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private ForecastDao dao;
    private RoomDatabase db;

    private final Object sync = new Object();

    public ForecastStorage(DBInstance dbInstance){
        dao = dbInstance.getRoomDatabase().getForecastDao();
        db = dbInstance.getRoomDatabase();
    }



    public CityForecastCache findCityForecast(String name) {
        synchronized (sync) {

            try {
                CityForecastDTO cityForecastDTO = dao.findForecast(name);

                if(cityForecastDTO == null) return null;

                List<ForecastDTO> forecastDTOs = dao.getForecasts(cityForecastDTO.id);

                List<CityForecast.Forecast> forecasts = new ArrayList<>(forecastDTOs.size());
                for (ForecastDTO forecastDTO : forecastDTOs) {
                    forecasts.add(new CityForecast.Forecast(
                            CalendarHelper.parseFormat(forecastDTO.localDateTime, forecastDateFormat),
                            forecastDTO.temperatureCelsius
                    ));
                }

                Calendar updatedAt = CalendarHelper.parseFormat(cityForecastDTO.lastUpdated, lastUpdatedFormat);

                CityForecast cityForecast = new CityForecast(
                        new City(
                                cityForecastDTO.cityName,
                                cityForecastDTO.lat,
                                cityForecastDTO.lng
                        ),
                        forecasts
                );

                return new CityForecastCache(cityForecast, updatedAt);

            } catch (ParseException e) {
                //This exception can be thrown by CalendarHelper.parseFormat() calls
                //All the dates are inserted by our application, and inconsistent format in database
                //is definitely unexpected bug
                throw new RuntimeException(e);
            }
        }
    }


    //Replaces existent one
    public void putCityForecast(CityForecast cityForecast){
        db.runInTransaction((Runnable) () -> {
            synchronized (sync) { //Transaction is not enough due to lastUpdated generation

                CityForecastDTO oldForecast = dao.findForecast(cityForecast.getCity().getName());
                if (oldForecast != null) {
                    dao.deleteForecasts(oldForecast.id);
                    dao.deleteCityForecast(oldForecast.id);
                }

                Calendar now = Calendar.getInstance();
                CityForecastDTO newCityForecastDto = new CityForecastDTO(
                        0,
                        cityForecast.getCity().getName(),
                        CalendarHelper.format(now, lastUpdatedFormat),
                        cityForecast.getCity().getLat(),
                        cityForecast.getCity().getLng()
                );

                long cityForecastId = dao.insertCityForecast(newCityForecastDto);

                List<ForecastDTO> forecastDTOs = new ArrayList<>(cityForecast.getForecasts().size());
                for (CityForecast.Forecast forecast : cityForecast.getForecasts()) {
                    forecastDTOs.add(new ForecastDTO(
                            cityForecastId,
                            CalendarHelper.format(forecast.getLocalDateTime(), forecastDateFormat),
                            forecast.getTemperatureCelsius()
                    ));
                }

                dao.insertForecasts(forecastDTOs);

            }
        });
    }


    public static class CityForecastCache
    {
        private CityForecast cityForecast;
        private Calendar lastUpdatedAt;

        public CityForecastCache(CityForecast cityForecast, Calendar lastUpdatedAt) {
            this.cityForecast = cityForecast;
            this.lastUpdatedAt = lastUpdatedAt;
        }

        public CityForecast getCityForecast() {
            return cityForecast;
        }

        public Calendar getLastUpdatedAt() {
            return lastUpdatedAt;
        }
    }
}
