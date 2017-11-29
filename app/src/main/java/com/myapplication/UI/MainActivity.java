package com.myapplication.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DeviceNetworkStatusFragment deviceNetworkStatusFragment = DeviceNetworkStatusFragment.newInstance();
        openFragment(deviceNetworkStatusFragment);

    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.main_layout, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        FragmentManager fm = getFragmentManager();

        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        }
        else {
            finish();
        }

    }
}
