package com.example.myapplication;

import static com.example.myapplication.HypothesisActivity.sp_variable_1;
import static com.example.myapplication.HypothesisActivity.sp_variable_2;
import static com.example.myapplication.HypothesisActivity.variable_count;
import static com.example.myapplication.SubActivity.factor_count;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager ;
import android.app.PendingIntent ;
import android.content.Context ;
import android.content.Intent ;
import android.view.View ;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class RandomDataActivity extends AppCompatActivity {

    public static ArrayList<Integer> RandomList_for_HT = new ArrayList<Integer>();
    public static int increment;
    public static int I;
    public static SharedPreferences project_count;
    public static String variable_first = " ";
    public static SharedPreferences sp_increment;
    public static String variable_second = " ";
    public static int previous_count = 0;
    public static int requestCodei;
    public static Intent notificationIntentintent;
    public static HashMap<Integer, String> factor_one = new HashMap<>(1000);
    public static HashMap<Integer, String> factor_two = new HashMap<>(1000);
    public static Boolean[] visited = new Boolean[1000];
    public static SharedPreferences bool_visited_sp;

    protected void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_randomquestion);
        EditText number_of_data = findViewById(R.id.data_number_2);
        ImageButton button_random = findViewById(R.id.randomButton);
        Integer name_count = variable_count - 1;
        String name_variable_1 = "variable_1_" + String.valueOf(name_count);
        String name_variable_2 = "variable_2_" + String.valueOf(name_count);
        String name_direction = "direction_" + String.valueOf(name_count);
        String name_type = "type_" + String.valueOf(name_count);
        sp_variable_1 = getApplicationContext().getSharedPreferences("variable_1", MODE_PRIVATE);
        sp_variable_2 = getApplicationContext().getSharedPreferences("variable_2", MODE_PRIVATE);
        String factor1 = sp_variable_1.getString(name_variable_1, null);
        String factor2 = sp_variable_2.getString(name_variable_2, null);
        String direction = sp_variable_1.getString(name_direction, null);
        String type = sp_variable_1.getString(name_type, null);
        variable_first = factor1;
        variable_second = factor2;
        button_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer n = Integer.parseInt(number_of_data.getText().toString());
                I = loadDateDate_count();
                I++;
                saveDateDate_count();
                Boolean bool = true;
                factor_one.put(factor_count, variable_first);
                factor_two.put(factor_count, variable_second);
                increment = 0;
                //Integer maximum_bound = 10 * n + 1;
                Random random = new Random();
                for (int i = 0; i < n; i++) {
                    Integer random_number = random.nextInt(n - 1) + 1;
                    RandomList_for_HT.add(random_number);
                }
                for(int i = 0; i < 10; i++) {
                    visited[i] = load_visited(i);
                }
                previous_count = load_increment();
                for(int i = 0; i < 10; i++) {
                    if (visited[i] == false) {
                        visited[i] = true;
                        save_visited(i);
                        while (increment < n) {
                            long seconds = Long.valueOf(1000L * 5 * RandomList_for_HT.get(increment));
                            long current_seconds = System.currentTimeMillis();
                            requestCodei = increment + previous_count + 1;
                            Log.d("requestCode", String.valueOf(requestCodei));
                            notificationIntentintent = new Intent(getApplicationContext(), AlarmReceiverforHT.class);
                            String name = "com.alarmreceiverforHT." + String.valueOf(i);
                            notificationIntentintent.putExtra("requestCode", requestCodei);
                            notificationIntentintent.putExtra("put", i);
                            notificationIntentintent.putExtra("variable1", factor1);
                            notificationIntentintent.putExtra("variable2", factor2);
                            notificationIntentintent.putExtra("variablecount", variable_count);
                            notificationIntentintent.putExtra("direction", direction);
                            notificationIntentintent.putExtra("totaldata", n);
                            notificationIntentintent.putExtra("type", type);
                            notificationIntentintent.setAction(name);

                            PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), requestCodei, notificationIntentintent, PendingIntent.FLAG_UPDATE_CURRENT);
                            //this pendingIntent will be called by the broadcast receiver
                            //getting calender instance
                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, current_seconds + seconds, broadcast);

                            increment++;



                        }
                        break;
                    }
                }
                if(increment == 0) {
                    Toast.makeText(RandomDataActivity.this, "No more projects can be made. Finish other projects fist", Toast.LENGTH_SHORT).show();
                }
                else if (increment > 0) {
                    save_increment();
                    Intent intent_3 = new Intent(getApplicationContext(), NotificationActivity.class);
                    startActivity(intent_3);

                    overridePendingTransition(R.anim.validation_translate, R.anim.main_translate_for_validation);
                }
            }
        });
    }
    private void saveDateDate_count() {
        project_count = getApplicationContext().getSharedPreferences("date_count", MODE_PRIVATE);
        SharedPreferences.Editor project_counter = project_count.edit();
        project_counter.putInt("count", I);
        project_counter.apply();

    }
    private Integer loadDateDate_count() {
        project_count = getApplicationContext().getSharedPreferences("date_count", MODE_PRIVATE);
        Integer count = project_count.getInt("count", 0);

        return count;

    }
    private void save_increment () {
        sp_increment = getApplicationContext().getSharedPreferences("increment", MODE_PRIVATE);
        SharedPreferences.Editor problem_editor = sp_increment.edit();
        problem_editor.putInt("increment_number", increment + previous_count + 1);
        problem_editor.apply();
    }
    private Integer load_increment () {
        sp_increment = getApplicationContext().getSharedPreferences("increment", MODE_PRIVATE);
        Integer constant = sp_increment.getInt("increment_number", 0);

        return constant;
    }
    public void save_visited (int index) {
        bool_visited_sp = getApplicationContext().getSharedPreferences("bool_visited", MODE_PRIVATE);
        SharedPreferences.Editor problem_editor = bool_visited_sp.edit();
        String name = "bool_visited" + String.valueOf(index);
        problem_editor.putBoolean(name, visited[index]);
        problem_editor.apply();
    }
    public Boolean load_visited (int index) {
        bool_visited_sp = getApplicationContext().getSharedPreferences("bool_visited", MODE_PRIVATE);
        String name = "bool_visited" + String.valueOf(index);
        Boolean boolea = bool_visited_sp.getBoolean(name, false);

        return boolea;
    }


}
