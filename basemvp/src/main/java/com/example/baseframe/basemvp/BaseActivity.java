package com.example.baseframe.basemvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 * @desc
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = contentLayout();
        if (layout > 0) {
            setContentView(layout);
        }
        init();
    }

    protected abstract int contentLayout();

    protected void init() {

    }
}
