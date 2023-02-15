package ScheduleManagement.AndroidApp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.io.File;
import java.util.Calendar;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity{
    // _IntentAddEvent - окно добавления события
    private Intent _IntentAddEvent;

    // FAB - кнопка ОТЕЦ
    private ExtendedFloatingActionButton _fabButton;

    // FAB - кнопки пездюки
    private  FloatingActionButton _fabAddEvent, _fabTest;

    private  Boolean _isAllFabVisible;

    // _viewPager2 - что бы странички листались
    private ViewPager2 _viewPager2;

    ViewPager2Adapter _viewPager2Adapter;

    TabLayout headerForPage;

    // _eventScheduleList - объект содержащий список событий
    private  EventScheduleList _eventScheduleList;

    private final String FILE_NAME_EVENT_LIST = "Event_Schedule_List.bin";

    // Поле открывающее доступ к функциям этого класса из сторонних классов (Метод getInstance())
    private static MainActivity instance;

    // Для открытия страницы в зависимости от текущего дня недели
    private int currentWeekNumb = -1;

    // для отслеживания выбранной недели(чётная, либо не чётная)
    private int currentWeekType = -1;

    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;


        // Объявление компонентов для FAB
        _fabButton = (ExtendedFloatingActionButton) findViewById(R.id.fab);

        // FAB кнопки
        _fabAddEvent = findViewById(R.id.add_schedule_event);
        _fabTest = findViewById(R.id.add_test);

        // TextView for FAB
//        _textForFabEvent = findViewById(R.id.add_event_action_text);
//        _textForTest = findViewById(R.id.add_test_text);

        // Настройка FAB
        _fabAddEvent.setVisibility(View.GONE);
        _fabTest.setVisibility(View.GONE);
//        _textForFabEvent.setVisibility(View.GONE);
//        _textForTest.setVisibility(View.GONE);

        _isAllFabVisible = false;

        _fabButton.shrink();

        // Инициализация активити для добавления события
        _IntentAddEvent = new Intent(MainActivity.this, ActivityAddScheduleItem.class);
        _IntentAddEvent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        _fabButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!_isAllFabVisible){
                            _fabAddEvent.show();
                            _fabTest.show();

                            _fabButton.setIcon(getDrawable(R.drawable.baseline_arrow_downward_24));
                            _fabButton.extend();

                            _isAllFabVisible = true;
                        }
                        else
                        {
                            _fabAddEvent.hide();
                            _fabTest.hide();

                            _fabButton.setIcon(getDrawable(R.drawable.baseline_arrow_upward_24));
                            _fabButton.shrink();

                            _isAllFabVisible = false;
                        }
                    }
                });

        // onClick для кнопки открытия активити для добавления события
        _fabAddEvent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Запуск активити для добавления события
                        Bundle bundleAdd = new Bundle();
                        bundleAdd.putInt("WeekDayKey", _viewPager2.getCurrentItem());
                        bundleAdd.putBoolean("editFlag", false);
                        _IntentAddEvent.putExtras(bundleAdd);
                        // TODO: тут закончил
                        startActivity(_IntentAddEvent);
                    }
                });

        // below is the sample action to handle add alarm
        // FAB. Here it shows simple Toast msg The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        _fabTest.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Alarm Added", Toast.LENGTH_SHORT).show();
                    }
                });


        // Читаем список событий из файла
        WriteEventListInFile();

        // Настройка viewPager2
        _viewPager2 = findViewById(R.id.viewpager);
        headerForPage = findViewById(R.id.tab_layout);

        _viewPager2Adapter = new ViewPager2Adapter(this, _eventScheduleList);

        _viewPager2.setAdapter(_viewPager2Adapter);

        // Настройка tabLayout(показывает дни недели)
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(headerForPage, _viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                String[] weekDay = {getString(R.string.Mon), getString(R.string.Tue),
                        getString(R.string.Wed), getString(R.string.Thu), getString(R.string.Fri),
                        getString(R.string.Sat), getString(R.string.Sun)};

                tab.setText(weekDay[position]);
            }
        });
        tabLayoutMediator.attach();


        _viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
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
                _viewPager2.setCurrentItem(6, false);
                break;
            case 2:
                // вт
                _viewPager2.setCurrentItem(0, false);
                break;
            case 3:
                // ср
                _viewPager2.setCurrentItem(1, false);
                break;
            case 4:
                // чт
                _viewPager2.setCurrentItem(2, false);
                break;
            case 5:
                // пт
                _viewPager2.setCurrentItem(3, false);
                break;
            case 6:
                // сб
                _viewPager2.setCurrentItem(4, false);
                break;
            case 7:
                // вс
                _viewPager2.setCurrentItem(5, false);
                break;
            default:
                // на всякий случай
                _viewPager2.setCurrentItem(0, false);
                break;
        }
    }


    public void OnClickEditEvent(int id){
        EventSchedule event = _eventScheduleList.GetEventsDayById(id);
        if(event == null){
            throw new Error("The event was not found by id. id = null");
        }

        // Запуск активити для редактирования события
        Bundle bundleAdd = new Bundle();
        bundleAdd.putBoolean("editFlag", true);
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

        _IntentAddEvent.putExtras(bundleAdd);
        // TODO: тут закончил
        startActivity(_IntentAddEvent);
    }

    public static MainActivity getInstance() {
        return instance;
    }

    /**
     * Чтение файла содержащего список событий расписания и последующая
     * его запись в переменную - _eventScheduleList
     */
    private void WriteEventListInFile(){
        try {
            // Проверка существования файла содержащего список событий
            File file = new File(getApplicationContext().getFilesDir(), FILE_NAME_EVENT_LIST);

            // Проверка на существование файла
            if(file.exists()){
                // Файл существует (был создан ранее)

                // Читаем список из файла и записываем в новый объект
                _eventScheduleList = FileIO.ReadScheduleEventListInFile(FILE_NAME_EVENT_LIST, this);
//                Toasty.success(this, "Список был создан", Toast.LENGTH_SHORT, true).show();
                Gson gson = new Gson();
                String json = gson.toJson(_eventScheduleList);
            }
            else
            {
                // Файл не существует (не был создан)

                // Cоздаём новый пустой объект и записываем его в файл
                _eventScheduleList = new EventScheduleList();
                FileIO.WriteScheduleEventListInFile(_eventScheduleList.GetEventsDayList(), FILE_NAME_EVENT_LIST, this);
//                Toasty.error(this, "Не существ", Toast.LENGTH_SHORT, true).show();
            }
        }
        catch (Error err){
            Toasty.error(this, Objects.requireNonNull(err.getMessage()), Toast.LENGTH_SHORT, true).show();
        }
    }

    /**
     * Обновление страниц ViewPager2
     */
    public void ReloadViewPager(){
        currentWeekNumb = _viewPager2.getCurrentItem();
        // Читаем список событий из файла
        WriteEventListInFile();

        _viewPager2 = findViewById(R.id.viewpager);

        _viewPager2Adapter = new ViewPager2Adapter(MainActivity.this, _eventScheduleList);

        _viewPager2.setAdapter(_viewPager2Adapter);

        _viewPager2.setCurrentItem(currentWeekNumb, false);
    }
}