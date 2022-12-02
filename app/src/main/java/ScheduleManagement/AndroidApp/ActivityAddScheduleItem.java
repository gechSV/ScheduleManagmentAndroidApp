package ScheduleManagement.AndroidApp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class ActivityAddScheduleItem extends AppCompatActivity implements View.OnClickListener{
    // _ET_EventName - текстовое поле для ввода названия события
    private EditText _ET_EventName;

    // Кнопки для выбора цвета
    private Button _buttonChoiceColorLime;
    private Button _buttonChoiceColorCactus;
    private Button _buttonChoiceColorBlue;
    private Button _buttonChoiceColorPurple;
    private Button _buttonChoiceColorRose;
    private Button _buttonChoiceColorRed;
    private Button _buttonChoiceColorPeach;
    private Button _buttonChoiceColorGray;
    private Button _buttonChoiceColorBlack;
    private Button _buttonChoiceColorBrown;


    int _saveColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_item);

        _ET_EventName = (EditText)findViewById(R.id.editTextEventName);

        Spinner spinner =  (Spinner) findViewById(R.id.spinnerChoiceWeeks);

        List<String> list = new ArrayList<String>();
        list.add("12121");
        list.add("32423432");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_list,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        _buttonChoiceColorLime = (Button) findViewById(R.id.buttonChoiceColorLime);
        _buttonChoiceColorCactus = (Button) findViewById(R.id.buttonChoiceColorCactus);
        _buttonChoiceColorBlue = (Button) findViewById(R.id.buttonChoiceColorBlue);
        _buttonChoiceColorPurple = (Button) findViewById(R.id.buttonChoiceColorPurple);
        _buttonChoiceColorRose = (Button) findViewById(R.id.buttonChoiceColorRose);
        _buttonChoiceColorRed = (Button) findViewById(R.id.buttonChoiceColorRed);
        _buttonChoiceColorPeach = (Button) findViewById(R.id.buttonChoiceColorPeach);
        _buttonChoiceColorGray = (Button) findViewById(R.id.buttonChoiceColorGray);
        _buttonChoiceColorBlack = (Button) findViewById(R.id.buttonChoiceColorBlack);
        _buttonChoiceColorBrown = (Button) findViewById(R.id.buttonChoiceColorBrown);

        // Добавляем кнопки к прослушиванию для метода onClick()
        _buttonChoiceColorLime.setOnClickListener(this);
        _buttonChoiceColorCactus.setOnClickListener(this);
        _buttonChoiceColorBlue.setOnClickListener(this);
        _buttonChoiceColorPurple.setOnClickListener(this);
        _buttonChoiceColorRose.setOnClickListener(this);
        _buttonChoiceColorRed.setOnClickListener(this);
        _buttonChoiceColorPeach.setOnClickListener(this);
        _buttonChoiceColorGray.setOnClickListener(this);
        _buttonChoiceColorBlack.setOnClickListener(this);
        _buttonChoiceColorBrown.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            // Прослушивание кнопок выбора цвета
            case (R.id.buttonChoiceColorLime):
                SetSaveColor(0xFF8DC643);
                break;
            case (R.id.buttonChoiceColorCactus):
                SetSaveColor(0xFF009144);
                break;
            case (R.id.buttonChoiceColorBlue):
                SetSaveColor(0xFF00b2d6);
                break;
            case (R.id.buttonChoiceColorPurple):
                SetSaveColor(0xFF683093);
                break;
            case (R.id.buttonChoiceColorRose):
                SetSaveColor(0xFFd61e5e);
                break;
            case (R.id.buttonChoiceColorRed):
                SetSaveColor(0xFFed2528);
                break;
            case (R.id.buttonChoiceColorPeach):
                SetSaveColor(0xFFe63b43);
                break;
            case (R.id.buttonChoiceColorGray):
                SetSaveColor(0xFF999);
                break;
            case (R.id.buttonChoiceColorBlack):
                SetSaveColor(0xFF191919);
                break;
            case (R.id.buttonChoiceColorBrown):
                SetSaveColor(0xFF603a16);
                break;
        }
    }

    private void SetSaveColor(int color){
        _saveColor = color;
        _ET_EventName.setText(Integer.toString(color));
    }
}