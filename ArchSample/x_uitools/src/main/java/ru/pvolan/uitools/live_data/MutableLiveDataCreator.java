package ru.pvolan.uitools.live_data;


import androidx.lifecycle.MutableLiveData;

public class MutableLiveDataCreator {

    public static <T> MutableLiveData<T> create(T initialValue){
        MutableLiveData<T> data = new MutableLiveData<>();
        data.setValue(initialValue);
        return data;
    }

}
