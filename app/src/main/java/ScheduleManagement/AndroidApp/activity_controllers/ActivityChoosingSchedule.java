package ScheduleManagement.AndroidApp.activity_controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import ScheduleManagement.AndroidApp.DpInPxDisplay;
import ScheduleManagement.AndroidApp.Groups;
import ScheduleManagement.AndroidApp.Organization;
import ScheduleManagement.AndroidApp.R;
import ScheduleManagement.AndroidApp.httpAppClient;

public class ActivityChoosingSchedule extends AppCompatActivity implements View.OnClickListener{
    private httpAppClient _httpAppClient;
    private CardView _CV_ActionCon;
    private CardView _buttonBack;
    private String _jsonOrgName;
    private LinearLayout _LL_ConnectErrorBox;
    private LinearLayout _LL_ButtonBox;

    private ProgressBar _PB_progress;

    private Organization[] organization;
    private Groups[] groups;
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

        if(!this.getOrganizationName()) return;

        this.buttonBuild();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Прослушивание кнопок выбора цвета
            case (R.id.backButton):
                this.finish();
                break;
        }
    }

    private boolean getOrganizationName(){
        try {
            // получаем массив наименований организаций

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    organization = _httpAppClient.GetOrganizationNameByTypeName();

                }
            });
            t1.start();

            t1.join();
            Log.d("MesLog", organization[0].getName());
            return true;
        }
        catch(RuntimeException err){
            _LL_ConnectErrorBox.setVisibility(View.VISIBLE);
            return false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void buttonBuild(){
        for(int i = 0; i < organization.length; i++){
//            View view = LayoutInflater
//                    .from(this)
//                    .inflate(R.layout.button_pattern_for_setting, null);

            Button btn = (Button) LayoutInflater
                    .from(this)
                    .inflate(R.layout.button_pattern_for_setting, null);

            btn.setId(idButton++);
            final int id_ = btn.getId();
            btn.setText(organization[i].getName());

            _LL_ButtonBox.addView(btn, i);
            Button btn1 = ((Button)findViewById(id_));

            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                   try {
                       Thread t1 = new Thread(new Runnable() {
                           @Override
                           public void run() {
                               groups = _httpAppClient.GetGroupByNameOrganizations(btn1.getText().toString());
                           }
                       });
                       t1.start();

                       t1.join();

                       Log.d("MesLog", groups[0].getName());
                   }
                   catch (RuntimeException err){
//                       Log.d("MesLog", err.getMessage().toString());
                       _LL_ConnectErrorBox.setVisibility(View.VISIBLE);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
                }
            });
        }
    }

}

