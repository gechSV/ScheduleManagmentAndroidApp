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

        this.getOrganizationName();

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

        }
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

    private void getOrganizationName(){
        try {
            // получаем массив наименований организаций
            organization = _httpAppClient.GetOrganizationNameByTypeName();
            Log.d("MesLog", organization[0].getName());
        }
        catch(RuntimeException err){
            _LL_ConnectErrorBox.setVisibility(View.VISIBLE);
            return;
        }
    }

}

