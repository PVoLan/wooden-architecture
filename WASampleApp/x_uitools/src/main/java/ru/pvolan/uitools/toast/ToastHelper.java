package ru.pvolan.uitools.toast;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    public static void show(Context c, CharSequence message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context c, int message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

}
