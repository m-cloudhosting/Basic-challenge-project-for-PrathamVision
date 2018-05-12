package com.vekain19.prathamvisioncodingchallenge.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.vekain19.prathamvisioncodingchallenge.R;
import com.vekain19.prathamvisioncodingchallenge.helpers.Entity.Pojo;
import com.vekain19.prathamvisioncodingchallenge.views.adapters.DataListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SeriviceUserFragment extends Fragment {

   public static SeriviceUserFragment newInstance() {
      return new SeriviceUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     View rootView =  inflater.inflate(R.layout.fragment_serivice_user, container, false);

        FastScrollRecyclerView recyclerDataList = rootView.findViewById(R.id.contactsList);
        recyclerDataList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerDataList.setHasFixedSize(true);

        try {
            ArrayList<Pojo> datalist = new ArrayList<>();
            JSONArray dataArray = new JSONArray(loadJSONFromAsset());
            for (int i=0; i<dataArray.length(); i++)
            {
                JSONObject dataObject = dataArray.getJSONObject(i);
                Pojo data = new Pojo();
                data.setName(dataObject.getString("name"));
                data.setNumber(dataObject.getString("number"));
                datalist.add(data);
            }

            DataListAdapter dataListAdapter = new DataListAdapter(datalist,getContext());
            recyclerDataList.setAdapter(dataListAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return rootView;
    }


    public String loadJSONFromAsset() {
        String json = "";
        try {
            if (getActivity()!=null) {
                InputStream is = (getActivity())
                        .getAssets()
                        .open("contacts.json");

                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}