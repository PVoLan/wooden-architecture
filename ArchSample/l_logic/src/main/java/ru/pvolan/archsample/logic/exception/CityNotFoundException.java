package ru.pvolan.archsample.logic.exception;

import android.content.Context;

import ru.pvolan.archsample.logic.R;

public class CityNotFoundException extends LogicException {

    public CityNotFoundException(Context appContext) {
        super(appContext.getString(R.string.city_not_found));
    }

}
