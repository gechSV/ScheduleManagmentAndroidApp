package ScheduleManagement.AndroidApp.activity_controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import ScheduleManagement.AndroidApp.R;
import ScheduleManagement.AndroidApp.httpAppClient;

public class ActivitySetting extends AppCompatActivity implements View.OnClickListener{

    private static ActivitySetting instance;
    private Intent _IntentChoosingSchedule;
    private Intent _IntentTimeIntervals;
    private CardView _CV_ActionCon;
    private CardView _buttonBack;
    private Button TestButton;
    private Button _BT_openChoosingSchedule;
    private Button _BT_openTimeIntervals;

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

        _CV_ActionCon = (CardView)findViewById(R.id.action_con);
        _CV_ActionCon.setBackgroundResource(R.drawable.menu_white_background);

        _buttonBack = (CardView)findViewById(R.id.backButton);
        _buttonBack.setBackgroundResource(R.drawable.style_for_button_setting);
        _buttonBack.setOnClickListener(this);

        _BT_openChoosingSchedule = findViewById(R.id.open_choosing_schedule);
        _BT_openChoosingSchedule.setOnClickListener(this);

        _BT_openTimeIntervals = findViewById(R.id.open_time_interval);
        _BT_openTimeIntervals.setOnClickListener(this);

        _PB_progress = (ProgressBar)findViewById(R.id.progressBar);
        _PB_progress.setVisibility(ProgressBar.INVISIBLE);

        _httpAppClient = new httpAppClient();
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
            case (R.id.open_time_interval):
                startActivity(_IntentTimeIntervals);
                break;
        }
    }
}