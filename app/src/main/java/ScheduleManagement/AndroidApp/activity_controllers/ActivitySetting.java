package ScheduleManagement.AndroidApp.activity_controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import ScheduleManagement.AndroidApp.ActivityDownloadUsersSchedule;
import ScheduleManagement.AndroidApp.ActivityScheduleConstructor;
import ScheduleManagement.AndroidApp.FileIO;
import ScheduleManagement.AndroidApp.R;
import ScheduleManagement.AndroidApp.httpAppClient;

public class ActivitySetting extends AppCompatActivity implements View.OnClickListener{

    private static ActivitySetting instance;
    private Intent _IntentChoosingSchedule, _IntentTimeIntervals, _IntentChoosingTeacherSchedule,
                    _IntentApplicationSetting, _IntentShareScedule, _IntentDownloadUserSchedule,
                    _IntentScheduleConstructor;
    private CardView _CV_ActionCon;
    private CardView _buttonBack;
    private Button _BT_openChoosingSchedule, _BT_openChoosingScheduleTeachers, _BT_openTimeIntervals,
                    _BT_applicationSettings, _BT_shareASchedule, _BT_download_users_schedule, _BT_open_schedule_constructor;
    private ProgressBar _PB_progress;
    private httpAppClient _httpAppClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        instance = this;

        _IntentChoosingSchedule = new Intent(ActivitySetting.this, ActivityChoosingSchedule.class);
        _IntentChoosingSchedule.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        _IntentTimeIntervals = new Intent(ActivitySetting.this, ActivityTimeSetting.class);
        _IntentTimeIntervals.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        _IntentChoosingTeacherSchedule = new Intent(ActivitySetting.this, activity_choosing_teacher_schedule.class);
        _IntentChoosingTeacherSchedule.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        _IntentApplicationSetting = new Intent(ActivitySetting.this, ActivityApplicationSettings.class);
        _IntentApplicationSetting.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        _IntentShareScedule = new Intent(ActivitySetting.this, ActivityShareSchedule.class);
        _IntentShareScedule.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        _IntentDownloadUserSchedule = new Intent(ActivitySetting.this, ActivityDownloadUsersSchedule.class);
        _IntentDownloadUserSchedule.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        _IntentScheduleConstructor = new Intent(ActivitySetting.this, ActivityScheduleConstructor.class);
        _IntentScheduleConstructor.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        _CV_ActionCon = (CardView)findViewById(R.id.action_con);
        _CV_ActionCon.setBackgroundResource(R.drawable.menu_background);

        _buttonBack = (CardView)findViewById(R.id.backButton);
        _buttonBack.setBackgroundResource(R.drawable.style_for_button_setting);
        _buttonBack.setOnClickListener(this);

        _BT_openChoosingSchedule = findViewById(R.id.open_choosing_schedule);
        _BT_openChoosingSchedule.setOnClickListener(this);

        _BT_openChoosingScheduleTeachers = findViewById(R.id.open_choosing_schedule_teachers);
        _BT_openChoosingScheduleTeachers.setOnClickListener(this);

        _BT_openTimeIntervals = findViewById(R.id.open_time_interval);
        _BT_openTimeIntervals.setOnClickListener(this);

        _PB_progress = (ProgressBar)findViewById(R.id.progressBar);
        _PB_progress.setVisibility(ProgressBar.INVISIBLE);

        _BT_applicationSettings = (Button)findViewById(R.id.application_settings);
        _BT_applicationSettings.setOnClickListener(this);

        _BT_download_users_schedule = (Button)findViewById(R.id.download_users_schedule);
        _BT_download_users_schedule.setOnClickListener(this);

        _BT_open_schedule_constructor = (Button)findViewById(R.id.open_schedule_constructor);
        _BT_open_schedule_constructor.setOnClickListener(this);

        _BT_shareASchedule = (Button)findViewById(R.id.share_a_schedule);
        _BT_shareASchedule.setOnClickListener(this);

        _httpAppClient = new httpAppClient(this);

        String url = FileIO.getUrlAddress("urlAddress.bin", this);
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

    }

    public static ActivitySetting getInstance() {
        return instance;
    }

    public void HideProgressBar(){
        _PB_progress.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.backButton):
                this.finish();
                break;
            case (R.id.open_choosing_schedule):
                _PB_progress.setVisibility(ProgressBar.VISIBLE);
                startActivity(_IntentChoosingSchedule);
                break;
            case (R.id.open_choosing_schedule_teachers):
                _PB_progress.setVisibility(ProgressBar.VISIBLE);
                startActivity(_IntentChoosingTeacherSchedule);
                break;
            case (R.id.open_time_interval):
                startActivity(_IntentTimeIntervals);
                break;
            case(R.id.application_settings):
                startActivity(_IntentApplicationSetting);
                break;
            case(R.id.share_a_schedule):
                startActivity(_IntentShareScedule);
                break;
            case(R.id.download_users_schedule):
                startActivity(_IntentDownloadUserSchedule);
                break;
            case(R.id.open_schedule_constructor):
                startActivity(_IntentScheduleConstructor);
                break;
        }
    }
}