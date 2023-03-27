package ScheduleManagement.AndroidApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class activity_choosing_schedule extends AppCompatActivity implements View.OnClickListener{

    private httpAppClient _httpAppClient;
    private CardView _CV_ActionCon;
    private CardView _buttonBack;
    private String _jsonOrgName;
    private LinearLayout _LL_ConnectErrorBox;

    private Organization[] organization;

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

        _httpAppClient = new httpAppClient();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Прослушивание кнопок выбора цвета
            case (R.id.backButton):
                this.finish();
                break;
        }
    }

}