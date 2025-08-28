package com.example.myapplication;
import static com.example.myapplication.ThirdActivity.bool;
import static com.example.myapplication.ThirdActivity.sp_1;
import static com.example.myapplication.ThirdActivity.sp_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HypothesisActivity extends AppCompatActivity {
    public static int variable_count = 0;
    public static SharedPreferences sp_variable_count;
    public static SharedPreferences sp_variable_1;
    public static SharedPreferences sp_variable_2;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hypothesis);

        Spinner spinner = findViewById(R.id.spinner);
        Spinner spinner_direction = findViewById(R.id.spinner_2);
        Spinner spinner_1 = findViewById(R.id.spinner_3);
        EditText factor_1 = findViewById(R.id.factor_1);
        EditText factor_2 = findViewById(R.id.factor_2);
        ImageButton button_1 = findViewById(R.id.next);
        String[] types = {" ", "number", "weight", "amount", "height"};
        String[] directions = {" ", "is" + " " + "more" + " " + "than", "is" + " " + "less" + " " + "than"};


        ArrayAdapter ad = new ArrayAdapter (this, R.layout.spinner, types);
        ArrayAdapter ad_direction = new ArrayAdapter(this, R.layout.spinner_direction, directions);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad_direction.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(ad);
        spinner_1.setAdapter(ad);
        spinner_direction.setAdapter(ad_direction);

        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDirection = spinner_direction.getSelectedItem().toString();
                String selectedType = spinner.getSelectedItem().toString();
                variable_count = loadvariable_count();
                String name_1 = "variable_1_" + String.valueOf(variable_count);
                String name_2 = "variable_2_" + String.valueOf(variable_count);
                String name_3 = "direction_" + String.valueOf(variable_count);
                String name_4 = "type_" + String.valueOf(variable_count);
                sp_variable_1= getApplicationContext().getSharedPreferences("variable_1", MODE_PRIVATE);
                sp_variable_2 = getApplicationContext().getSharedPreferences("variable_2", MODE_PRIVATE);

                SharedPreferences.Editor editor_1 = sp_variable_1.edit();
                SharedPreferences.Editor editor_2 = sp_variable_2.edit();

                editor_1.putString(name_1, factor_1.getText().toString());
                editor_1.putString(name_3, selectedDirection);
                editor_1.putString(name_4, selectedType);
                editor_2.putString(name_2, factor_2.getText().toString());

                editor_1.apply();
                editor_2.apply();
                variable_count++;
                savevariablecount();
                Intent intent = new Intent(getApplicationContext(), RandomDataActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fade_innn, R.anim.fade_out);

            }
        });


    }
    public int loadvariable_count() {
        sp_variable_count = getApplicationContext().getSharedPreferences("variable_count", MODE_PRIVATE);
        Integer count_factor = sp_variable_count.getInt("variable_count", 0);

        return count_factor;
    }

    public void savevariablecount() {
        sp_variable_count = getApplicationContext().getSharedPreferences("variable_count", MODE_PRIVATE);
        SharedPreferences.Editor sp_factor_count_editor = sp_variable_count.edit();
        sp_factor_count_editor.putInt("variable_count", variable_count);
        sp_factor_count_editor.apply();

    }
}