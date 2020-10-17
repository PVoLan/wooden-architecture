package ru.pvolan.archsample.activities.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ru.pvolan.archsample.R;
import ru.pvolan.archsample.usecases.main.MainScreenUseCase;
import ru.pvolan.uitools.edittext.EditTextHelper;

public class MainActivity extends AppCompatActivity {


    private EditText editCityName;
    private View progressFrame;
    private TextView textError;
    private ForecastAdapter forecastAdapter;
    private MainActivityVM viewModel;


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

        viewModel.getErrorText().observe(this, (Observer<String>) s -> onErrorTextChanged(s) );
        viewModel.getProgressVisible().observe(this, (Observer<Boolean>) s -> onProgressVisibleChanged(s) );
        viewModel.getData().observe(this, (Observer<MainScreenUseCase.ForecastData>) s -> onDataChanged(s) );
    }


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


    private boolean onSearchEditAction() {
        EditTextHelper.hideKeyboard(editCityName);
        viewModel.loadForecast(editCityName.getText().toString(), false);
        return true;
    }


    private void onClearCacheClick() {
        EditTextHelper.hideKeyboard(editCityName);
        viewModel.loadForecast(editCityName.getText().toString(), true);
    }

}