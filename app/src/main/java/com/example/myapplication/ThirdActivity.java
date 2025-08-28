package com.example.myapplication;
import static com.example.myapplication.SubActivity.sp_factor;
import static com.example.myapplication.SubActivity.sp_factor_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {
    public static SharedPreferences sp_1;
    public static SharedPreferences sp_2;
    public static SharedPreferences sp_key;
    public static SharedPreferences bool;
    public static Integer k = 0;
    public static Integer project_countable;
    ArrayList<String> data = new ArrayList<>(1000);
    ArrayList<String> data_2 = new ArrayList<>(1000);
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        EditText factor_1 = findViewById(R.id.factor_1);
        EditText factor_2 = findViewById(R.id.factor_2);
        Intent intent = getIntent();
        intent.getAction();
        String factor1 = intent.getStringExtra("factor_1");
        String factor2 = intent.getStringExtra("factor_2");
        Integer index = intent.getIntExtra("put", 0);
        Integer datacount = intent.getIntExtra("datacount", 0);
        factor_1.setText(factor1);
        factor_2.setText(factor2);
        project_countable = intent.getIntExtra("name",0);
        EditText data1_1 = findViewById(R.id.data1_1);
        EditText data2_1 = findViewById(R.id.data2_1);
        Button button_next2 = findViewById(R.id.button_next_2);
        Button button_save = findViewById(R.id.button_save);
        k = load_k();
        EditText count = findViewById(R.id.count);
        String text = String.valueOf(k) + " " + "/" + " " + String.valueOf(datacount);
        count.setText(text);
        Intent intent_2 = new Intent(getApplicationContext(), FourthActivity.class);
        button_save.setOnClickListener(new View.OnClickListener() {

            public void onClick (View v) {
                if(load_boolean() == true) {
                    data.add(data1_1.getText().toString());
                    data_2.add(data2_1.getText().toString());
                    saveData();
                    saveData_2();
                    save_boolean();
                    k++;
                    save_k();
                    Toast.makeText(ThirdActivity.this,"SAVED",Toast.LENGTH_SHORT).show();
                }
                else {
                    data = loadData_1();
                    data_2 = loadData_2();
                    data.add(data1_1.getText().toString());
                    data_2.add(data2_1.getText().toString());
                    saveData();
                    saveData_2();
                    k++;
                    save_k();
                    Toast.makeText(ThirdActivity.this,"SAVED",Toast.LENGTH_SHORT).show();
                }

            }

        });


        button_next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThirdActivity.this,"Calculating...",Toast.LENGTH_SHORT).show();
                ArrayList<Integer> data_double = new ArrayList<Integer>(1000);
                ArrayList<Integer> data_2_double = new ArrayList<Integer>(1000);
                data = loadData_1();
                data_2 = loadData_2();
                for(int i = 0; i<data.size(); i++) {
                    Integer a = 0;
                    a = Integer.parseInt(data.get(i));
                    data_double.add(a);
                }
                for(int i = 0; i<data_2.size(); i++) {
                    Integer a = 0;
                    a = Integer.parseInt(data_2.get(i));
                    data_2_double.add(a);
                }
                ArrayList<Double> DoubleData = new ArrayList<Double>(1000);
                ArrayList<Double> DoubleData_2 = new ArrayList<Double>(1000);

                for(int i = 0; i<data_double.size(); i++) {
                    double a = new Double(data_double.get(i));
                    double b = new Double(data_2_double.get(i));
                    System.out.println(a);
                    DoubleData.add(a);
                    DoubleData_2.add(a);
                }
                double average_1 = 0.0;
                double average_2 = 0.0;
                double k1 = 0.0;
                double k2 = 0.0;
                double r = 0.0;

                for (int i = 0; i < data_double.size(); i++) {
                    k1 += DoubleData.get(i);
                }
                double double_data = new Double(data_double.size());
                average_1 = k1 / (double_data);
                for (int i = 0; i < data_2_double.size(); i++) {
                    k2 += DoubleData_2.get(i);

                }
                double double_data_2 = new Double(data_2_double.size());
                average_2 = k2 / (double_data_2);

                k1 = 0.0;
                k2 = 0.0;

                double k3 = 0.0;
                double k4 = 0.0;
                double k5 = 0.0;

                for (int i = 0; i < data.size(); i++) {
                    k1 += DoubleData.get(i) - average_1;
                    k2 += DoubleData_2.get(i) - average_2;
                    k3 += k1 * k2;
                    k4 += Math.pow((DoubleData.get(i) - average_1), 2);
                    k5 += Math.pow((DoubleData_2.get(i) - average_2), 2);


                }
                r = k3 / Math.sqrt(k4 * k5);
                String result = "";
                double slope = 0.0;
                double constant = 0.0;
                if(r <= 0.5) {
                    result = "fff";
                    slope = k3/k4;
                    String slope_string = Double.toString(slope);
                    constant = average_2 - slope * average_1;
                    String constant_string = Double.toString(constant);
                    intent_2.putExtra("slope", slope_string);
                    intent_2.putExtra("constant", constant_string);
                    intent_2.putExtra("factor11", factor1);
                    intent_2.putExtra("factor22", factor2);
                    intent_2.putExtra("put", index);
                    startActivity(intent_2);
                }
                if(r > 0.5) {
                    result = "You can predict based on";
                    slope = k3 / k4;
                    String slope_string = Double.toString(slope);
                    constant = average_2 - slope * average_1;
                    String constant_string = Double.toString(constant);
                    intent_2.putExtra("slope", slope_string);
                    intent_2.putExtra("constant", constant_string);
                    intent_2.putExtra("factor11", factor1);
                    intent_2.putExtra("factor22", factor2);
                    intent_2.putExtra("put", index);
                    startActivity(intent_2);

                }
            }

        });




    }
    private void saveData() {
        sp_1 = getApplicationContext().getSharedPreferences("list_data_1", MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        Type listType1 = new TypeToken<ArrayList<String>>() {}.getType();
        String json = gson.toJson(data, listType1);
        SharedPreferences.Editor editor = sp_1.edit();
        String name = "datas_" + String.valueOf(project_countable);
        editor.putString(name, json);
        editor.apply();

    }
    private void saveData_2() {
        sp_2 = getApplicationContext().getSharedPreferences("list_data_2", MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        Type listType1 = new TypeToken<ArrayList<String>>() {}.getType();
        String json = gson.toJson(data_2, listType1);
        SharedPreferences.Editor editor = sp_2.edit();
        String name = "datas_2_" + String.valueOf(project_countable);
        editor.putString(name, json);
        editor.apply();

    }
    private ArrayList<String> loadData_1() {
        sp_1 = getApplicationContext().getSharedPreferences("list_data_1", MODE_PRIVATE);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        String name = "datas_" + String.valueOf(project_countable);
        String strContact = sp_1.getString(name, null);
        data = gson.fromJson(strContact, listType);
        if(data == null) {
            data = new ArrayList<String>(1000);
        }

        return data;


    }
    private ArrayList<String> loadData_2() {
        sp_2 = getApplicationContext().getSharedPreferences("list_data_2", MODE_PRIVATE);
        Gson gson = new Gson();
        ArrayList<String> list_data_2 = new ArrayList<>(1000);
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        String name = "datas_2_" + String.valueOf(project_countable);
        String strContact = sp_2.getString(name, null);
        data_2 = gson.fromJson(strContact, listType);
        if(data_2 == null) {
            data_2 = new ArrayList<String>(1000);
        }
        return data_2;
    }
    private void save_boolean() {
        bool = getApplicationContext().getSharedPreferences("bool", MODE_PRIVATE);
        SharedPreferences.Editor editor = bool.edit();
        String name = "bool_" + String.valueOf(project_countable);
        editor.putBoolean(name, false);
        editor.apply();


    }
    private Boolean load_boolean()  {
        bool = getApplicationContext().getSharedPreferences("bool", MODE_PRIVATE);
        String name = "bool_" + String.valueOf(project_countable);
        Boolean boole = bool.getBoolean(name, true);

        return boole;
    }
    private void save_k() {
        sp_key = getApplicationContext().getSharedPreferences("key", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp_key.edit();
        String name = "ikey_" + String.valueOf(project_countable);
        edit.putInt(name, k);
        edit.apply();
    }
    private Integer load_k() {
        sp_key = getApplicationContext().getSharedPreferences("key", MODE_PRIVATE);
        String name = "ikey_" + String.valueOf(project_countable);
        Integer k_value = sp_key.getInt(name, 0);
        return k_value;

    }

    //save k




}

