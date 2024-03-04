package ru.pvolan.container;

import android.content.Context;

import ru.pvolan.archsample.logic.forecast.ForecastLogic;
import ru.pvolan.archsample.network.forecast.IForecastAPI;
import ru.pvolan.archsample.network.forecast.fake.FakeForecastAPI;
import ru.pvolan.archsample.network.forecast.real.ForecastAPI;
import ru.pvolan.archsample.storage.forecast.ForecastStorage;
import ru.pvolan.archsample.storage.settings.SettingsStorage;
import ru.pvolan.archsample.storage.tools.db.DBInstance;
import ru.pvolan.archsample.usecases.main.MainScreenUseCase;
import ru.pvolan.archsample.usecases.settings.SettingsScreenUseCase;

//Dependency injection container
//Here is the place where the model is assembled from parts

public class Container {

    private MainScreenUseCase mainScreenUseCase;
    private SettingsScreenUseCase settingsScreenUseCase;

    public Container(Context appContext) {

        //Network
        //Choose one you prefer
        IForecastAPI forecastAPI = new ForecastAPI(appContext);
        //IForecastAPI forecastAPI = new FakeForecastAPI();

        //Storage
        DBInstance dbInstance = new DBInstance(appContext);
        ForecastStorage forecastStorage = new ForecastStorage(dbInstance);
        SettingsStorage settingsStorage = new SettingsStorage(appContext);

        //Logic
        ForecastLogic forecastLogic = new ForecastLogic(appContext, forecastStorage, forecastAPI);

        //Use case
        mainScreenUseCase = new MainScreenUseCase(forecastLogic, settingsStorage);
        settingsScreenUseCase = new SettingsScreenUseCase(settingsStorage);

    }

    public MainScreenUseCase getMainScreenUseCase() {
        return mainScreenUseCase;
    }

    public SettingsScreenUseCase getSettingsScreenUseCase() {
        return settingsScreenUseCase;
    }
}
