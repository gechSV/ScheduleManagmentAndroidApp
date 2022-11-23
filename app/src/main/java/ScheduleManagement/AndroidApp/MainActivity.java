package ScheduleManagement.AndroidApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    // _IntentAddEvent - окно добавления события
    private Intent _IntentAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        _IntentAddEvent = new Intent(MainActivity.this, ActivityAddScheduleItem.class);
        _IntentAddEvent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Запуск activity _IntentAddEvent
    public void OpenIntentAddAnEvent(View view) {
        startActivity(_IntentAddEvent);
    }
}