package com.vekain19.prathamvisioncodingchallenge.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vekain19.prathamvisioncodingchallenge.R;
import com.vekain19.prathamvisioncodingchallenge.helpers.DataBase.Db_Helper;
import com.vekain19.prathamvisioncodingchallenge.views.activities.HomeActivity;

import static android.content.Context.MODE_PRIVATE;

public class SignUpFragment extends Fragment {

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    private EditText etUser_Phone = null;
    private EditText et_reg_fname = null;
    private EditText et_password = null;
    private Button Login_by_phone;
    private CoordinatorLayout snackBarView;
    private Db_Helper dataBaseObject;
    private TextView existingUser;
    private ImageView googleSignInButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
             final View rootView =  inflater.inflate(R.layout.fragment_sign_up, container, false);

        etUser_Phone = rootView.findViewById(R.id.etUser_Phone);
        et_reg_fname = rootView.findViewById(R.id.et_reg_fname);
        et_password = rootView.findViewById(R.id.password);
        Login_by_phone= rootView.findViewById(R.id.user_button_login);
        snackBarView = rootView.findViewById(R.id.snackview);
        dataBaseObject = new Db_Helper(getContext());
        existingUser = rootView.findViewById(R.id.existingUser);
        googleSignInButton = rootView.findViewById(R.id.googleSinInButton);

        Login_by_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_reg_fname == null || et_reg_fname.getText().toString().length() < 1) {
                    showSnackBar("Please enter first name");
                    et_reg_fname.requestFocus();
                }
                else if (et_password == null || et_password.getText().toString().length() < 1) {
                    showSnackBar("Please enter a valid password");
                    et_password.requestFocus();
                }
                else if (etUser_Phone == null || etUser_Phone.getText().toString().length() < 10) {
                    showSnackBar("Please enter a valid phone number");
                    etUser_Phone.requestFocus();
                }
                else {
                    CreateNewUser(rootView);
                }
            }
        });

        existingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                );
                transaction.replace(R.id.baseParentForAuthentication, LogInFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackBar("Not included in the Task !!");
            }
        });


        return  rootView;
    }

    private void CreateNewUser(View view) {
        try {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            if (dataBaseObject.inserNewUserData(
                    et_reg_fname.getText().toString().trim(),
                    etUser_Phone.getText().toString().trim(),
                    et_password.getText().toString().trim())) {

                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("user_id_value", etUser_Phone.getText().toString());
                editor.putString("first_name_value", et_reg_fname.getText().toString());
                editor.apply();

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                getContext().startActivity(intent);
                this.getActivity().finish();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void showSnackBar(String message)
    {
        Snackbar.make(snackBarView,message,Snackbar.LENGTH_SHORT).show();
    }

}
