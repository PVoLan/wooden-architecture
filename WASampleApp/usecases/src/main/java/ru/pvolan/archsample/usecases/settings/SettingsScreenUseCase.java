package ru.pvolan.archsample.usecases.settings;

import ru.pvolan.archsample.storage.settings.SettingsStorage;

public class SettingsScreenUseCase {

    private SettingsStorage settingsStorage;

    public SettingsScreenUseCase(SettingsStorage settingsStorage) {
        this.settingsStorage = settingsStorage;
    }


    public Settings initSettings(){
        return new Settings(
                settingsStorage.readMinGoodTemperature(),
                settingsStorage.readMaxGoodTemperature()
        );
    }


    public void saveSettings(Settings settings){
        settingsStorage.saveMinGoodTemperature(settings.minTemperature);
        settingsStorage.saveMaxGoodTemperature(settings.maxTemperature);
    }


    public static class Settings{
        private int minTemperature;
        private int maxTemperature;

        public Settings(int minTemperature, int maxTemperature) {
            this.minTemperature = minTemperature;
            this.maxTemperature = maxTemperature;
        }

        public int getMinTemperature() {
            return minTemperature;
        }

        public int getMaxTemperature() {
            return maxTemperature;
        }
    }
}
