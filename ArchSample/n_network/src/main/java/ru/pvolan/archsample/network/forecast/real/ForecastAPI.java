package ru.pvolan.archsample.network.forecast.real;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import ru.pvolan.archsample.entities.City;
import ru.pvolan.archsample.entities.CityForecast;
import ru.pvolan.archsample.network.exception.NetworkException;
import ru.pvolan.archsample.network.forecast.IForecastAPI;
import ru.pvolan.archsample.network.tools.ExecuteHelper;
import ru.pvolan.archsample.network.tools.PParam;
import ru.pvolan.archsample.network.tools.PRequest;
import ru.pvolan.archsample.network.tools.PResponse;
import ru.pvolan.archsample.network.tools.RequestHelper;
import ru.pvolan.archsample.network.tools.ResponseProcessor;
import ru.pvolan.archsample.network.tools.SuccessResultProcessor;
import ru.pvolan.tools.calendar.CalendarHelper;
import ru.pvolan.tools.json.JsonHelper;

public class ForecastAPI implements IForecastAPI {

    private Context appContext;
    private RequestHelper requestHelper;
    private ExecuteHelper executeHelper;

    public ForecastAPI(Context appContext) {
        this.appContext = appContext;
        this.requestHelper = new RequestHelper(appContext);
        this.executeHelper = new ExecuteHelper(appContext);
    }


    @Override
    public CityForecast getForecast(String cityName) throws NetworkException {

        PRequest request = requestHelper.createGetRequest("forecast",
                new PParam("q", cityName),
                new PParam("units", "metric"));

        PResponse response = executeHelper.executeSync(request);

        return new ResponseProcessor(appContext).processResponse(response, new SuccessResultProcessor<CityForecast>() {
            @Override
            public CityForecast processResult(JSONObject jsonResponse) throws JSONException {
                return parseResponse(jsonResponse);
            }
        });

    }


    private CityForecast parseResponse(JSONObject jsonResponse) throws JSONException {
        JSONObject jsonCity = JsonHelper.getJsonObject(jsonResponse, "city");
        JSONObject jsonCoords = JsonHelper.getJsonObject(jsonCity, "coord");

        City city = new City(
                JsonHelper.getString(jsonCity, "name"),
                JsonHelper.getFloat(jsonCoords, "lat"),
                JsonHelper.getFloat(jsonCoords, "lon")
        );

        int timeOffsetSec = JsonHelper.getInt(jsonCity, "timezone");
        TimeZone timeZone = createCustomTimeZone(timeOffsetSec);

        JSONArray jsonList = JsonHelper.getJsonArray(jsonResponse, "list");
        List<CityForecast.Forecast> forecast = JsonHelper.getListFromJsonArray(jsonList, jsonItem -> {
            long dateUnix = JsonHelper.getLong(jsonItem, "dt");
            Calendar dateCal = CalendarHelper.createFromUnix(dateUnix);
            dateCal.setTimeZone(timeZone);

            JSONObject jsonMain = JsonHelper.getJsonObject(jsonItem, "main");
            float temp = JsonHelper.getFloat(jsonMain, "temp");

            return new CityForecast.Forecast(dateCal, temp);
        });

        return new CityForecast(city, forecast);
    }


    private TimeZone createCustomTimeZone(int timeOffsetSec) {
        int timeOffsetMin = timeOffsetSec / 60;
        int hours = timeOffsetMin / 60;
        int mins = timeOffsetMin % 60;
        char sign = timeOffsetMin >=0 ? '+' : '-';
        String format = String.format("GMT%s%02d%02d", sign, hours, mins);
        return TimeZone.getTimeZone(format);
    }

}
