package com.vekain19.prathamvisioncodingchallenge.views.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.vekain19.prathamvisioncodingchallenge.R;
import com.vekain19.prathamvisioncodingchallenge.views.fragments.SignUpFragment;

public class UserAuthentication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left
        );
        transaction.replace(R.id.baseParentForAuthentication, SignUpFragment.newInstance());
        transaction.commit();
    }
}
