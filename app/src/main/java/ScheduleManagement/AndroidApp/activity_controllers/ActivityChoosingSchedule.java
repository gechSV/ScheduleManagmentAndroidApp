package ScheduleManagement.AndroidApp.activity_controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import ScheduleManagement.AndroidApp.EventSchedule;
import ScheduleManagement.AndroidApp.EventScheduleList;
import ScheduleManagement.AndroidApp.FileIO;
import ScheduleManagement.AndroidApp.middleware_class.Groups;
import ScheduleManagement.AndroidApp.middleware_class.Organization;
import ScheduleManagement.AndroidApp.R;
import ScheduleManagement.AndroidApp.httpAppClient;
import ScheduleManagement.AndroidApp.middleware_class.Schedule;

public class ActivityChoosingSchedule extends AppCompatActivity implements View.OnClickListener{
    private httpAppClient _httpAppClient;
    private CardView _CV_ActionCon;
    private CardView _buttonBack;
    private LinearLayout _LL_ConnectErrorBox;
    private LinearLayout _LL_ButtonBox;
    private ProgressBar _PB_progress;

    private Organization[] organization;
    private Groups[] groups;

    private Schedule[] _Schedule;
    int idButton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_schedule);

        _CV_ActionCon = (CardView)findViewById(R.id.action_con);
        _CV_ActionCon.setBackgroundResource(R.drawable.menu_white_background);

        _buttonBack = (CardView)findViewById(R.id.backButton);
        _buttonBack.setBackgroundResource(R.drawable.style_for_button_setting);
        _buttonBack.setOnClickListener(this);

        _LL_ConnectErrorBox = (LinearLayout)findViewById(R.id.connectErrorBox);
        _LL_ButtonBox = (LinearLayout)findViewById(R.id.button_list);
        _PB_progress = (ProgressBar)findViewById(R.id.progressBar);
        _PB_progress.setVisibility(ProgressBar.INVISIBLE);

        _httpAppClient = new httpAppClient();

        ActivitySetting.getInstance().HideProgressBar();

        if((!this.getOrganizationName()) || organization == null) {
            _LL_ConnectErrorBox.setVisibility(View.VISIBLE);
            return;
        }

        this.buttonOrganizationBuild(organization);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.backButton):
                this.finish();
                break;
        }
    }

    private boolean getOrganizationName(){
        try {
            // Запускаем дочерний поток для запроса к серверу
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // получаем массив наименований организаций
                        organization = _httpAppClient.GetOrganizationNameByTypeName();
                    }
                    catch (Exception err){
                        Log.d("MesLog", err.getMessage());
                    }
                }
            });
            t1.start();
            t1.join();
            return true;
        }
        catch(RuntimeException | InterruptedException err){
            return false;
        }
    }

    private boolean GetGroupName(String orgName){
        try {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        groups = _httpAppClient.GetGroupByNameOrganizations(orgName);
                    }
                    catch (Exception err){
                        Log.d("MesLog", err.getMessage());
                    }
                }
            });
            t1.start();
            t1.join();
            Log.d("MesLog", groups[0].getName());
            return true;
        }
        catch (RuntimeException | InterruptedException err){
            _LL_ConnectErrorBox.setVisibility(View.VISIBLE);
            return false;
        }
    }

    private boolean GetSchedule(String groupName, String password){
        try {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                         _Schedule = _httpAppClient.getScheduleByName(groupName, password);
                    }
                    catch (Exception err){
                        Log.d("MesLog", err.getMessage());
                    }
                }
            });
            t1.start();
            t1.join();
//            Log.d("MesLog", _jsonSchedule);
            return true;
        }
        catch (RuntimeException err){
            _LL_ConnectErrorBox.setVisibility(View.VISIBLE);
            return false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void buttonOrganizationBuild(Organization[] org){
        for(int i = 0; i < org.length; i++){

            Button btn = (Button) LayoutInflater
                    .from(this)
                    .inflate(R.layout.button_pattern_for_setting, null);

            btn.setId(idButton++);
            final int id_ = btn.getId();
            btn.setText(org[i].getName());

            _LL_ButtonBox.addView(btn, i);
            Button btn1 = ((Button)findViewById(id_));

            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    _PB_progress.setVisibility(ProgressBar.VISIBLE);
                    if(!GetGroupName(btn1.getText().toString())){
                        _PB_progress.setVisibility(ProgressBar.INVISIBLE);
                        return;
                    }
                    _PB_progress.setVisibility(ProgressBar.INVISIBLE);
                    _LL_ButtonBox.removeAllViews();
                    buttonGroupBuild(groups);
                }
            });
        }
    }

    private void buttonGroupBuild(Groups[] gr){
        for (int i = 0; i < gr.length; i++){
            Button btn = (Button) LayoutInflater
                    .from(this)
                    .inflate(R.layout.button_pattern_for_setting, null);

            btn.setId(idButton++);
            final int id_ = btn.getId();
            btn.setText(gr[i].getName());

            _LL_ButtonBox.addView(btn, i);
            Button btn1 = ((Button)findViewById(id_));

            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    _LL_ConnectErrorBox.setVisibility(View.INVISIBLE);
                    if(!GetSchedule(btn1.getText().toString(), "zabgu") || _Schedule == null){
                        _LL_ButtonBox.setVisibility(View.INVISIBLE);
                        _LL_ConnectErrorBox.setVisibility(View.VISIBLE);
                        return;
                    }
                    EventScheduleList eventList = FileIO.ReadScheduleEventListInFile
                            ("Event_Schedule_List_1.bin", ActivityChoosingSchedule.this);

                    eventList.RemoveEventsByScheduleType(1);

                    for(Schedule schedule: _Schedule){
                        EventSchedule event = schedule.toEventSchedule();
                        event.setScheduleType(1);
                        eventList.AppendEvent(event);
                    }

                    FileIO.WriteScheduleEventListInFile(eventList.GetEventsDayList(),
                            "Event_Schedule_List_1.bin", ActivityChoosingSchedule.this);

                    MainActivity.getInstance().ReloadViewPager_1();
                    MainActivity.getInstance().ReloadViewPager_2();
                }
            });
        }
    }
}

