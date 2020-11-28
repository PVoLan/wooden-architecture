package ru.pvolan.archsample.activities.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.pvolan.archsample.R;
import ru.pvolan.archsample.usecases.settings.SettingsScreenUseCase;
import ru.pvolan.uitools.toast.ToastHelper;

public class SettingsActivity extends AppCompatActivity {

    public static void startForResult(Activity src, int requestCode) {
        Intent starter = new Intent(src, SettingsActivity.class);
        src.startActivityForResult(starter, requestCode);
    }


    private EditText editMinTemperature;
    private EditText editMaxTemperature;

    private SettingsActivityVM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        editMinTemperature = findViewById(R.id.editMinTemperature);
        editMaxTemperature = findViewById(R.id.editMaxTemperature);

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSaveClicked();
            }
        });


        viewModel = new ViewModelProvider(this).get(SettingsActivityVM.class);

        if(savedInstanceState == null /*first launch*/){
            initSettings();
        }
    }


    private void initSettings() {
        SettingsScreenUseCase.Settings settings = viewModel.initSettings();
        editMaxTemperature.setText(Integer.toString(settings.getMaxTemperature()));
        editMinTemperature.setText(Integer.toString(settings.getMinTemperature()));
    }

    private void buttonSaveClicked() {
        int minTemp, maxTemp;

        try {
            minTemp = Integer.parseInt(editMinTemperature.getText().toString());
            maxTemp = Integer.parseInt(editMaxTemperature.getText().toString());
        } catch (NumberFormatException e) {
            ToastHelper.show(this, R.string.settings_invalid_temperature);
            return;
        }

        viewModel.saveSettings(
                new SettingsScreenUseCase.Settings(minTemp, maxTemp)
        );

        setResult(RESULT_OK);
        finish();
    }
}
