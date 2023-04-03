package ScheduleManagement.AndroidApp.activity_controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

import ScheduleManagement.AndroidApp.DpInPxDisplay;
import ScheduleManagement.AndroidApp.EventSchedule;
import ScheduleManagement.AndroidApp.EventScheduleList;
import ScheduleManagement.AndroidApp.FileIO;
import ScheduleManagement.AndroidApp.HintBuilder;
import ScheduleManagement.AndroidApp.R;
import es.dmoral.toasty.Toasty;


public class ActivityAddScheduleItem extends AppCompatActivity implements View.OnClickListener{

    private int currentNightMode;
    // _ET_EventName - текстовое поле для ввода названия события
    private EditText _ET_EventName;
    private EditText _ET_TypeOfEvent;
    private EditText _ET_EventLocation;
    private EditText _ET_NameOfTheEventHost;

    // Кнопки для выбора цвета
    private Button _buttonChoiceColorLime;
    private Button _buttonChoiceColorGreen;
    private Button _buttonChoiceColorBlue;
    private Button _buttonChoiceColorPurple;
    private Button _buttonChoiceColorPink;
    private Button _buttonChoiceColorRed;
    private Button _buttonChoiceColorOrange;
    private Button _buttonChoiceColorGray;
    private Button _buttonChoiceColorTeal;
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
    // Выбор верхней/нижней недели
    private Button _BT_upWeek, _BT_downWeek;

    // Кнопка сохранения события
    private CardView _buttonSaveEvent;
    private CardView _buttonBack;

    private LinearLayout _LL_Color_1, _LL_Color_2;
    private LinearLayout _LL_TypeOfEvent;
    private LinearLayout _LL_HintName, _LL_HintTypeCon, _LL_HintLocation, _LL_HintHost;

    private CardView _CV_ActionCon;

    // запоминает выбранный цвет
    private int _saveColor;

    // запоминает выбранное время
    private Calendar _startTime;
    private Calendar _endTime;

    boolean[] _weekClick;

    private EventSchedule _eventSchedule_1[];
    private EventSchedule _eventSchedule_2[];

    EventSchedule eventScheduleForEdit;

    EditText test;

    // Контейнеры для принятия данных от Bundle
    boolean editFlag = false;
    int eventId = -1;
    String eventName = null;
    int colorId = -1;
    String eventType = null;
    String eventLocation = null;
    String eventHost = null;
    int weekDayKey = -1;
    int HHStart = -1;
    int MMStart = -1;
    int HHEnd = -1;
    int MMEnd = -1;

    String FILE_NAME, FILE_NAME_FOR_HINT_1, FILE_NAME_FOR_HINT_2;

    EventScheduleList eventScheduleListForHint;
    ArrayList<String> hintNameList;
    ArrayList<String> hintTypeList;
    ArrayList<String> hintLocationList;
    ArrayList<String> hintHostList;

    private int HintButtonId = 0;

    TypedValue typedValue;
    int colorWeekButtonInactive, colorWeekButtonActive;

    boolean[] _weekTypeFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_item);

        currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

        _ET_EventName = (EditText)findViewById(R.id.editTextEventName);
        _ET_TypeOfEvent = (EditText)findViewById(R.id.editTextTypeOfEvent);
        _ET_EventLocation = (EditText)findViewById(R.id.editTextEventLocation);
        _ET_NameOfTheEventHost = (EditText)findViewById(R.id.editTextNameOfTheEventHost);

        _LL_Color_1 = (LinearLayout)findViewById(R.id.linearLayoutButtonColorRow1);
        _LL_TypeOfEvent = (LinearLayout)findViewById(R.id.linearLayoutTypeOfEvent);
        _LL_HintName = (LinearLayout)findViewById(R.id.hintNameCon);
        _LL_HintTypeCon = (LinearLayout)findViewById(R.id.hintTypeCon);
        _LL_HintLocation = (LinearLayout)findViewById(R.id.hintLocationCon);
        _LL_HintHost = (LinearLayout)findViewById(R.id.hintHostCon);

        _buttonChoiceColorLime = (Button) findViewById(R.id.buttonChoiceColorLime);
        _buttonChoiceColorGreen = (Button) findViewById(R.id.buttonChoiceColorCactus);
        _buttonChoiceColorBlue = (Button) findViewById(R.id.buttonChoiceColorBlue);
        _buttonChoiceColorPurple = (Button) findViewById(R.id.buttonChoiceColorPurple);
        _buttonChoiceColorPink = (Button) findViewById(R.id.buttonChoiceColorRose);
        _buttonChoiceColorRed = (Button) findViewById(R.id.buttonChoiceColorRed);
        _buttonChoiceColorOrange = (Button) findViewById(R.id.buttonChoiceColorPeach);
        _buttonChoiceColorGray = (Button) findViewById(R.id.buttonChoiceColorGray);
        _buttonChoiceColorTeal = (Button) findViewById(R.id.buttonChoiceColorBlack);
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
        _BT_upWeek = (Button)findViewById(R.id.button_up_week);
        _BT_downWeek = (Button)findViewById(R.id.button_down_week);

        _buttonSaveEvent = (CardView) findViewById(R.id.buttonSaveEvent);
        _buttonSaveEvent.setBackgroundResource(R.drawable.style_for_button_setting);

        _buttonBack = (CardView)findViewById(R.id.backButton);
        _buttonBack.setBackgroundResource(R.drawable.style_for_button_setting);

        _CV_ActionCon = (CardView)findViewById(R.id.action_con);
        _CV_ActionCon.setBackgroundResource(R.drawable.menu_white_background);



        // Добавляем кнопки к прослушиванию для метода onClick()
        _buttonChoiceColorLime.setOnClickListener(this);
        _buttonChoiceColorGreen.setOnClickListener(this);
        _buttonChoiceColorBlue.setOnClickListener(this);
        _buttonChoiceColorPurple.setOnClickListener(this);
        _buttonChoiceColorPink.setOnClickListener(this);
        _buttonChoiceColorRed.setOnClickListener(this);
        _buttonChoiceColorOrange.setOnClickListener(this);
        _buttonChoiceColorGray.setOnClickListener(this);
        _buttonChoiceColorTeal.setOnClickListener(this);
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
        _BT_upWeek.setOnClickListener(this);
        _BT_downWeek.setOnClickListener(this);

        _buttonSaveEvent.setOnClickListener(this);
        _buttonBack.setOnClickListener(this);

        // Рабочий пример кнопок
        _startTime = Calendar.getInstance();
        _endTime = Calendar.getInstance();

        _weekClick = new boolean[7];
        Arrays.fill(_weekClick, false);
        _weekTypeFlag = new boolean[2];
        Arrays.fill(_weekTypeFlag, false);

        typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.text_button_week_color_inactive, typedValue, true);
        colorWeekButtonInactive = typedValue.data;
        getTheme().resolveAttribute(R.attr.text_button_week_color_active, typedValue, true);
        colorWeekButtonActive = typedValue.data;

        ClickWeekUp(_weekTypeFlag[0]);
        ClickWeekDown(_weekTypeFlag[1]);

        // Получаем данные при добавлении нового события
        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            editFlag = bundle.getBoolean("editFlag");
            eventId = bundle.getInt("eventId");
            eventName = bundle.getString("eventName");
            colorId = bundle.getInt("colorId");
            eventType = bundle.getString("eventType");
            eventLocation = bundle.getString("eventLocation");
            eventHost = bundle.getString("eventHost");
            weekDayKey = bundle.getInt("WeekDayKey");

            HHStart = bundle.getInt("HHStart");
            MMStart = bundle.getInt("MMStart");
            HHEnd = bundle.getInt("HHEnd");
            MMEnd = bundle.getInt("MMEnd");
            FILE_NAME = bundle.getString("fileName");
            FILE_NAME_FOR_HINT_1 = bundle.getString("hintFile1");
            FILE_NAME_FOR_HINT_2 = bundle.getString("hintFile2");

            // Выбор дня недели в зависимости от того на каком
            // дне недели мы находились на гл. странице
            switch (weekDayKey){
                case 0:
                    _buttonChoiceWeekDayMon.performClick();
                    break;
                case 1:
                    _buttonChoiceWeekDayTue.performClick();
                    break;
                case 2:
                    _buttonChoiceWeekDayWed.performClick();
                    break;
                case 3:
                    _buttonChoiceWeekDayThu.performClick();
                    break;
                case 4:
                    _buttonChoiceWeekDayFri.performClick();
                    break;
                case 5:
                    _buttonChoiceWeekDaySat.performClick();
                    break;
                case 6:
                    _buttonChoiceWeekDaySun.performClick();
                    break;
                default:
                    break;
            }

            // Если Активити было открыто для редактирования уже существующего
            // события то editFlag = true
            if (editFlag){
                eventScheduleForEdit = new EventSchedule();

                if(eventId != -1){
                    eventScheduleForEdit.SetId(eventId);
                }
                else
                {
                    throw new Error("Incorrect ID. id = -1");
                }

                if(eventName != null){
                    _ET_EventName.setText(eventName);
                    eventScheduleForEdit.SetNameEvent(eventName);
                }

                eventScheduleForEdit.SetColorForEvent(colorId);
                // Программно эмулируем нажатие на кнопку выбора цвета
                switch (colorId){
                    case 1:
                        _buttonChoiceColorLime.performClick();
                        break;
                    case 2:
                        _buttonChoiceColorGreen.performClick();
                        break;
                    case 3:
                        _buttonChoiceColorBlue.performClick();
                        break;
                    case 4:
                        _buttonChoiceColorPurple.performClick();
                        break;
                    case 5:
                        _buttonChoiceColorPink.performClick();
                        break;
                    case 6:
                        _buttonChoiceColorRed.performClick();
                        break;
                    case 7:
                        _buttonChoiceColorOrange.performClick();
                        break;
                    case 8:
                        _buttonChoiceColorGray.performClick();
                        break;
                    case 9:
                        _buttonChoiceColorTeal.performClick();
                        break;
                    case 10:
                        _buttonChoiceColorBrown.performClick();
                        break;

                }

                if(eventType != null){
                    _ET_TypeOfEvent.setText(eventType);
                    eventScheduleForEdit.SetTypeEvent(eventType);
                }

                if(eventLocation != null){
                    _ET_EventLocation.setText(eventLocation);
                    eventScheduleForEdit.SetLocationEvent(eventLocation);
                }

                if(eventHost != null){
                    _ET_NameOfTheEventHost.setText(eventHost);
                    eventScheduleForEdit.SetEventHost(eventHost);
                }

                _startTime.set(Calendar.HOUR_OF_DAY, HHStart);
                _startTime.set(Calendar.MINUTE, MMStart);
                eventScheduleForEdit.SetTimeEventStart(_startTime);
                _buttonStartTime.setText(eventScheduleForEdit.GetStartTimeEvent());

                _endTime.set(Calendar.HOUR_OF_DAY, HHEnd);
                _endTime.set(Calendar.MINUTE, MMEnd);
                eventScheduleForEdit.SetTimeEventEnd(_endTime);
                _buttonEndTime.setText(eventScheduleForEdit.GetEndTimeEvent());
            }

            // Здесь работаем с подсказками
            try{
                _LL_HintName.setVisibility(View.VISIBLE);

                // Читаем из файлов расписания - расписание и объединяем в один список
                eventScheduleListForHint = FileIO.ReadScheduleEventListInFile(
                        FILE_NAME_FOR_HINT_1, this);

                eventScheduleListForHint = eventScheduleListForHint.addAll(
                        FileIO.ReadScheduleEventListInFile(FILE_NAME_FOR_HINT_2, this));

                // Получаем из списка событий - список наименований
                hintNameList = HintBuilder.getHintForNameByStr("", eventScheduleListForHint);

                // Получаем из списка событий - список типов мероприятий
                hintTypeList = HintBuilder.getHintForTypeByStr("", eventScheduleListForHint);

                // Получаем из списка событий - список локаций мероприятия
                hintLocationList = HintBuilder.getHintForLocationByStr("", eventScheduleListForHint);

                // Получаем из списка событий - список локаций мероприятия
                hintHostList = HintBuilder.getHintForHostByStr("", eventScheduleListForHint);

                ButtonHintBuilder(_LL_HintName, _ET_EventName, hintNameList);
                ButtonHintBuilder(_LL_HintTypeCon, _ET_TypeOfEvent, hintTypeList);
                ButtonHintBuilder(_LL_HintLocation, _ET_EventLocation, hintLocationList);
                ButtonHintBuilder(_LL_HintHost, _ET_NameOfTheEventHost, hintHostList);
            }
            catch (Error err){
                Toasty.error(this, Objects.requireNonNull(err.getMessage()), Toast.LENGTH_SHORT,
                        true).show();
            }
        }

        _ET_EventName.addTextChangedListener(new TextWatcherName());
        _ET_TypeOfEvent.addTextChangedListener(new TextWatcherType());
        _ET_EventLocation.addTextChangedListener(new TextWatcherLocation());
        _ET_NameOfTheEventHost.addTextChangedListener(new TextWatcherHost());
    }


    @Override
    public void onClick(View v){
        switch (v.getId()) {
            // Прослушивание кнопок выбора цвета
            case (R.id.buttonChoiceColorLime):
                _saveColor = 1;
                UpdColorImage(0);
                break;

            case (R.id.buttonChoiceColorCactus):
                _saveColor = 2;
                UpdColorImage(1);
                break;

            case (R.id.buttonChoiceColorBlue):
                _saveColor = 3;
                UpdColorImage(2);
                break;

            case (R.id.buttonChoiceColorPurple):
                _saveColor = 4;
                UpdColorImage(3);
                break;

            case (R.id.buttonChoiceColorRose):
                _saveColor = 5;
                UpdColorImage(4);
                break;

            case (R.id.buttonChoiceColorRed):
                _saveColor = 6;
                UpdColorImage(5);
                break;

            case (R.id.buttonChoiceColorPeach):
                _saveColor = 7;
                UpdColorImage(6);
                break;

            case (R.id.buttonChoiceColorGray):
                _saveColor = 8;
                UpdColorImage(7);
                break;

            case (R.id.buttonChoiceColorBlack):
                _saveColor = 9;
                UpdColorImage(8);
                break;

            case (R.id.buttonChoiceColorBrown):
                _saveColor = 10;
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
                    _buttonChoiceWeekDayMon.setTextColor(colorWeekButtonInactive);
                }
                else
                {
                    _buttonChoiceWeekDayMon.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                    _buttonChoiceWeekDayMon.setTextColor(colorWeekButtonActive);
                }
                _weekClick[0] = !_weekClick[0];
                break;

            case (R.id.buttonTue):
                if (_weekClick[1]){
                    _buttonChoiceWeekDayTue.setBackgroundResource(R.drawable.style_for_week_day_button);
                    _buttonChoiceWeekDayTue.setTextColor(colorWeekButtonInactive);
                }
                else
                {
                    _buttonChoiceWeekDayTue.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                    _buttonChoiceWeekDayTue.setTextColor(colorWeekButtonActive);
                }
                _weekClick[1] = !_weekClick[1];
                break;

            case (R.id.buttonWed):
                if (_weekClick[2]){
                    _buttonChoiceWeekDayWed.setBackgroundResource(R.drawable.style_for_week_day_button);
                    _buttonChoiceWeekDayWed.setTextColor(colorWeekButtonInactive);
                }
                else
                {
                    _buttonChoiceWeekDayWed.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                    _buttonChoiceWeekDayWed.setTextColor(colorWeekButtonActive);
                }
                _weekClick[2] = !_weekClick[2];
                break;

            case (R.id.buttonThu):
                if (_weekClick[3]){
                    _buttonChoiceWeekDayThu.setBackgroundResource(R.drawable.style_for_week_day_button);
                    _buttonChoiceWeekDayThu.setTextColor(colorWeekButtonInactive);
                }
                else
                {
                    _buttonChoiceWeekDayThu.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                    _buttonChoiceWeekDayThu.setTextColor(colorWeekButtonActive);
                }
                _weekClick[3] = !_weekClick[3];
                break;

            case (R.id.buttonFri):
                if (_weekClick[4]){
                    _buttonChoiceWeekDayFri.setBackgroundResource(R.drawable.style_for_week_day_button);
                    _buttonChoiceWeekDayFri.setTextColor(colorWeekButtonInactive);
                }
                else
                {
                    _buttonChoiceWeekDayFri.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                    _buttonChoiceWeekDayFri.setTextColor(colorWeekButtonActive);
                }
                _weekClick[4] = !_weekClick[4];
                break;

            case (R.id.buttonSat):
                if (_weekClick[5]){
                    _buttonChoiceWeekDaySat.setBackgroundResource(R.drawable.style_for_week_day_button);
                    _buttonChoiceWeekDaySat.setTextColor(colorWeekButtonInactive);
                }
                else
                {
                    _buttonChoiceWeekDaySat.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                    _buttonChoiceWeekDaySat.setTextColor(colorWeekButtonActive);
                }
                _weekClick[5] = !_weekClick[5];
                break;

            case (R.id.buttonSun):
                if (_weekClick[6]){
                    _buttonChoiceWeekDaySun.setBackgroundResource(R.drawable.style_for_week_day_button);
                    _buttonChoiceWeekDaySun.setTextColor(colorWeekButtonInactive);
                }
                else
                {
                    _buttonChoiceWeekDaySun.setBackgroundResource(R.drawable.style_for_week_day_button_click);
                    _buttonChoiceWeekDaySun.setTextColor(colorWeekButtonActive);
                }
                _weekClick[6] = !_weekClick[6];
                break;

            case (R.id.buttonSaveEvent):
                SaveEvent();
                break;
            case (R.id.backButton):
                this.finish();
                break;
            case (R.id.button_up_week):
                _weekTypeFlag[0] = !_weekTypeFlag[0];
                ClickWeekUp(_weekTypeFlag[0]);
                break;

            case (R.id.button_down_week):
                _weekTypeFlag[1] = !_weekTypeFlag[1];
                ClickWeekDown(_weekTypeFlag[1]);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    private void ClickWeekUp(boolean flag){
        if (flag){
            _BT_upWeek.setBackgroundResource(R.drawable.button_week_up_add_schedule_active);
            switch (currentNightMode){
                case Configuration.UI_MODE_NIGHT_NO:
                    _BT_upWeek.setTextColor(getResources().getColor(R.color.white));
                case  Configuration.UI_MODE_NIGHT_YES:

                    break;
            }
        }
        else
        {
            _BT_upWeek.setBackgroundResource(R.drawable.button_week_up_add_schedule_inactive);
            switch (currentNightMode){
                case Configuration.UI_MODE_NIGHT_NO:
                    _BT_upWeek.setTextColor(getResources().getColor(R.color.defoultTextColor));
                    break;
            }
        }
    }

    private void ClickWeekDown(boolean flag){
        if (flag){
            _BT_downWeek.setBackgroundResource(R.drawable.button_week_down_add_schedule_active);
            switch (currentNightMode){
                case Configuration.UI_MODE_NIGHT_NO:
                    _BT_downWeek.setTextColor(getResources().getColor(R.color.white));
                case  Configuration.UI_MODE_NIGHT_YES:

                    break;
            }
        }
        else
        {
            _BT_downWeek.setBackgroundResource(R.drawable.button_week_down_add_schedule_inactive);
            switch (currentNightMode){
                case Configuration.UI_MODE_NIGHT_NO:
                    _BT_downWeek.setTextColor(getResources().getColor(R.color.defoultTextColor));
                    break;
            }
        }
    }

    private void SetSaveColor(int color){
        _saveColor = color;
    }

    private void UpdColorImage(int colorButtonId){
        _buttonChoiceColorLime.setBackgroundResource(R.drawable.style_for_choice_button_lime);
        _buttonChoiceColorGreen.setBackgroundResource(R.drawable.style_for_choice_button_cactus);
        _buttonChoiceColorBlue.setBackgroundResource(R.drawable.style_for_choice_button_blue);
        _buttonChoiceColorPurple.setBackgroundResource(R.drawable.style_for_choice_button_purple);
        _buttonChoiceColorPink.setBackgroundResource(R.drawable.style_for_choice_button_pink);
        _buttonChoiceColorRed.setBackgroundResource(R.drawable.style_for_choice_button_red);
        _buttonChoiceColorOrange.setBackgroundResource(R.drawable.style_for_choice_button_peach);
        _buttonChoiceColorGray.setBackgroundResource(R.drawable.style_for_choice_button_gray);
        _buttonChoiceColorTeal.setBackgroundResource(R.drawable.style_for_choice_button_teal);
        _buttonChoiceColorBrown.setBackgroundResource(R.drawable.style_for_choice_button_brown);

        switch (colorButtonId){
            case 0:
                _buttonChoiceColorLime.setBackgroundResource(R.drawable.style_for_choice_button_lime_click);
                break;
            case 1:
                _buttonChoiceColorGreen.setBackgroundResource(R.drawable.style_for_choice_button_cactus_click);
                break;
            case 2:
                _buttonChoiceColorBlue.setBackgroundResource(R.drawable.style_for_choice_button_blue_click);
                break;
            case 3:
                _buttonChoiceColorPurple.setBackgroundResource(R.drawable.style_for_choice_button_purple_click);
                break;
            case 4:
                _buttonChoiceColorPink.setBackgroundResource(R.drawable.style_for_choice_button_pink_click);
                break;
            case 5:
                _buttonChoiceColorRed.setBackgroundResource(R.drawable.style_for_choice_button_red_click);
                break;
            case 6:
                _buttonChoiceColorOrange.setBackgroundResource(R.drawable.style_for_choice_button_peach_click);
                break;
            case 7:
                _buttonChoiceColorGray.setBackgroundResource(R.drawable.style_for_choice_button_gray_click);
                break;
            case 8:
                _buttonChoiceColorTeal.setBackgroundResource(R.drawable.style_for_choice_button_teal_click);
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
//                    currentTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                    currentTime.set(Calendar.MINUTE, minute);

                    time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    time.set(Calendar.MINUTE, minute);
                    button.setText(simpleDateFormat.format(time.getTime()));
                }
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityAddScheduleItem.this, R.style.TimePickerTheme,
                myTimeListener, hour, minute, true);

//        timePickerDialog.setTitle("Choose time:");
//        timePickerDialog.getWindow().setBackgroundDrawableResource(R.color.deep_orange_50);
        timePickerDialog.show();
    }

    private void SaveEvent(){
        ColorStateList colorStateListRed = ColorStateList.valueOf(0xFFFF9494);


        int checkWeekChoice = 0;
        for (boolean click: _weekClick){
            if (click){
                checkWeekChoice++;
            }
        }

        int checkWeekType = 0;
        for (boolean click: _weekTypeFlag){
            if (click){
                checkWeekType++;
            }
        }

        if(checkWeekType <= 0){
            Toasty.error(this, "1212345", Toast.LENGTH_SHORT,
                    true).show();
            return;
        }

        // Если не выбран день недели, выводим ошибку
        if (checkWeekChoice <= 0) {
            Toasty.error(this, R.string.error_choice_week_day, Toast.LENGTH_SHORT,
                    true).show();
            return;
        }
        else
        {
            // Если выбрано несколько дней недели то необходимо сохранить несколько экземпляров
            // класса EventSchedule и записать их, сделано так, что бы при удалении и редактировании
            // Обрабатывалось только одно событие

            // Выделение памяти для массива
            if(_weekTypeFlag[0]){
                _eventSchedule_1 = new EventSchedule[checkWeekChoice];
            }
            if (_weekTypeFlag[1]) {
                _eventSchedule_2 = new EventSchedule[checkWeekChoice];
            }
        }

        // Установка флагов дней недели
        int weekClickCount = 0;
        for(int i = 0; i < _weekClick.length; i++){
            if(_weekClick[i]){
                if (_weekTypeFlag[0]){
                    _eventSchedule_1[weekClickCount] = new EventSchedule();
                    _eventSchedule_1[weekClickCount].SetWeekDayPeek(i);
                }
                if (_weekTypeFlag[1]){
                    _eventSchedule_2[weekClickCount] = new EventSchedule();
                    _eventSchedule_2[weekClickCount].SetWeekDayPeek(i);
                }
                weekClickCount++;
            }
        }



        // Цикл для записи данных в объекты EventSchedule
        if(_weekTypeFlag[0]){
            for(EventSchedule event: _eventSchedule_1){
                event.setWeekId(0);
                // Event name
                if (_ET_EventName.getText().toString().length() != 0) {
                    event.SetNameEvent(_ET_EventName.getText().toString());
                }
                else{
                    _ET_EventName.setBackgroundTintList(colorStateListRed);
                    Toasty.error(this, R.string.WriteNameError, Toast.LENGTH_SHORT, true).show();
                    return;
                }

                // Type Event
                if (_ET_TypeOfEvent.getText().toString().length() != 0) {
                    event.SetTypeEvent(_ET_TypeOfEvent.getText().toString());
                }

                // Event Location
                if (_ET_EventLocation.getText().toString().length() != 0) {
                    event.SetLocationEvent(_ET_EventLocation.getText().toString());
                }

                // Event Host
                if (_ET_NameOfTheEventHost.getText().toString().length() != 0) {
                    event.SetEventHost(_ET_NameOfTheEventHost.getText().toString());
                }

                // Time start and End
                if(!editFlag){
                    event.SetTimeEventStart(_startTime);
                    event.SetTimeEventEnd(_endTime);
                }


                // Event add Color
                event.SetColorForEvent(_saveColor);
            }
        }

        if(_weekTypeFlag[1]){
            for(EventSchedule event: _eventSchedule_2){
                event.setWeekId(1);
                // Event name
                if (_ET_EventName.getText().toString().length() != 0) {
                    event.SetNameEvent(_ET_EventName.getText().toString());
                }
                else{
                    _ET_EventName.setBackgroundTintList(colorStateListRed);
                    Toasty.error(this, R.string.WriteNameError, Toast.LENGTH_SHORT, true).show();
                    return;
                }

                // Type Event
                if (_ET_TypeOfEvent.getText().toString().length() != 0) {
                    event.SetTypeEvent(_ET_TypeOfEvent.getText().toString());
                }

                // Event Location
                if (_ET_EventLocation.getText().toString().length() != 0) {
                    event.SetLocationEvent(_ET_EventLocation.getText().toString());
                }

                // Event Host
                if (_ET_NameOfTheEventHost.getText().toString().length() != 0) {
                    event.SetEventHost(_ET_NameOfTheEventHost.getText().toString());
                }

                // Time start and End
                event.SetTimeEventStart(_startTime);
                event.SetTimeEventEnd(_endTime);

                // Event add Color
                event.SetColorForEvent(_saveColor);
            }
        }



        // Сохранение данных в файл (Подразумевается, что файл уже существует, но ...)
        // FILE_NAME = "Event_Schedule_List.bin"
        try {
            // Читаем объект из файла
            EventScheduleList eventScheduleListInFile = FileIO.ReadScheduleEventListInFile(FILE_NAME, this);

            // Если редактируем, то просто удаляем старую запись
            if(editFlag){
                eventScheduleListInFile.RemoveEventsDayById(eventId);
            }

            // Добавляем в конец списка событие
            if(_weekTypeFlag[0]){
                for(EventSchedule event: _eventSchedule_1)
                    eventScheduleListInFile.AppendEvent(event);
            }

            if(_weekTypeFlag[1]){
                for(EventSchedule event: _eventSchedule_2)
                    eventScheduleListInFile.AppendEvent(event);
            }

            eventScheduleListInFile.SortEventList();

            FileIO.WriteScheduleEventListInFile(eventScheduleListInFile.GetEventsDayList(), FILE_NAME, this);
            Toasty.success(this, "Save", Toast.LENGTH_SHORT, true).show();
            setResult(Activity.RESULT_OK);
            finish();
        }
        catch (Error err){
            Toasty.error(this, Objects.requireNonNull(err.getMessage()), Toast.LENGTH_SHORT,
                    true).show();
        }

        MainActivity.getInstance().ReloadViewPager_1();
        MainActivity.getInstance().ReloadViewPager_2();
    }

    /**
     * Создаёт кнопки на основании выборки из списка ранее использованных наименований
     * @param hintNameArrayList - Список наименований
     */
    private void ButtonHintBuilder(LinearLayout linearLayoutForHint,
                                            EditText editTextForSetHint,
                                                ArrayList<String> hintNameArrayList){
        linearLayoutForHint.removeAllViews();

        if (hintNameArrayList.size() == 0){

            linearLayoutForHint.setVisibility(View.GONE);
        }
        else {
            linearLayoutForHint.setVisibility(View.VISIBLE);
        }

        LinearLayout.LayoutParams layoutParams = new  LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                (int) DpInPxDisplay.ConvertDpToPixels(this, 40)
        );

        // left, top, right, bottom
        layoutParams.setMargins(
                (int)DpInPxDisplay.ConvertDpToPixels(this, 8),
                0,
                0,
                (int)DpInPxDisplay.ConvertDpToPixels(this, 8));

        for(int i = 0; i < hintNameArrayList.size(); i++){

            Button btn = new Button(this);
            btn.setId(HintButtonId++);
            final int id_ = btn.getId();
            btn.setText(hintNameArrayList.get(i));

            btn.setPadding(
                    (int)DpInPxDisplay.ConvertDpToPixels(this, 8),
                    0,
                    (int)DpInPxDisplay.ConvertDpToPixels(this, 8),
                    0
            );

            btn.setBackground(ContextCompat.getDrawable(this, R.drawable.style_for_hintbutton));
            btn.setLayoutParams(layoutParams);

            linearLayoutForHint.addView(btn, i);
            Button btn1 = ((Button)findViewById(id_));

            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    editTextForSetHint.setText(btn1.getText());
                    linearLayoutForHint.setVisibility(View.GONE);
                }
            });
        }
    }


    // Класс для отлова события модификации текста в EditTextName
    public class TextWatcherName implements TextWatcher {

        @Override
        public void afterTextChanged(Editable editable) {
            hintNameList = HintBuilder.getHintForNameByStr(
                    String.valueOf(_ET_EventName.getText()), eventScheduleListForHint);

            ButtonHintBuilder(_LL_HintName, _ET_EventName, hintNameList);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    }

    // Класс для отлова события модификации текста в EditType
    public class TextWatcherType implements TextWatcher {

        @Override
        public void afterTextChanged(Editable editable) {
            hintNameList = HintBuilder.getHintForTypeByStr(
                    String.valueOf(_ET_TypeOfEvent.getText()), eventScheduleListForHint);

            ButtonHintBuilder(_LL_HintTypeCon, _ET_TypeOfEvent, hintNameList);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    }

    // Класс для отлова события модификации текста в EditType
    public class TextWatcherLocation implements TextWatcher {

        @Override
        public void afterTextChanged(Editable editable) {
            hintNameList = HintBuilder.getHintForLocationByStr(
                    String.valueOf(_ET_EventLocation.getText()), eventScheduleListForHint);

            ButtonHintBuilder(_LL_HintLocation, _ET_EventLocation, hintNameList);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    }

    // Класс для отлова события модификации текста в EditHost
    public class TextWatcherHost implements TextWatcher {

        @Override
        public void afterTextChanged(Editable editable) {
            hintHostList = HintBuilder.getHintForHostByStr(
                    String.valueOf(_ET_NameOfTheEventHost.getText()), eventScheduleListForHint);

            ButtonHintBuilder(_LL_HintHost, _ET_NameOfTheEventHost, hintHostList);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    }
}

//TODO: время при редактировании не должно изменяться