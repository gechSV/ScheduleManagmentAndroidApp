package ScheduleManagement.AndroidApp.activity_controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import ScheduleManagement.AndroidApp.R;
import ScheduleManagement.AndroidApp.httpAppClient;

public class ActivityShareSchedule extends AppCompatActivity implements View.OnClickListener {

    CardView _CV_ActionCon, _buttonBack, _share;

    EditText _ET_schedule_name, _ET_schedule_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_schedule);

        _CV_ActionCon = (CardView)findViewById(R.id.action_con);
        _CV_ActionCon.setBackgroundResource(R.drawable.menu_background);

        _buttonBack = (CardView)findViewById(R.id.backButton);
        _buttonBack.setBackgroundResource(R.drawable.style_for_button_setting);
        _buttonBack.setOnClickListener(this);

        _share = (CardView)findViewById(R.id.share);
        _share.setBackgroundResource(R.drawable.style_for_button_setting);
        _share.setOnClickListener(this);

        _ET_schedule_name = (EditText) findViewById(R.id.schedule_name);
        _ET_schedule_password = (EditText) findViewById(R.id.schedule_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.backButton):
                this.finish();
                break;
            case(R.id.share):
                shareOrUpdateScheduleOnClick();
                break;
        }
    }

    private void shareOrUpdateScheduleOnClick(){
        String name = _ET_schedule_name.getText().toString();
        String password = _ET_schedule_password.getText().toString();

        ColorStateList colorStateListRed = ColorStateList.valueOf(0xFFFF9494);

        if(name.length() == 0){
            _ET_schedule_name.setBackgroundTintList(colorStateListRed);
            return;
        }
        if(password.length() == 0){
            _ET_schedule_password.setBackgroundTintList(colorStateListRed);
            return;
        }

        try {
            shareOrUpdateSchedule(name, password);
        }
        catch (Error error){
            Toast.makeText(ActivityShareSchedule.this, R.string.server_connect_err, Toast.LENGTH_SHORT).show();
        }
    }

    private void shareOrUpdateSchedule(String name, String password){
        try {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        httpAppClient _httpAppClient = new httpAppClient(ActivityShareSchedule.this);
                        _httpAppClient.addSchedule(name, password, ActivityShareSchedule.this);
                    }
                    catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            t1.start();
            t1.join();
            Toast.makeText(this, R.string.ok_add_apdate, Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}