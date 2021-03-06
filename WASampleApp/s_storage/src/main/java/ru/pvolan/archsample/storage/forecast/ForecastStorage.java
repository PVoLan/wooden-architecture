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

    private DBInstance dbInstance;

    private final Object sync = new Object();

    public ForecastStorage(DBInstance dbInstance){
        this.dbInstance = dbInstance;
    }

    private ForecastDao dao(){ return dbInstance.getRoomDatabase().getForecastDao(); }

    private RoomDatabase db(){ return dbInstance.getRoomDatabase(); }



    public CityForecastCache findCityForecast(String cityName) {
        synchronized (sync) {

            try {
                CityDTO cityDTO = dao().findForecast(cityName);

                if(cityDTO == null) return null;

                List<ForecastDTO> forecastDTOs = dao().getForecasts(cityDTO.id);

                List<CityForecast.Forecast> forecasts = new ArrayList<>(forecastDTOs.size());
                for (ForecastDTO forecastDTO : forecastDTOs) {
                    forecasts.add(new CityForecast.Forecast(
                            CalendarHelper.parseFormat(forecastDTO.localDateTime, forecastDateFormat),
                            forecastDTO.temperatureCelsius
                    ));
                }

                Calendar updatedAt = CalendarHelper.parseFormat(cityDTO.lastUpdated, lastUpdatedFormat);

                CityForecast cityForecast = new CityForecast(
                        new City(
                                cityDTO.cityName,
                                cityDTO.lat,
                                cityDTO.lng
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
    public void putCityForecast(String cityName, CityForecast cityForecast){
        db().runInTransaction((Runnable) () -> {
            synchronized (sync) { //Transaction is not enough due to lastUpdated generation

                CityDTO oldForecast = dao().findForecast(cityName);
                if (oldForecast != null) {
                    dao().deleteForecasts(oldForecast.id);
                    dao().deleteCityForecast(oldForecast.id);
                }

                Calendar now = Calendar.getInstance();
                CityDTO newCityDto = new CityDTO(
                        0,
                        cityName,
                        CalendarHelper.format(now, lastUpdatedFormat),
                        cityForecast.getCity().getLat(),
                        cityForecast.getCity().getLng()
                );

                long cityForecastId = dao().insertCityForecast(newCityDto);

                List<ForecastDTO> forecastDTOs = new ArrayList<>(cityForecast.getForecasts().size());
                for (CityForecast.Forecast forecast : cityForecast.getForecasts()) {
                    forecastDTOs.add(new ForecastDTO(
                            cityForecastId,
                            CalendarHelper.format(forecast.getLocalDateTime(), forecastDateFormat),
                            forecast.getTemperatureCelsius()
                    ));
                }

                dao().insertForecasts(forecastDTOs);

            }
        });
    }


    public void clear() {

        db().runInTransaction((Runnable) () -> {
            synchronized (sync) { //Transaction is not enough due to lastUpdated generation
                dao().deleteAllCities();
                dao().deleteAllForecasts();
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
