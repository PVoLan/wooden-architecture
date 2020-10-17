package ru.pvolan.archsample.activities.main;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.pvolan.archsample.R;
import ru.pvolan.archsample.application.SampleApp;
import ru.pvolan.archsample.usecases.exception.UCException;
import ru.pvolan.archsample.usecases.main.MainScreenUseCase;
import ru.pvolan.tools.async_task.AsyncTaskEx;
import ru.pvolan.tools.ex_res.ExceptionalResult;
import ru.pvolan.tools.string.StringHelper;
import ru.pvolan.uitools.live_data.MutableLiveDataCreator;

public class MainActivityVM extends ViewModel {


    ///////////////////////////////////////////
    // Data

    private MutableLiveData<Boolean> progressVisible = MutableLiveDataCreator.create(false);
    private MutableLiveData<String> errorText = MutableLiveDataCreator.create(null);
    private MutableLiveData<MainScreenUseCase.ForecastData> data = MutableLiveDataCreator.create(null);

    public MutableLiveData<Boolean> getProgressVisible() {
        return progressVisible;
    }

    public MutableLiveData<String> getErrorText() {
        return errorText;
    }

    public MutableLiveData<MainScreenUseCase.ForecastData> getData() {
        return data;
    }


    /////////////////////////////////////////////
    //Activity actions


    void loadForecast(String cityName, boolean clearCache)
    {
        if(progressVisible.getValue()) return; //Accidental double click defense

        if(StringHelper.isNullOrEmpty(cityName)){
            errorText.setValue(SampleApp.getApp().getString(R.string.main_empty_city_name));
            return;
        }

        new LoadTask().execute(new MainScreenUseCase.GetForecastInput(cityName, clearCache));
    }



    //I personally like async task. But any other tool for async calls is also appropriate
    //PS. No, no memory leak can occur here - we are in ViewModel, not in Activity!
    private class LoadTask extends AsyncTaskEx<MainScreenUseCase.GetForecastInput, MainScreenUseCase.ForecastData> {

        @Override
        protected void onPreExecute() {
            progressVisible.setValue(true);
        }

        @Override
        protected MainScreenUseCase.ForecastData doInBackground(MainScreenUseCase.GetForecastInput input) throws Exception {
            return SampleApp.cnt().getMainScreenUseCase().getForecast(input);
        }

        @Override
        protected void onPostExecute(ExceptionalResult<MainScreenUseCase.ForecastData> result) {
            progressVisible.setValue(false);

            Exception error = result.getError();
            if(error != null){
                errorText.setValue(error.getMessage() != null ? error.getMessage() : "No error message?");
                data.setValue(null);
                return;
            }

            errorText.setValue(null);
            data.setValue(result.getData());
        }

    }
}
