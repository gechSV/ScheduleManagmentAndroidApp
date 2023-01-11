package ScheduleManagement.AndroidApp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;


public class ActivityAddScheduleItem extends AppCompatActivity implements View.OnClickListener{

    // _ET_EventName - текстовое поле для ввода названия события
    private EditText _ET_EventName;
    private EditText _ET_TypeOfEvent;
    private EditText _ET_EventLocation;
    private EditText _ET_NameOfTheEventHost;

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

    // Кнопки открытия TimePicker
    private Button _buttonStartTime;
    private Button _buttonEndTime;

    // Кнопка сохранения события
    private Button _buttonSaveEvent;

    private LinearLayout _LL_TypeOfEvent;

    // запоминает выбранный цвет
    private int _saveColor;

    // запоминает выбранное время
    private Calendar _startTime;
    private Calendar _endTime;

    private EventSchedule _eventSchedule;

    private final static String FILE_NAME = "NewEvent.bin";

    EditText test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_item);

        _ET_EventName = (EditText)findViewById(R.id.editTextEventName);
        _ET_TypeOfEvent = (EditText)findViewById(R.id.editTextTypeOfEvent);
        _ET_EventLocation = (EditText)findViewById(R.id.editTextEventLocation);
        _ET_NameOfTheEventHost = (EditText)findViewById(R.id.editTextNameOfTheEventHost);

        _LL_TypeOfEvent = (LinearLayout)findViewById(R.id.linearLayoutTypeOfEvent);

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

        _buttonStartTime = (Button) findViewById(R.id.buttonStartTime);
        _buttonEndTime = (Button) findViewById(R.id.buttonEndTime);

        _buttonSaveEvent = (Button) findViewById(R.id.buttonSaveEvent);

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

        _buttonStartTime.setOnClickListener(this);
        _buttonEndTime.setOnClickListener(this);

        _buttonSaveEvent.setOnClickListener(this);

        // Рабочий пример кнопок
//        for(int i = 0; i < 2; i++){
//            LinearLayout row2 = (LinearLayout) findViewById(R.id.linearLayoutTypeOfEvent);
//            Button ivBowl = new Button(this);
//            ivBowl.setText("hi");
//            LinearLayout.LayoutParams layoutParams = new  LinearLayout.LayoutParams(getPixelValue(this, 70), getPixelValue(this, 70));
//            layoutParams.setMargins(5, 3, 0, 0); // left, top, right, bottom
//            ivBowl.setBackground(ContextCompat.getDrawable(this, R.drawable.style_for_choice_button_black));
//            ivBowl.setLayoutParams(layoutParams);
//            row2.addView(ivBowl);
//        }

        test = (EditText) findViewById(R.id.editTextTextMultiLine);

        _startTime = Calendar.getInstance();
        _endTime = Calendar.getInstance();

        _eventSchedule = new EventSchedule();
    }

    public static int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
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
            case (R.id.buttonStartTime):
                SetTime(_buttonStartTime, _startTime);
                break;
            case (R.id.buttonEndTime):
                SetTime(_buttonEndTime, _endTime);
                break;
            case (R.id.buttonSaveEvent):
                SaveEvent();

                break;
        }
    }

    private void SetSaveColor(int color){
        _saveColor = color;
        _ET_EventName.setText(Integer.toString(color));
    }

    private void SetTime(Button button, Calendar time){

        final Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    currentTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    currentTime.set(Calendar.MINUTE, minute);
                    button.setText(simpleDateFormat.format(currentTime.getTime()));
                    time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    time.set(Calendar.MINUTE, minute);
                }
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityAddScheduleItem.this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour,
                minute, true);

        timePickerDialog.setTitle("Choose time:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    private void SaveEvent(){
        ColorStateList colorStateListRed = ColorStateList.valueOf(0xFFFF9494);

        Boolean checkErrorFlag = false;

        // Event name
        if (_ET_EventName.getText().toString().length() != 0) {
            _eventSchedule.SetNameEvent(_ET_EventName.getText().toString());
        }
        else{
            _ET_EventName.setBackgroundTintList(colorStateListRed);
            Toasty.error(this, R.string.WriteNameError, Toast.LENGTH_SHORT, true).show();
            return;
        }

        // Type Event
        if (_ET_TypeOfEvent.getText().toString().length() != 0) {
            _eventSchedule.SetTypeEvent(_ET_EventName.getText().toString());
        }

        // Event Location
        if (_ET_EventLocation.getText().toString().length() != 0) {
            _eventSchedule.SetEventLocation(_ET_EventName.getText().toString());
        }

        // Event Host
        if (_ET_NameOfTheEventHost.getText().toString().length() != 0) {
            _eventSchedule.SetEventHost(_ET_EventName.getText().toString());
        }

        // Time start and End
        _eventSchedule.SetTimeEventStart(_startTime);
        _eventSchedule.SetTimeEventEnd(_endTime);

        // Event for Color
        // Color saveColor = new Color.valueOf(0xFFFFFFFF);
        // _eventSchedule.SetColorForEvent();
//
//        Gson gson = new Gson();
//        String json = gson.toJson(_eventSchedule);

        // Сохранение данных в файл
        // FILE_NAME = "NewEvent.bin"
        try {
            FileIO.WriteScheduleEventInFile(_eventSchedule, FILE_NAME, ActivityAddScheduleItem.this);
            Toasty.success(this, R.string.SaveGood, Toast.LENGTH_SHORT, true).show();
        }
        catch (Error err){
            Toasty.error(this, err.getMessage(), Toast.LENGTH_SHORT, true).show();
        }


        // Пример чтения данных из файла
//        try{
//            EventSchedule testEvent = FileIO.ReadScheduleEventInFile(FILE_NAME, ActivityAddScheduleItem.this);
//            Gson gson = new Gson();
//            String json = gson.toJson(testEvent);
//            test.setText(json);
//            Toasty.success(this, "ReadGood", Toast.LENGTH_SHORT, true).show();
//        }
//        catch (Error err){
//            Toasty.error(this, err.getMessage(), Toast.LENGTH_SHORT, true).show();
//        }

        //TODO: Записывать все данные в список. При чтении распределять по нужным местам календаря
        //TODO: Соответственно создать класс списка

    }
}