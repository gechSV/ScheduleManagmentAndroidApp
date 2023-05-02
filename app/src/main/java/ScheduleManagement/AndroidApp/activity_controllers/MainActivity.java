package ScheduleManagement.AndroidApp.activity_controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import ScheduleManagement.AndroidApp.EventSchedule;
import ScheduleManagement.AndroidApp.EventScheduleList;
import ScheduleManagement.AndroidApp.FileIO;
import ScheduleManagement.AndroidApp.R;
import ScheduleManagement.AndroidApp.TimeForNumberList;
import ScheduleManagement.AndroidApp.ViewPager2Adapter;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    // _IntentAddEvent - окно добавления события
    private Intent _IntentAddEvent;
    private Intent _IntentSetting;
    private CardView _BT_AddEvent;
    private  Boolean _isAllFabVisible;
    // _viewPager2 - что бы странички листались
    private ViewPager2 _viewPager_1;
    private ViewPager2 _viewPager_2;
    ViewPager2Adapter _viewPager2Adapter_1;
    ViewPager2Adapter _viewPager2Adapter_2;
    TabLayout headerForPage_1;
    TabLayout headerForPage_2;
    private CardView menuCon;

    private LinearLayout _LL_backGrayBlur;

    private CardView _CV_cardFullInform, _CV_menuFullInform, _CV_closeFullInform, _CV_FullInformNameBackground;

    TextView _TV_FullInformName, _TV_FullInformType, _TV_FullInformHost, _TV_FullInformLocation,
            _TV_FullInformStartTime, _TV_FullInformEndTime, _TV_FullInformTimeDuration;

    // _eventScheduleList - объект содержащий список событий
    private EventScheduleList _eventScheduleList_1;
    private  EventScheduleList _eventScheduleList_2;

    private final String FILE_NAME_EVENT_LIST_1 = "Event_Schedule_List_1.bin";
    private final String FILE_NAME_TIME_LIST = "TimeList.bin";
    private final String FILE_NAME_TIME_LIST_ZABGU = "TimeListForZabGU.bin";
    // Поле открывающее доступ к функциям этого класса из сторонних классов (Метод getInstance())
    private static MainActivity instance;

    // Для открытия страницы в зависимости от текущего дня недели
    private int currentWeekNumb = -1;

    // для отслеживания выбранной недели(чётная, либо не чётная)
    private int currentWeekType = -1;

    private int _weekFlag = 1;

    private Button _BT_upWeek, _BT_downWeek;

    private CardView _BTC_setting;

    private TextView test;

    private int currentNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

        menuCon = findViewById(R.id.menu_con);
        menuCon.setBackgroundResource(R.drawable.menu_background);

        // Кнопки меню
        _BT_AddEvent = findViewById(R.id.add_schedule_event);
        _BT_AddEvent.setBackgroundResource(R.drawable.style_for_add_button_in_menu);

        _BT_upWeek = findViewById(R.id.button_up_week);
        _BT_downWeek = findViewById(R.id.button_down_week);
        _BTC_setting = findViewById(R.id.button_setting);
        _BTC_setting.setBackgroundResource(R.drawable.style_for_button_setting);

        setActiveButtonWeekChoice(_weekFlag);

        _isAllFabVisible = false;

        // Инициализация активити для добавления события
        _IntentAddEvent = new Intent(MainActivity.this, ActivityAddScheduleItem.class);
        _IntentAddEvent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Инициализация активити настроек
        _IntentSetting = new Intent(MainActivity.this, ActivitySetting.class);

        // onClick для кнопки открытия активити для добавления события
        _BT_AddEvent.setOnClickListener(this);
        _BT_upWeek.setOnClickListener(this);
        _BT_downWeek.setOnClickListener(this);
        _BTC_setting.setOnClickListener(this);

        // Читаем список событий из файла для первой недли
        _eventScheduleList_1 = ReadEventListFromFile(FILE_NAME_EVENT_LIST_1);
//        _eventScheduleList_2 = ReadEventListFromFile(FILE_NAME_EVENT_LIST_1).GetEventScheduleByWeekType(1);

        // Настройка viewPager2
        _viewPager_1 = findViewById(R.id.viewpager_1);
        _viewPager_2 = findViewById(R.id.viewpager_2);
        headerForPage_1 = findViewById(R.id.tab_layout_1);
        headerForPage_2 = findViewById(R.id.tab_layout_2);

        _LL_backGrayBlur = (LinearLayout)findViewById(R.id.backGrayBlur);
        _LL_backGrayBlur.setOnClickListener(this);

        _CV_cardFullInform = (CardView)findViewById(R.id.cardFullInform);
        _CV_cardFullInform.setBackgroundResource(R.drawable.action_screen_menu);

        _CV_menuFullInform = (CardView)findViewById(R.id.menuFullInform);
        _CV_menuFullInform.setBackgroundResource(R.drawable.action_screen_menu);

        _CV_closeFullInform = (CardView)findViewById(R.id.closeFullInform);
        _CV_closeFullInform.setOnClickListener(this);
        _CV_closeFullInform.setBackgroundResource(R.drawable.style_for_button_setting);

        _CV_FullInformNameBackground = (CardView)findViewById(R.id.FullInformNameBackground);
        _CV_FullInformNameBackground.setBackgroundResource(R.drawable.style_for_card_time_lime);

        _TV_FullInformName = (TextView)findViewById(R.id.FullInformName);
        _TV_FullInformType = (TextView)findViewById(R.id.FullInformType);
        _TV_FullInformHost = (TextView)findViewById(R.id.FullInformHost);
        _TV_FullInformLocation = (TextView)findViewById(R.id.FullInformLocation);
        _TV_FullInformStartTime = (TextView)findViewById(R.id.FullInformStartTime);
        _TV_FullInformEndTime = (TextView)findViewById(R.id.FullInformEndTime);
        _TV_FullInformTimeDuration = (TextView)findViewById(R.id.FullInformTimeDuration);

        _viewPager2Adapter_1 = new ViewPager2Adapter(this, _eventScheduleList_1, 1);
        _viewPager2Adapter_2 = new ViewPager2Adapter(this, _eventScheduleList_1, 2);

        _viewPager_1.setAdapter(_viewPager2Adapter_1);
        _viewPager_2.setAdapter(_viewPager2Adapter_2);

        // Настройка tabLayout(показывает дни недели)
        TabLayoutMediator tabLayoutMediator_1 = new TabLayoutMediator(headerForPage_1, _viewPager_1,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                String[] weekDay = {getString(R.string.Mon), getString(R.string.Tue),
                        getString(R.string.Wed), getString(R.string.Thu), getString(R.string.Fri),
                        getString(R.string.Sat), getString(R.string.Sun)};

                tab.setText(weekDay[position]);
            }
        });
        tabLayoutMediator_1.attach();

        TabLayoutMediator tabLayoutMediator_2 = new TabLayoutMediator(headerForPage_2, _viewPager_2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        String[] weekDay = {getString(R.string.Mon), getString(R.string.Tue),
                                getString(R.string.Wed), getString(R.string.Thu), getString(R.string.Fri),
                                getString(R.string.Sat), getString(R.string.Sun)};

                        tab.setText(weekDay[position]);
                    }
                });
        tabLayoutMediator_2.attach();


        _viewPager_1.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                currentWeekNumb = _viewPager_1.getCurrentItem();
            }

            @Override
            public void onPageScrollStateChanged(int state){
                super.onPageScrollStateChanged(state);
            }
        });

        _viewPager_2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                currentWeekNumb = _viewPager_2.getCurrentItem();
            }

            @Override
            public void onPageScrollStateChanged(int state){
                super.onPageScrollStateChanged(state);
            }
        });

        // Открытие страницы в соотвествии с текущим днём недели
        if(currentWeekNumb == -1){
            Calendar c = Calendar.getInstance();
            // Получаем индикатор текущего дня недели
            currentWeekNumb = c.get(Calendar.DAY_OF_WEEK);
        }

        switch (currentWeekNumb){
            case 1:
                // пн
                _viewPager_1.setCurrentItem(6, false);
                _viewPager_2.setCurrentItem(6, false);
                break;
            case 2:
                // вт
                _viewPager_1.setCurrentItem(0, false);
                _viewPager_2.setCurrentItem(0, false);
                break;
            case 3:
                // ср
                _viewPager_1.setCurrentItem(1, false);
                _viewPager_2.setCurrentItem(1, false);
                break;
            case 4:
                // чт
                _viewPager_1.setCurrentItem(2, false);
                _viewPager_2.setCurrentItem(2, false);
                break;
            case 5:
                // пт
                _viewPager_1.setCurrentItem(3, false);
                _viewPager_2.setCurrentItem(3, false);
                break;
            case 6:
                // сб
                _viewPager_1.setCurrentItem(4, false);
                _viewPager_2.setCurrentItem(4, false);
                break;
            case 7:
                // вс
                _viewPager_1.setCurrentItem(5, false);
                _viewPager_2.setCurrentItem(5, false);
                break;
            default:
                // на всякий случай
                _viewPager_1.setCurrentItem(0, false);
                _viewPager_2.setCurrentItem(0, false);
                break;
        }

        if (!ReadFirstRunFlagFromFile("firstRun.bin")){
            TimeForNumberList newTime = this.ReadTimeForNumberListFromFile(FILE_NAME_TIME_LIST);
            newTime.FirstSetTimeList();
            // Заполнение шаблонов времени для пользовательского расписания
            FileIO.WriteTimeForNumberList(newTime.GetTimeForNumberList(), FILE_NAME_TIME_LIST, this);
            // Запись в файл шаблонов для скачиваемого расписания вузов
            FileIO.WriteTimeForNumberList(newTime.GetTimeForNumberList(), FILE_NAME_TIME_LIST_ZABGU, this);
        }
        FileIO.SetRunAppFlag(true, "firstRun.bin", this);
    }

    public void OnClickEditEvent(int id){
        EventSchedule event = _eventScheduleList_1.GetEventsDayById(id);
        if(event == null){
            throw new Error("The event was not found by id. id = null");
        }

        // Запуск активити для редактирования события
        Bundle bundleAdd = new Bundle();
        bundleAdd.putBoolean("editFlag", true);

        bundleAdd.putString("hintFile1", FILE_NAME_EVENT_LIST_1);
        bundleAdd.putString("hintFile2", FILE_NAME_EVENT_LIST_1);
        bundleAdd.putInt("eventId", event.GetId());
        bundleAdd.putString("eventName", event.GetNameEvent());
        bundleAdd.putInt("colorId", event.GetColorForEvent());
        bundleAdd.putString("eventType", event.GetTypeEvent());
        bundleAdd.putString("eventLocation", event.GetLocationEvent());
        bundleAdd.putString("eventHost", event.GetEventHost());
        bundleAdd.putInt("WeekDayKey", event.GetWeekDayPeekId());

        Calendar time = Calendar.getInstance();
        time = event.GetTimeEventStart();

        bundleAdd.putInt("HHStart", time.get(Calendar.HOUR_OF_DAY));
        bundleAdd.putInt("MMStart", time.get(Calendar.MINUTE));

        time = event.GetTimeEventEnd();
        bundleAdd.putInt("HHEnd", time.get(Calendar.HOUR_OF_DAY));
        bundleAdd.putInt("MMEnd", time.get(Calendar.MINUTE));

        bundleAdd.putString("fileName", FILE_NAME_EVENT_LIST_1);
        bundleAdd.putInt("weekType", _weekFlag);

        _IntentAddEvent.putExtras(bundleAdd);
        startActivity(_IntentAddEvent);
    }

    public void OnClickDeleteEvent(int id){
        if(_weekFlag == 1){
            FileIO.DeleteItemInFileById(FILE_NAME_EVENT_LIST_1,
                    this, id);
        } else if (_weekFlag == 2) {
            FileIO.DeleteItemInFileById(FILE_NAME_EVENT_LIST_1,
                    this, id);
        }
        else {
            //TODO: добавить ошибку
        }
    }

    public static MainActivity getInstance() {
        return instance;
    }

    /**
     * Чтение файла содержащего список событий расписания и последующая
     * его запись в переменную - _eventScheduleList
     */
    private EventScheduleList ReadEventListFromFile(String fileName){
        try {
            // Проверка существования файла содержащего список событий
            File file = new File(getApplicationContext().getFilesDir(), fileName);

            // Проверка на существование файла
            if(file.exists()){
                // Файл существует (был создан ранее)

                // Читаем список из файла и записываем в новый объект
                return FileIO.ReadScheduleEventListInFile(fileName, this);
//                Toasty.success(this, "Список был создан", Toast.LENGTH_SHORT, true).show();
//                Gson gson = new Gson();
//                String json = gson.toJson(eventList);
            }
            else
            {
                // Файл не существует (не был создан)
                // Cоздаём новый пустой объект и записываем его в файл
                EventScheduleList eventList = new EventScheduleList();
                FileIO.WriteScheduleEventListInFile(eventList.GetEventsDayList(), fileName, this);
                return eventList;
//                Toasty.error(this, "Не существ", Toast.LENGTH_SHORT, true).show();
            }
        }
        catch (Error err){
            Toasty.error(this, Objects.requireNonNull(err.getMessage()), Toast.LENGTH_SHORT, true).show();
        }
        return null;
    }

    /**
     * Обновление страниц ViewPager2
     */
    public void ReloadViewPager_1(){
        openViewPager(_weekFlag);

        // Читаем список событий из файла
        _eventScheduleList_1 = ReadEventListFromFile(FILE_NAME_EVENT_LIST_1);

        _viewPager_1 = findViewById(R.id.viewpager_1);

        _viewPager2Adapter_1 = new ViewPager2Adapter(MainActivity.this,
                _eventScheduleList_1, 1);

        _viewPager_1.setAdapter(_viewPager2Adapter_1);

        _viewPager_1.setCurrentItem(currentWeekNumb, false);
    }

    public void ReloadViewPager_2(){
        openViewPager(_weekFlag);

        // Читаем список событий из файла
        _eventScheduleList_1 = ReadEventListFromFile(FILE_NAME_EVENT_LIST_1);

        _viewPager_2 = findViewById(R.id.viewpager_2);

        _viewPager2Adapter_2 = new ViewPager2Adapter(MainActivity.this,
                _eventScheduleList_1, 2);

        _viewPager_2.setAdapter(_viewPager2Adapter_2);

        _viewPager_2.setCurrentItem(currentWeekNumb, false);
    }

    /**
     * Метод нужен для измеенения внешнего вида кнопок выбора недели
     * @param weekFlag 1- первая неделя; 2- вторая неделя
     */
    private void setActiveButtonWeekChoice(int weekFlag){
        if (weekFlag == 1){

            _BT_upWeek.setBackgroundResource(R.drawable.style_for_button_up_week_active);
            _BT_downWeek.setBackgroundResource(R.drawable.style_for_button_down_week_inactive);

            switch (currentNightMode){
                case Configuration.UI_MODE_NIGHT_NO:
                    _BT_upWeek.setTextColor(getResources().getColor(R.color.deep_orange_300));
                    _BT_downWeek.setTextColor(getResources().getColor(R.color.defoultTextColor));
                case  Configuration.UI_MODE_NIGHT_YES:

                    break;
            }
        }
        else if(weekFlag == 2) {

            _BT_upWeek.setBackgroundResource(R.drawable.style_for_button_up_inactive);
            _BT_downWeek.setBackgroundResource(R.drawable.style_for_button_down_week_active);

            switch (currentNightMode){
                case Configuration.UI_MODE_NIGHT_NO:
                    _BT_upWeek.setTextColor(getResources().getColor(R.color.defoultTextColor));
                    _BT_downWeek.setTextColor(getResources().getColor(R.color.deep_orange_300));
                    break;
            }
        }
    }

    private void modifiButtonWeekFlag(){
        if(_weekFlag == 1){
            _weekFlag = 2;
        } else {
            _weekFlag = 1;
        }
    }

    private void openViewPager(int weekFlag){
        if(weekFlag == 1){
            _viewPager_1.setVisibility(View.VISIBLE);
            headerForPage_1.setVisibility(View.VISIBLE);
            _viewPager_2.setVisibility(View.GONE);
            headerForPage_2.setVisibility(View.GONE);
            _viewPager_1.setCurrentItem(currentWeekNumb, false);
        }
        else if(weekFlag == 2){
            _viewPager_1.setVisibility(View.GONE);
            headerForPage_1.setVisibility(View.GONE);
            _viewPager_2.setVisibility(View.VISIBLE);
            headerForPage_2.setVisibility(View.VISIBLE);
            _viewPager_2.setCurrentItem(currentWeekNumb, false);
        }
        else {
            //TODO: добавить ошибку
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.add_schedule_event):
                // Запуск активити для добавления события
                Bundle bundleAdd = new Bundle();
                if(_weekFlag == 1){

                    bundleAdd.putInt("WeekDayKey", _viewPager_1.getCurrentItem());
                    bundleAdd.putString("fileName", FILE_NAME_EVENT_LIST_1);

                } else if (_weekFlag == 2) {

                    bundleAdd.putInt("WeekDayKey", _viewPager_2.getCurrentItem());
                    bundleAdd.putString("fileName", FILE_NAME_EVENT_LIST_1);

                }else {
                    //TODO: добавить ошибку
                }
                bundleAdd.putBoolean("editFlag", false);
                bundleAdd.putString("hintFile1", FILE_NAME_EVENT_LIST_1);
                bundleAdd.putString("hintFile2", FILE_NAME_EVENT_LIST_1);
                bundleAdd.putInt("weekType", _weekFlag);
                _IntentAddEvent.putExtras(bundleAdd);
                startActivity(_IntentAddEvent);
                break;

            case(R.id.button_up_week):
                modifiButtonWeekFlag();
                setActiveButtonWeekChoice(_weekFlag);
                openViewPager(_weekFlag);
                break;

            case(R.id.button_down_week):
                modifiButtonWeekFlag();
                setActiveButtonWeekChoice(_weekFlag);
                openViewPager(_weekFlag);
                break;
            case(R.id.button_setting):
                startActivity(_IntentSetting);
                break;
            case(R.id.backGrayBlur):
                if(_LL_backGrayBlur.getVisibility() == View.VISIBLE){
                    CloseInformTable();
                }
                break;
            case(R.id.closeFullInform):
                CloseInformTable();
                break;
            default:
                break;
        }
    }

    private void CloseInformTable(){
        Animation animationAlphaRev = AnimationUtils.loadAnimation(this,
                R.anim.animation_background_time_choice_reverse);
        Animation animationTranslationRev = AnimationUtils.loadAnimation(this,
                R.anim.animation_emergence_time_choice_con_reverse);

        _LL_backGrayBlur.startAnimation(animationAlphaRev);
        _CV_menuFullInform.startAnimation(animationTranslationRev);
        _CV_cardFullInform.startAnimation(animationTranslationRev);

        _LL_backGrayBlur.setVisibility(View.GONE);
        _CV_menuFullInform.setVisibility(View.GONE);
        _CV_cardFullInform.setVisibility(View.GONE);

    }

    public void ShowInformTable(int id){

        _TV_FullInformName.setText(_eventScheduleList_1.GetEventsDayById(id).GetNameEvent());

        String type = _eventScheduleList_1.GetEventsDayById(id).GetTypeEvent();
        if ((type != null) && (type != "")){
            _TV_FullInformType.setText("Type of event: " + type);
            _TV_FullInformType.setVisibility(View.VISIBLE);
        }
        else{
            _TV_FullInformType.setVisibility(View.GONE);
        }

        String host = _eventScheduleList_1.GetEventsDayById(id).GetEventHost();
        if((host != null) && (host != "")){
            _TV_FullInformHost.setText("Host of the event: " + host);
            _TV_FullInformHost.setVisibility(View.VISIBLE);
        }
        else{
            _TV_FullInformHost.setVisibility(View.GONE);
        }

        String location = _eventScheduleList_1.GetEventsDayById(id).GetLocationEvent();
        if((location != null) && (location != "")){
            _TV_FullInformLocation.setText("Location: " + location);
            _TV_FullInformLocation.setVisibility(View.VISIBLE);
        }
        else{
            _TV_FullInformLocation.setVisibility(View.GONE);
        }

        _TV_FullInformStartTime.setText(_eventScheduleList_1.GetEventsDayById(id)
                .GetStartTimeEvent().replace(':', '꞉'));

        _TV_FullInformEndTime.setText(_eventScheduleList_1.GetEventsDayById(id)
                .GetEndTimeEvent().replace(':', '꞉'));

        int startHH = _eventScheduleList_1.GetEventsDayById(id).GetTimeEventStart().get(Calendar.HOUR_OF_DAY);
        int startMM = _eventScheduleList_1.GetEventsDayById(id).GetTimeEventStart().get(Calendar.MINUTE);
        int endHH = _eventScheduleList_1.GetEventsDayById(id).GetTimeEventEnd().get(Calendar.HOUR_OF_DAY);
        int endMM = _eventScheduleList_1.GetEventsDayById(id).GetTimeEventEnd().get(Calendar.MINUTE);

        Calendar duration = _eventScheduleList_1.GetEventsDayById(id).GetTimeEventEnd();
        duration.add(Calendar.MINUTE, -startMM);
        duration.add(Calendar.HOUR_OF_DAY, -startHH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        _TV_FullInformTimeDuration.setText("Time duration: " + simpleDateFormat.format(duration.getTime())
                .replace(':', '꞉'));


        // Установка цвета карточки. По умолчанию (если не выбран цвет) - серый
        switch (_eventScheduleList_1.GetEventsDayById(id).GetColorForEvent()){
            case 1:
                _CV_FullInformNameBackground.setBackgroundResource(R.drawable.style_for_card_time_lime);
                break;
            case 2:
                _CV_FullInformNameBackground.setBackgroundResource(R.drawable.style_for_card_time_green);
                break;
            case 3:
                _CV_FullInformNameBackground.setBackgroundResource(R.drawable.style_for_card_time_blue);
                break;
            case 4:
                _CV_FullInformNameBackground.setBackgroundResource(R.drawable.style_for_card_time_purple);
                break;
            case 5:
                _CV_FullInformNameBackground.setBackgroundResource(R.drawable.style_for_card_time_pink);
                break;
            case 6:
                _CV_FullInformNameBackground.setBackgroundResource(R.drawable.style_for_card_time_red);
                break;
            case 7:
                _CV_FullInformNameBackground.setBackgroundResource(R.drawable.style_for_card_time_orange);
                break;
            case 8:
                _CV_FullInformNameBackground.setBackgroundResource(R.drawable.style_for_card_time_gray);
                break;
            case 9:
                _CV_FullInformNameBackground.setBackgroundResource(R.drawable.style_for_card_time_teal);
                break;
            case 10:
                _CV_FullInformNameBackground.setBackgroundResource(R.drawable.style_for_card_time_brown);
                break;
            default:
                _CV_FullInformNameBackground.setBackgroundResource(R.drawable.style_for_card_time_gray);
                break;
        }

        Animation animationAlpha = AnimationUtils.loadAnimation(this, R.anim.animation_background_time_choice);
        Animation animationTranslation = AnimationUtils.loadAnimation(this, R.anim.animation_emergence_time_choice_con);


        _LL_backGrayBlur.startAnimation(animationAlpha);
        _CV_menuFullInform.startAnimation(animationTranslation);
        _CV_cardFullInform.startAnimation(animationTranslation);

        _LL_backGrayBlur.setVisibility(View.VISIBLE);
        _CV_menuFullInform.setVisibility(View.VISIBLE);
        _CV_cardFullInform.setVisibility(View.VISIBLE);
    }

    /**
     * Чтение файла содержащего список паттернов времени
     */
    private TimeForNumberList ReadTimeForNumberListFromFile(String fileName){
        try {
            // Проверка существования файла содержащего список событий
            File file = new File(getApplicationContext().getFilesDir(), fileName);

            // Проверка на существование файла
            if(file.exists()){
                // Файл существует (был создан ранее)
                // Читаем список из файла и записываем в новый объект
                return FileIO.ReadTimeForNumberList(fileName, this);
            }
            else
            {
                // Файл не существует (не был создан)
                // Cоздаём новый пустой объект и записываем его в файл
                TimeForNumberList timeList = new TimeForNumberList();
                FileIO.WriteTimeForNumberList(timeList.GetTimeForNumberList(), fileName, this);
                return timeList;
            }
        }
        catch (Error err){
            Toasty.error(this, Objects.requireNonNull(err.getMessage()), Toast.LENGTH_SHORT, true).show();
        }
        return null;
    }

    /**
     * Чтение файла содержащего список паттернов времени
     */
    private boolean ReadFirstRunFlagFromFile(String fileName){
        try {
            // Проверка существования файла содержащего список событий
            File file = new File(getApplicationContext().getFilesDir(), fileName);

            // Проверка на существование файла
            if(file.exists()){
                // Файл существует (был создан ранее)
                // Читаем список из файла и записываем в новый объект
                return FileIO.CheckRunAppFlag(fileName, this);
            }
            else
            {
                // Файл не существует (не был создан)
                // Cоздаём новый пустой объект и записываем его в файл
                boolean flag = false;
                FileIO.SetRunAppFlag(flag, fileName, this);
                return flag;
            }
        }
        catch (Error err){
            Toasty.error(this, Objects.requireNonNull(err.getMessage()), Toast.LENGTH_SHORT, true).show();
        }
        return false;
    }
}