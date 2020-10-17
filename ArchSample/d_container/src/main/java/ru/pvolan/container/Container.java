package ru.pvolan.container;

import android.content.Context;

import ru.pvolan.archsample.logic.forecast.ForecastLogic;
import ru.pvolan.archsample.network.forecast.ForecastAPI;
import ru.pvolan.archsample.storage.forecast.ForecastStorage;
import ru.pvolan.archsample.storage.tools.db.DBInstance;
import ru.pvolan.archsample.usecases.main.MainScreenUseCase;

//Dependency injection container
//Here is the place where the model is assembled from parts

public class Container {

    private MainScreenUseCase mainScreenUseCase;

    public Container(Context appContext) {

        //Network
        ForecastAPI forecastAPI = new ForecastAPI(appContext);

        //Storage
        DBInstance dbInstance = new DBInstance(appContext);
        ForecastStorage forecastStorage = new ForecastStorage(dbInstance);

        //Logic
        ForecastLogic forecastLogic = new ForecastLogic(appContext, forecastStorage, forecastAPI);

        //Use case
        mainScreenUseCase = new MainScreenUseCase(forecastLogic);

    }

    public MainScreenUseCase getMainScreenUseCase() {
        return mainScreenUseCase;
    }
}
