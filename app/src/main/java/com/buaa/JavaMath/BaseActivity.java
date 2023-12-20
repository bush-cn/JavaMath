package com.buaa.JavaMath;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * @Description
 * @author Bush
 * @date 2023/12/17 16:09
**/
public class BaseActivity extends AppCompatActivity {
    public static SharedPreferences preferences;

    /**
     *
     * @param savedInstanceState
     * @author Bush
     * @date 2023/12/17 16:09
    **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    /**
     *
     * @param item
     * @return boolean
     * @author Bush
     * @date 2023/12/17 16:08
    **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
