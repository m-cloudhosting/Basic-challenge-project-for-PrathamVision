package com.vekain19.prathamvisioncodingchallenge.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vekain19.prathamvisioncodingchallenge.R;
import com.vekain19.prathamvisioncodingchallenge.helpers.DataBase.Db_Helper;
import com.vekain19.prathamvisioncodingchallenge.views.activities.HomeActivity;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class LogInFragment extends Fragment {

    public static LogInFragment newInstance() {
        return new LogInFragment();
    }

    private ImageView backButton;
    private EditText et_reg_fname = null;
    private EditText et_password = null;
    private Button Login_by_phone;
    private CoordinatorLayout snackBarView;
    private Db_Helper dataBaseObject;
    private RelativeLayout loadingBar;
    private ImageView googleSinInButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_log_in, container, false);

        backButton = view.findViewById(R.id.backButton);
        et_reg_fname = view.findViewById(R.id.et_reg_fname);
        et_password = view.findViewById(R.id.password);
        Login_by_phone= view.findViewById(R.id.user_button_login);
        snackBarView = view.findViewById(R.id.snackview);
        loadingBar = view.findViewById(R.id.loading_bar);
        googleSinInButton = view.findViewById(R.id.googleSinInButton);
        dataBaseObject = new Db_Helper(getContext());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.commit();
                manager.popBackStack();
            }
        });

        Login_by_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_reg_fname == null || et_reg_fname.getText().toString().length() < 1) {
                    showSnackBar("Please enter User name");
                    et_reg_fname.requestFocus();
                }
                else if (et_password == null || et_password.getText().toString().length() < 1) {
                    showSnackBar("Please enter a valid password");
                    et_password.requestFocus();
                }
                else {
                    loadingBar.setVisibility(View.VISIBLE);
                    verifyUser(view);
                }
            }
        });

        googleSinInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackBar("Not included in the Task !!");
            }
        });

        return  view;
    }

    public void verifyUser(View view){
        try {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if (comparePasswords(dataBaseObject.getUserByName(et_reg_fname.getText().toString().trim()))){

                    SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user_id_value", "9999916187");
                    editor.putString("first_name_value", et_reg_fname.getText().toString());
                    editor.apply();

                    loadingBar.setVisibility(View.GONE);

                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    getContext().startActivity(intent);
                    this.getActivity().finish();
                }else {
                    loadingBar.setVisibility(View.GONE);
                    Log.e("Matched","FALSE");
                    showSnackBar("Invalid Username or Password");
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private boolean comparePasswords(ArrayList<String> userByName) {
        boolean matched = false;
        for (int i=0; i<userByName.size();i++)
        {
            if (et_password.getText().toString().trim().equals(userByName.get(i)))
            {
                matched = true;
                break;
            }
            else matched = false;
        }

        return matched;
    }

    public void showSnackBar(String message)
    {
        Snackbar.make(snackBarView,message,Snackbar.LENGTH_SHORT).show();
    }
}
