package com.msc.idol.mypa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class SoftKeyboardRelativeLayout extends RelativeLayout {
    private boolean isKeyboardShown = false;
    private List<SoftKeyboardListener> listeners = new ArrayList<SoftKeyboardListener>();
    private float layoutMaxH = 0f; // max measured height is considered layout normal size
    private static final float DETECT_ON_SIZE_PERCENT = 0.8f;

    public SoftKeyboardRelativeLayout(Context context) {
        super(context);
    }

    public SoftKeyboardRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("NewApi")
    public SoftKeyboardRelativeLayout(Context context, AttributeSet attrs,
                                      int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int newH = MeasureSpec.getSize(heightMeasureSpec);
        if (newH > layoutMaxH) {
            layoutMaxH = newH;
        }
        if (layoutMaxH != 0f) {
            final float sizePercent = newH / layoutMaxH;
            if (!isKeyboardShown && sizePercent <= DETECT_ON_SIZE_PERCENT) {
                isKeyboardShown = true;
                for (final SoftKeyboardListener lsner : listeners) {
                    lsner.onSoftKeyboardShow();
                }
            } else if (isKeyboardShown && sizePercent > DETECT_ON_SIZE_PERCENT) {
                isKeyboardShown = false;
                for (final SoftKeyboardListener listener : listeners) {
                    listener.onSoftKeyboardHide();
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void addSoftKeyboardListener(SoftKeyboardListener listener) {
        listeners.add(listener);
    }

    public void removeSoftKeyboardListener(SoftKeyboardListener listener) {
        listeners.remove(listener);
    }

    // Callback
    public interface SoftKeyboardListener {
        public void onSoftKeyboardShow();

        public void onSoftKeyboardHide();
    }
}