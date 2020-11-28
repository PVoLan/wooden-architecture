package ru.pvolan.archsample.activities.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ru.pvolan.archsample.R;
import ru.pvolan.archsample.activities.settings.SettingsActivity;
import ru.pvolan.archsample.usecases.main.MainScreenUseCase;
import ru.pvolan.uitools.edittext.EditTextHelper;

public class MainActivity extends AppCompatActivity {


    private static final int SETTINGS_REQUEST_CODE = 1001;

    private EditText editCityName;
    private View progressFrame;
    private TextView textError;
    private ForecastAdapter forecastAdapter;
    private MainActivityVM viewModel;


    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCityName = findViewById(R.id.editCityName);
        progressFrame = findViewById(R.id.progressFrame);
        textError = findViewById(R.id.textError);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        forecastAdapter = new ForecastAdapter();
        recyclerView.setAdapter(forecastAdapter);

        editCityName.setOnEditorActionListener((v, actionId, event) -> onSearchEditAction());

        findViewById(R.id.buttonClearCache).setOnClickListener(v -> onClearCacheClick());

        viewModel = new ViewModelProvider(this).get(MainActivityVM.class);

        viewModel.getErrorText().observe(this, s -> onErrorTextChanged(s) );
        viewModel.getProgressVisible().observe(this, s -> onProgressVisibleChanged(s) );
        viewModel.getData().observe(this, s -> onDataChanged(s) );
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);

        MenuItem itemSettings = menu.findItem (R.id.menu_settings);
        VectorDrawableCompat settingsIcon = VectorDrawableCompat.create(getResources(), R.drawable.settings, null);
        itemSettings.setIcon(settingsIcon);


        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_settings:
                settingsMenuClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETTINGS_REQUEST_CODE && resultCode == RESULT_OK){
            viewModel.updateForecastIfNeeded();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // VM callbacks
    ///////////////////////////////////////////////////////////////////////////

    private void onProgressVisibleChanged(Boolean progressVisible) {
        progressFrame.setVisibility(progressVisible ? View.VISIBLE : View.GONE);
    }


    private void onErrorTextChanged(String text) {
        textError.setVisibility(text != null ? View.VISIBLE : View.GONE);
        textError.setText(text);
    }


    private void onDataChanged(MainScreenUseCase.ForecastData data) {
        if(data != null) {
            forecastAdapter.setData(data.getItems());
        } else {
            forecastAdapter.setData(new ArrayList<>());
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // User actions
    ///////////////////////////////////////////////////////////////////////////

    private boolean onSearchEditAction() {
        EditTextHelper.hideKeyboard(editCityName);
        updateForecast(false);
        return true;
    }


    private void onClearCacheClick() {
        EditTextHelper.hideKeyboard(editCityName);
        updateForecast(true);
    }


    private void updateForecast(boolean clearCache){
        viewModel.loadForecast(editCityName.getText().toString(), clearCache);
    }


    private void settingsMenuClick() {
        SettingsActivity.startForResult(this, SETTINGS_REQUEST_CODE);
    }

}