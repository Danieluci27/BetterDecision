package com.example.myapplication;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class DataActivity extends AppCompatActivity {
    public static SharedPreferences sp_3;
    public static SharedPreferences sp_4;
    public static SharedPreferences sp_bool;
    public static int project_counting;
    public static int count = 0;
    public static SharedPreferences sp_counting;
    public static ArrayList<String> data_3 = new ArrayList<String>(1000);
    public static ArrayList<String> data_4 = new ArrayList<String>(1000);
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        String direction_text = " ";
        EditText variable1 = findViewById(R.id.variable_1);
        EditText variable2 = findViewById(R.id.variable_2);
        Intent intent = getIntent();
        intent.getAction();
        String variable_1 = intent.getStringExtra("variable_1");
        String variable_2 = intent.getStringExtra("variable_2");
        project_counting = intent.getIntExtra("name", 0);
        int indexfromHT = intent.getIntExtra("put", 0);
        String direction = intent.getStringExtra("direction");
        Integer totaldata = intent.getIntExtra("totaldata", 0);
        String type = intent.getStringExtra("type");
        variable1.setText(variable_1);
        variable2.setText(variable_2);
        count = load_count();
        EditText data1_3 = findViewById(R.id.data1_3);
        EditText data2_3 = findViewById(R.id.data2_3);
        Button button_next2 = findViewById(R.id.button_next_3);
        Button button_save = findViewById(R.id.button_save_1);
        EditText datacount = findViewById(R.id.datacount);
        String text = String.valueOf(count) + " " + "/" + " " + String.valueOf(totaldata);
        datacount.setText(text);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                if(load_boole() == true) {
                    data_3.add(data1_3.getText().toString());
                    data_4.add(data2_3.getText().toString());
                    saveData_3();
                    saveData_4();
                    save_boole();
                    count++;
                    save_count();
                    Toast.makeText(DataActivity.this, "SAVED", Toast.LENGTH_SHORT).show();
                }
                else {
                    data_3 = loadData_3();
                    data_4 = loadData_4();
                    data_3.add(data1_3.getText().toString());
                    data_4.add(data2_3.getText().toString());
                    saveData_3();
                    saveData_4();
                    count++;
                    save_count();
                    Toast.makeText(DataActivity.this, "SAVED", Toast.LENGTH_SHORT).show();
                }

            }

        });

        button_next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DataActivity.this, "Calculating...", Toast.LENGTH_SHORT);
                ArrayList<Integer> data_double = new ArrayList<Integer>(1000);
                ArrayList<Integer> data_2_double = new ArrayList<Integer>(1000);
                data_3 = loadData_3();
                data_4 = loadData_4();
                for(int i = 0; i < data_3.size(); i++) {
                    Integer a = 0;
                    a = Integer.parseInt(data_3.get(i));
                    data_double.add(a);
                    String five = "size" + String.valueOf(i);
                }
                for(int i = 0; i < data_4.size(); i++) {
                    Integer a = 0;
                    a = Integer.parseInt(data_4.get(i));
                    data_2_double.add(a);
                }
                ArrayList<Double> DoubleData = new ArrayList<Double>(1000);
                ArrayList<Double> DoubleData_2 = new ArrayList<Double>(1000);

                for(int i = 0; i<data_double.size(); i++) {
                    double a = Double.valueOf(data_double.get(i));
                    double b = Double.valueOf(data_2_double.get(i));
                    DoubleData.add(a);
                    DoubleData_2.add(b);
                }
                double average_1 = 0.0;
                double average_2 = 0.0;
                double k1 = 0.0;
                double k2 = 0.0;
                double r = 0.0;
                for (int i = 0; i < data_double.size(); i++) {
                    k1 += DoubleData.get(i);
                    k2 += DoubleData_2.get(i);
                }
                double data_double_size_1 = new Double(data_double.size());
                average_1 = k1 / data_double_size_1;
                double data_double_size_2 = (double) data_2_double.size();
                average_2 = k2 / data_double_size_2;

                double k4 = 0.0;
                double k5 = 0.0;
                double data_size_1 = new Double(DoubleData.size());
                double data_size_2 = new Double(DoubleData_2.size());
                for (int i = 0; i < data_3.size(); i++) {
                    k4 += (Math.pow((DoubleData.get(i) - average_1), 2)) / (data_size_1 - 1.0);
                    k5 += (Math.pow((DoubleData_2.get(i) - average_2), 2)) / (data_size_2 - 1.0);

                }
                double S_E_1 = k4;
                double S_E_2 = k5;
                double frac_1 = k4/(data_size_1);
                double frac_2 = k5/(data_size_2);

                double total_s_v = Math.sqrt(frac_1 + frac_2);

                double total_S_E = 2.262 * total_s_v;

                double CI_right = (average_1 - average_2) + total_S_E;
                double CI_Left = (average_1 - average_2) - total_S_E;
                Log.d("CI_right", String.valueOf(CI_right));
                System.out.println(CI_Left);
                String result = "";
                Intent intent_true = new Intent(getApplicationContext(), CIResultActivity.class);
                Intent intent_no = new Intent(getApplicationContext(), WrongActivity.class);

                    if(CI_Left > 0.0 && CI_right > 0.0 || CI_Left < 0.0 && CI_right < 0.0) {
                        intent_true.putExtra("CI_Right", String.valueOf(CI_right));
                        intent_true.putExtra("CI_Left", String.valueOf(CI_Left));
                        intent_true.putExtra("put", indexfromHT);
                        String Description = "Average" + " " + type + " " + "of" + " " + variable_1 + " " + direction + " " + "average" + " " + type + " " + "of" + " " + variable_2;
                        intent_true.putExtra("description", Description);
                        intent_true.putExtra("variable", variable_1);
                        startActivity(intent_true);
                    }
                    else {
                        startActivity(intent_no);
                    }



            }

        });




    }
    private void saveData_3() {
        sp_3 = getApplicationContext().getSharedPreferences("list_data_3", MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        String json = gson.toJson(data_3, listType);
        SharedPreferences.Editor editor = sp_3.edit();
        String name = "list_data_3_" + String.valueOf(project_counting);
        editor.putString(name, json);
        editor.apply();

    }
    private void saveData_4() {
        sp_4 = getApplicationContext().getSharedPreferences("list_data_4", MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        String json = gson.toJson(data_4,listType);
        SharedPreferences.Editor editor = sp_4.edit();
        String name = "list_data_4_" + String.valueOf(project_counting);
        editor.putString(name, json);
        editor.apply();

    }
    private ArrayList<String> loadData_3() {
        sp_3 = getApplicationContext().getSharedPreferences("list_data_3", MODE_PRIVATE);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        String name = "list_data_3_" + String.valueOf(project_counting);
        String strContact = sp_3.getString(name, null);
        data_3 = gson.fromJson(strContact, listType);
        if(data_3 == null) {
            data_3 = new ArrayList<>(1000);
        }
        return data_3;


    }
    private ArrayList<String> loadData_4() {
        sp_4 = getApplicationContext().getSharedPreferences("list_data_4", MODE_PRIVATE);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        String name = "list_data_4_" + String.valueOf(project_counting);
        String strContact = sp_4.getString(name, null);
        data_4 = gson.fromJson(strContact, listType);
        if(data_4 == null) {
            data_4 = new ArrayList<String>(1000);
        }
        return data_4;

    }
    private void save_boole() {
        sp_bool = getApplicationContext().getSharedPreferences("boole", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp_bool.edit();
        String name = "boole_" + String.valueOf(project_counting);
        editor.putBoolean(name, false);
        editor.apply();


    }
    private Boolean load_boole()  {
        sp_bool = getApplicationContext().getSharedPreferences("boole", MODE_PRIVATE);
        String name = "boole_" + String.valueOf(project_counting);
        Boolean boole = sp_bool.getBoolean(name, true);

        return boole;
    }
    private void save_count() {
        sp_counting = getApplicationContext().getSharedPreferences("count", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp_counting.edit();
        String name = "count_" + String.valueOf(project_counting);
        editor.putInt(name, count);
        editor.apply();

    }
    private Integer load_count() {
        sp_counting = getApplicationContext().getSharedPreferences("count", MODE_PRIVATE);
        String name = "count_" + String.valueOf(project_counting);
        Integer ans = sp_counting.getInt(name, 0);

        return ans;
    }




}



