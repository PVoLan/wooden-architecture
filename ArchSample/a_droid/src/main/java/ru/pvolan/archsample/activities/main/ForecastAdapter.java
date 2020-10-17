package ru.pvolan.archsample.activities.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.pvolan.archsample.R;
import ru.pvolan.archsample.usecases.main.MainScreenUseCase;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastItemVH>{

    private List<MainScreenUseCase.ForecastItem> data = new ArrayList<>();

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    @NonNull
    public ForecastItemVH onCreateViewHolder(ViewGroup parent, int viewType)  {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.forecast_item, parent, false);
        return new ForecastItemVH(v);
    }

    @Override
    public void onBindViewHolder(ForecastItemVH holder, int position) {
        holder.bind(data.get(position));
    }

    void setData(List<MainScreenUseCase.ForecastItem> data){
        this.data = data;
        notifyDataSetChanged();
    }

}


class ForecastItemVH extends RecyclerView.ViewHolder {

    TextView textDate;
    TextView textTemperature;

    public ForecastItemVH(@NonNull View itemView) {
        super(itemView);

        textDate = itemView.findViewById(R.id.textDate);
        textTemperature = itemView.findViewById(R.id.textTemperature);
    }

    void bind(MainScreenUseCase.ForecastItem forecastItem) {
        textDate.setText(forecastItem.getDateTime());
        textTemperature.setText(forecastItem.getTemperature());
    }

}

