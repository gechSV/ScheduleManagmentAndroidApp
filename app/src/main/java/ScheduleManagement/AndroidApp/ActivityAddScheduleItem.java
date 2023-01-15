package ScheduleManagement.AndroidApp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

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

    // Кнопки для выбора дня недели
    private Button _buttonChoiceWeekDayMon;
    private Button _buttonChoiceWeekDayTue;
    private Button _buttonChoiceWeekDayWed;
    private Button _buttonChoiceWeekDayThu;
    private Button _buttonChoiceWeekDayFri;
    private Button _buttonChoiceWeekDaySat;
    private Button _buttonChoiceWeekDaySun;

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

    boolean[] _weekClick;

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

        _buttonChoiceWeekDayMon = (Button) findViewById(R.id.buttonMon);
        _buttonChoiceWeekDayTue = (Button) findViewById(R.id.buttonTue);
        _buttonChoiceWeekDayWed = (Button) findViewById(R.id.buttonWed);
        _buttonChoiceWeekDayThu = (Button) findViewById(R.id.buttonThu);
        _buttonChoiceWeekDayFri = (Button) findViewById(R.id.buttonFri);
        _buttonChoiceWeekDaySat = (Button) findViewById(R.id.buttonSat);
        _buttonChoiceWeekDaySun = (Button) findViewById(R.id.buttonSun);

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

        _buttonChoiceWeekDayMon.setOnClickListener(this);
        _buttonChoiceWeekDayTue.setOnClickListener(this);
        _buttonChoiceWeekDayWed.setOnClickListener(this);
        _buttonChoiceWeekDayThu.setOnClickListener(this);
        _buttonChoiceWeekDayFri.setOnClickListener(this);
        _buttonChoiceWeekDaySat.setOnClickListener(this);
        _buttonChoiceWeekDaySun.setOnClickListener(this);

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

        _weekClick = new boolean[7];

        Arrays.fill(_weekClick, false);

        _eventSchedule = new EventSchedule();
    }

//    public static int getPixelValue(Context context, int dimenId) {
//        Resources resources = context.getResources();
//        return (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                dimenId,
//                resources.getDisplayMetrics()
//        );
//    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            // Прослушивание кнопок выбора цвета
            case (R.id.buttonChoiceColorLime):
                _saveColor = 0xFF8DC643;
                UpdColorImage(0);
                break;

            case (R.id.buttonChoiceColorCactus):
                _saveColor = 0xFF009144;
                UpdColorImage(1);
                break;

            case (R.id.buttonChoiceColorBlue):
                _saveColor = 0xFF00b2d6;
                UpdColorImage(2);
                break;

            case (R.id.buttonChoiceColorPurple):
                _saveColor = 0xFF683093;
                UpdColorImage(3);
                break;

            case (R.id.buttonChoiceColorRose):
                _saveColor = 0xFFd61e5e;
                UpdColorImage(4);
                break;

            case (R.id.buttonChoiceColorRed):
                _saveColor = 0xFFed2528;
                UpdColorImage(5);
                break;

            case (R.id.buttonChoiceColorPeach):
                _saveColor = 0xFFe63b43;
                UpdColorImage(6);
                break;

            case (R.id.buttonChoiceColorGray):
                _saveColor = 0xFF999;
                UpdColorImage(7);
                break;

            case (R.id.buttonChoiceColorBlack):
                _saveColor = 0xFF191919;
                UpdColorImage(8);
                break;

            case (R.id.buttonChoiceColorBrown):
                _saveColor = 0xFF603a16;
                UpdColorImage(9);
                break;

            case (R.id.buttonStartTime):
                SetTime(_buttonStartTime, _startTime);
                break;

            case (R.id.buttonEndTime):
                SetTime(_buttonEndTime, _endTime);
                break;

            case (R.id.buttonMon):
                if (_weekClick[0]){
                    _buttonChoiceWeekDayMon.setBackgroundResource(R.drawable.style_for_week_day_button);
                }
                else
                {
                    _buttonChoiceWeekDayMon.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                }
                _weekClick[0] = !_weekClick[0];
                break;

            case (R.id.buttonTue):
                if (_weekClick[1]){
                    _buttonChoiceWeekDayTue.setBackgroundResource(R.drawable.style_for_week_day_button);
                }
                else
                {
                    _buttonChoiceWeekDayTue.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                }
                _weekClick[1] = !_weekClick[1];
                break;

            case (R.id.buttonWed):
                if (_weekClick[2]){
                    _buttonChoiceWeekDayWed.setBackgroundResource(R.drawable.style_for_week_day_button);
                }
                else
                {
                    _buttonChoiceWeekDayWed.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                }
                _weekClick[2] = !_weekClick[2];
                break;

            case (R.id.buttonThu):
                if (_weekClick[3]){
                    _buttonChoiceWeekDayThu.setBackgroundResource(R.drawable.style_for_week_day_button);
                }
                else
                {
                    _buttonChoiceWeekDayThu.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                }
                _weekClick[3] = !_weekClick[3];
                break;

            case (R.id.buttonFri):
                if (_weekClick[4]){
                    _buttonChoiceWeekDayFri.setBackgroundResource(R.drawable.style_for_week_day_button);
                }
                else
                {
                    _buttonChoiceWeekDayFri.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                }
                _weekClick[4] = !_weekClick[4];
                break;

            case (R.id.buttonSat):
                if (_weekClick[5]){
                    _buttonChoiceWeekDaySat.setBackgroundResource(R.drawable.style_for_week_day_button);
                }
                else
                {
                    _buttonChoiceWeekDaySat.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                }
                _weekClick[5] = !_weekClick[5];
                break;

            case (R.id.buttonSun):
                if (_weekClick[6]){
                    _buttonChoiceWeekDaySun.setBackgroundResource(R.drawable.style_for_week_day_button);
                }
                else
                {
                    _buttonChoiceWeekDaySun.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                }
                _weekClick[6] = !_weekClick[6];
                break;

            case (R.id.buttonSaveEvent):
                SaveEvent();

                break;
        }
    }

    private void SetSaveColor(int color){
        _saveColor = color;
    }

    private void UpdColorImage(int colorButtonId){
        _buttonChoiceColorLime.setBackgroundResource(R.drawable.style_for_choice_button_lime);
        _buttonChoiceColorCactus.setBackgroundResource(R.drawable.style_for_choice_button_cactus);
        _buttonChoiceColorBlue.setBackgroundResource(R.drawable.style_for_choice_button_blue);
        _buttonChoiceColorPurple.setBackgroundResource(R.drawable.style_for_choice_button_purple);
        _buttonChoiceColorRose.setBackgroundResource(R.drawable.style_for_choice_button_rose);
        _buttonChoiceColorRed.setBackgroundResource(R.drawable.style_for_choice_button_red);
        _buttonChoiceColorPeach.setBackgroundResource(R.drawable.style_for_choice_button_peach);
        _buttonChoiceColorGray.setBackgroundResource(R.drawable.style_for_choice_button_gray);
        _buttonChoiceColorBlack.setBackgroundResource(R.drawable.style_for_choice_button_black);
        _buttonChoiceColorBrown.setBackgroundResource(R.drawable.style_for_choice_button_brown);

        switch (colorButtonId){
            case 0:
                _buttonChoiceColorLime.setBackgroundResource(R.drawable.style_for_choice_button_lime_click);
                break;
            case 1:
                _buttonChoiceColorCactus.setBackgroundResource(R.drawable.style_for_choice_button_cactus_click);
                break;
            case 2:
                _buttonChoiceColorBlue.setBackgroundResource(R.drawable.style_for_choice_button_blue_click);
                break;
            case 3:
                _buttonChoiceColorPurple.setBackgroundResource(R.drawable.style_for_choice_button_purple_click);
                break;
            case 4:
                _buttonChoiceColorRose.setBackgroundResource(R.drawable.style_for_choice_button_rose_click);
                break;
            case 5:
                _buttonChoiceColorRed.setBackgroundResource(R.drawable.style_for_choice_button_red_click);
                break;
            case 6:
                _buttonChoiceColorPeach.setBackgroundResource(R.drawable.style_for_choice_button_peach_click);
                break;
            case 7:
                _buttonChoiceColorGray.setBackgroundResource(R.drawable.style_for_choice_button_gray_click);
                break;
            case 8:
                _buttonChoiceColorBlack.setBackgroundResource(R.drawable.style_for_choice_button_black_click);
                break;
            case 9:
                _buttonChoiceColorBrown.setBackgroundResource(R.drawable.style_for_choice_button_brown_click);
                break;
        }
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

         // Event add Color
         _eventSchedule.SetColorForEvent(_saveColor);

         // Event add WeekDay
        _eventSchedule.SetWeekDayPeek(_weekClick);

        // Сохранение данных в файл
        // FILE_NAME = "NewEvent.bin"
        try {
            FileIO.WriteScheduleEventInFile(_eventSchedule, FILE_NAME, ActivityAddScheduleItem.this);
            Toasty.success(this, R.string.SaveGood, Toast.LENGTH_SHORT, true).show();
        }
        catch (Error err){
            Toasty.error(this, Objects.requireNonNull(err.getMessage()), Toast.LENGTH_SHORT, true).show();
        }

        // Пример чтения данных из файла
        try{
            EventSchedule testEvent = FileIO.ReadScheduleEventInFile(FILE_NAME, ActivityAddScheduleItem.this);
            Gson gson = new Gson();
            String json = gson.toJson(testEvent);
            test.setText(json);
            Toasty.success(this, "ReadGood", Toast.LENGTH_SHORT, true).show();
        }
        catch (Error err){
            Toasty.error(this, Objects.requireNonNull(err.getMessage()), Toast.LENGTH_SHORT, true).show();
        }


//        String hexColor = String.format("#%06X", (0xFFFFFF & _saveColor));
//        test.setText(hexColor);

        //TODO: Записывать все данные в список. При чтении распределять по нужным местам календаря
        //TODO: Соответственно создать класс списка

    }
}