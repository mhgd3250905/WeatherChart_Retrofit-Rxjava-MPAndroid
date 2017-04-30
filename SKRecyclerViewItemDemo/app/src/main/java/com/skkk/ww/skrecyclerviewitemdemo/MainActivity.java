package com.skkk.ww.skrecyclerviewitemdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDefaultFragment();
    }

    /**
     * 设置默认fragment
     */
    private void setDefaultFragment() {
        RecyclerViewFragment recyclerViewFragment=new RecyclerViewFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,recyclerViewFragment).commit();
    }


}
