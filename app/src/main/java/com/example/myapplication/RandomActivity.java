package com.example.myapplication;

import static com.example.myapplication.SubActivity.factor_count;
import static com.example.myapplication.SubActivity.sp_factor;
import static com.example.myapplication.SubActivity.sp_factor_2;

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

public class RandomActivity extends AppCompatActivity {

    public static ArrayList<Integer> RandomList = new ArrayList<>();
    public static int problem;
    public static int l;
    public static SharedPreferences project_count;
    public static String factor_first = " ";
    public static SharedPreferences sp_problem;
    public static String factor_second = " ";
    public static int previous = 0;
    public static int requestCode;
    public static Intent notificationIntent;
    public static HashMap<Integer, String> factor_one = new HashMap<>(1000);
    public static HashMap<Integer, String> factor_two = new HashMap<>(1000);
    public static Boolean[] visited = new Boolean[1000];
    public static SharedPreferences bool_action_sp;

    protected void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_question);
        EditText number_of_data = findViewById(R.id.data_number);
        ImageButton button_random = findViewById(R.id.random);
        Integer name_count = factor_count - 1;
        //Java automatically convert int to string
        String name_factor_1 = "factor_1_" + name_count;
        String name_factor_2 = "factor_2_" + name_count;
        sp_factor = getApplicationContext().getSharedPreferences("factor_1", MODE_PRIVATE);
        sp_factor_2 = getApplicationContext().getSharedPreferences("factor_2", MODE_PRIVATE);
        String factor1 = sp_factor.getString(name_factor_1, null);
        String factor2 = sp_factor_2.getString(name_factor_2, null);
        factor_first = factor1;
        factor_second = factor2;
        button_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer n = Integer.parseInt(number_of_data.getText().toString());
                l = loadDate_count();
                l++;
                saveDate_count();
                factor_one.put(factor_count, factor_first);
                factor_two.put(factor_count, factor_second);
                problem = 0;
                //Integer maximum_bound = 10 * n + 1;
                Random random = new Random();
                for (int i = 0; i < n; i++) {
                    Integer random_number = random.nextInt(n - 1) + 1;
                    RandomList.add(random_number);
                }
                for(int i = 0; i < 10; i++) {
                    visited[i] = load_boole(i);
                }
                previous = load_problem();
                for(int i = 0; i < 10; i++) {
                    if (!visited[i]) {
                        visited[i] = true;
                        save_boole(i);
                         while (problem < n) {
                            long seconds = 1000L * 10 * RandomList.get(problem);
                            long current_seconds = System.currentTimeMillis();
                            requestCode = problem + previous + 1;
                            Log.d("requestCode", String.valueOf(requestCode));
                            notificationIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                            String name = "com.alarmreceiver." + i;
                            notificationIntent.putExtra("requestCode", requestCode);
                            notificationIntent.putExtra("put", i);
                            notificationIntent.putExtra("factor1", factor1);
                            notificationIntent.putExtra("factor2", factor2);
                            notificationIntent.putExtra("factorcount", factor_count);
                            notificationIntent.putExtra("datacount", n);
                            notificationIntent.setAction(name);

                            PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), requestCode, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
                            //this pendingIntent will be called by the broadcast receiver
                            //getting calender instance
                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, current_seconds + seconds, broadcast);

                            problem++;



                        }
                        break;
                    }
                }
                if(problem == 0) {
                    Toast.makeText(RandomActivity.this, "No more projects can be made. Finish other projects fist", Toast.LENGTH_SHORT).show();
                }
                else if (problem > 0) {
                    save_problem();
                    Intent intent_3 = new Intent(getApplicationContext(), NotificationActivity_1.class);
                    startActivity(intent_3);

                    overridePendingTransition(R.anim.prediction_translate, R.anim.main_translate);
                }
            }
        });
    }
    private void saveDate_count() {
        project_count = getApplicationContext().getSharedPreferences("number", MODE_PRIVATE);
        SharedPreferences.Editor project_counter = project_count.edit();
        project_counter.putInt("count", l);
        project_counter.apply();

    }
    private Integer loadDate_count() {
        project_count = getApplicationContext().getSharedPreferences("number", MODE_PRIVATE);
        return project_count.getInt("count", 0);
    }
    private void save_problem () {
        sp_problem = getApplicationContext().getSharedPreferences("problem", MODE_PRIVATE);
        SharedPreferences.Editor problem_editor = sp_problem.edit();
        problem_editor.putInt("problem_number", problem + previous + 1);
        problem_editor.apply();
    }
    private Integer load_problem () {
        sp_problem = getApplicationContext().getSharedPreferences("problem", MODE_PRIVATE);
        return sp_problem.getInt("problem_number", 0);
    }
    public void save_boole (int index) {
        bool_action_sp = getApplicationContext().getSharedPreferences("bool_action", MODE_PRIVATE);
        SharedPreferences.Editor problem_editor = bool_action_sp.edit();
        String name = "bool_action" + index;
        problem_editor.putBoolean(name, visited[index]);
        problem_editor.apply();
    }
    public Boolean load_boole (int index) {
        bool_action_sp = getApplicationContext().getSharedPreferences("bool_action", MODE_PRIVATE);
        String name = "bool_action" + index;
        return bool_action_sp.getBoolean(name, false);
    }


}
