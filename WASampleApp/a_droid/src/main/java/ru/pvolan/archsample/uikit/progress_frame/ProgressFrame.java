package ru.pvolan.archsample.uikit.progress_frame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.pvolan.archsample.R;

//White layout with progress bar in the center.
public class ProgressFrame extends FrameLayout {

    public ProgressFrame(@NonNull Context context) {
        super(context);
        init();
    }

    public ProgressFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        View.inflate(getContext(), R.layout.progress_frame, this);
        setBackgroundColor(getResources().getColor(R.color.colorWhite));
    }


    //Catches all touches, so no views below the layout are accidentally clicked
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
