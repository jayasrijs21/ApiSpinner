package com.example.apispinner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    Spinner spinner1;
    TextView tv1;
    ProgressBar progressBar;
    ArrayAdapter<String> adapter;

    List<SpinnerModel> spinnerModelList = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner1 = findViewById(R.id.spinner1);

        tv1 = findViewById(R.id.tvtext1);

        progressBar = findViewById(R.id.progressBar3);

        fetchContents();

    }

    private void fetchContents() {
        progressBar.setVisibility(View.VISIBLE);

        RetrofitClient.getRetrofitClient().getSpinnerModels().enqueue(new Callback<List<SpinnerModel>>() {
            @Override
            public void onResponse(Call<List<SpinnerModel>> call, Response<List<SpinnerModel>> response) {

                List<SpinnerModel> spinnerModelList = response.body();
                if (spinnerModelList != null && spinnerModelList.size() > 0) {
                    String[] names = new String[spinnerModelList.size()];
                    for (int i = 0; i < spinnerModelList.size(); i++) {
                        names[i] = spinnerModelList.get(i).getName();

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,names);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);

                    }
                }
            }


            @Override
            public void onFailure(Call<List<SpinnerModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                

            }
        });


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String value = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }
}