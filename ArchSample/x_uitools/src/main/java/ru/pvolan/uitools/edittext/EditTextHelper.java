package ru.pvolan.uitools.edittext;

import android.content.Context;
import android.os.ResultReceiver;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class EditTextHelper {

    public static void showKeyboard(final View editText) {
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                InputMethodManager imm = ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                imm.showSoftInput(editText, 0);
            }
        }, 100);
    }


    public static void showKeyboard(final View editText, final ResultReceiver resultReceiver) {
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                InputMethodManager imm = ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                imm.showSoftInput(editText, 0, resultReceiver);
            }
        }, 100);
    }


    public static void hideKeyboard(final View editText) {
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }

        }, 100);
    }


    public static void hideKeyboardWithoutPost(final View editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
