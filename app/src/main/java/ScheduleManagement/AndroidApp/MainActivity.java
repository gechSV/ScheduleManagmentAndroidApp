package ScheduleManagement.AndroidApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // _IntentAddEvent - окно добавления события
    private Intent _IntentAddEvent;

    // FAB - кнопка ОТЕЦ
    private ExtendedFloatingActionButton _fabButton;

    // FAB - кнопки пездюки
    private  FloatingActionButton _fabAddEvent, _fabTest;

    // Отображение текста у пездюков
//    private TextView _textForFabEvent, _textForTest;

    private  Boolean _isAllFabsVisible;

    // _viewPager2 - что бы странички листались
    private ViewPager2 _viewPager2;

    // _eventScheduleList - объeкт содержащий список событий
    private  EventScheduleList _eventScheduleList;

    private final String FILE_NAME_EVENT_LIST = "Event_Schedule_List.bin";

    private TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        _isAllFabsVisible = false;

        _fabButton.shrink();

        _fabButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!_isAllFabsVisible){
                            _fabAddEvent.show();
                            _fabTest.show();
//                            _textForFabEvent.setVisibility(View.VISIBLE);
//                            _textForTest.setVisibility(View.VISIBLE);
                            _fabButton.setIcon(getDrawable(R.drawable.baseline_arrow_downward_24));
                            _fabButton.extend();

                            _isAllFabsVisible = true;
                        }
                        else
                        {
                            _fabAddEvent.hide();
                            _fabTest.hide();
//                            _textForFabEvent.setVisibility(View.GONE);
//                            _textForTest.setVisibility(View.GONE);
                            _fabButton.setIcon(getDrawable(R.drawable.baseline_arrow_upward_24));
                            _fabButton.shrink();

                            _isAllFabsVisible = false;
                        }
                    }
                });

        _fabAddEvent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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


        // Инициализация активити для добавления события
        _IntentAddEvent = new Intent(MainActivity.this, ActivityAddScheduleItem.class);

        try {
            // Проверка существования файла содержащего список событий
            File file = new File(getApplicationContext().getFilesDir(), FILE_NAME_EVENT_LIST);

            // Проверка на существование файла
            if(file.exists()){
                // Файл существует (был создан ранее)

                // Читаем список из файла и записываем в новый объект
                _eventScheduleList = FileIO.ReadScheduleEventListInFile(FILE_NAME_EVENT_LIST, this);
                Toasty.success(this, "Список был создан", Toast.LENGTH_SHORT, true).show();
                Gson gson = new Gson();
                String json = gson.toJson(_eventScheduleList);
//                test.setText("");
            }
            else
            {
                // Файл не существует (не был создан)

                // Cоздаём новый пустой объект и записываем его в файл
                _eventScheduleList = new EventScheduleList();
                FileIO.WriteScheduleEventListInFile(_eventScheduleList.GetEventsDayList(), FILE_NAME_EVENT_LIST, this);
                Toasty.error(this, "Не существ", Toast.LENGTH_SHORT, true).show();
            }
        }
        catch (Error err){
            Toasty.error(this, Objects.requireNonNull(err.getMessage()), Toast.LENGTH_SHORT, true).show();
        }


        // Настройка viewPager2
        _viewPager2 = findViewById(R.id.viewpager);

        ViewPager2Adapter _viewPager2Adapter = new ViewPager2Adapter(this, _eventScheduleList);

        _viewPager2.setAdapter(_viewPager2Adapter);

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
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
//            case (R.id.fab):
//                startActivity(_IntentAddEvent);
//                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}