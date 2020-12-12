package ru.pvolan.archsample.activities.settings;

import androidx.lifecycle.ViewModel;

import ru.pvolan.archsample.application.SampleApp;
import ru.pvolan.archsample.usecases.settings.SettingsScreenUseCase;


//Note. This activity is quire simple, so theoretically we could omit ViewModel layer
//But ViewModel layer is still presented for consistency and as a reserve for future needs
public class SettingsActivityVM extends ViewModel {

    public SettingsScreenUseCase.Settings initSettings(){
        return SampleApp.cnt().getSettingsScreenUseCase().initSettings();
    }


    public void saveSettings(SettingsScreenUseCase.Settings settings){
        SampleApp.cnt().getSettingsScreenUseCase().saveSettings(settings);
    }

}
