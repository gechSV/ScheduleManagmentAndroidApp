package ScheduleManagement.AndroidApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    // _IntentAddEvent - окно добавления события
    private Intent _IntentAddEvent;
    // _viewPager2 - что бы странички листались
    private ViewPager2 _viewPager2;

    // _eventScheduleList - объкт содержащий список событий
    private  EventScheduleList _eventScheduleList;

    private final String FILE_NAME_EVENT_LIST = "Event_Schedule_List.bin";

    private TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        test =  (TextView) findViewById(R.id.textView3);

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

    // Запуск activity _IntentAddEvent (добавление события в расписание)
    public void OpenIntentAddAnEvent(View view) {
        startActivity(_IntentAddEvent);
    }
}