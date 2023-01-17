package ScheduleManagement.AndroidApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    // _IntentAddEvent - окно добавления события
    private Intent _IntentAddEvent;

    private ViewPager2 _viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация активити для добавления события
        _IntentAddEvent = new Intent(MainActivity.this, ActivityAddScheduleItem.class);

        // Настройка viewPager2
        _viewPager2 = findViewById(R.id.viewpager);

        ViewPager2Adapter _viewPager2Adapter = new ViewPager2Adapter(this);

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