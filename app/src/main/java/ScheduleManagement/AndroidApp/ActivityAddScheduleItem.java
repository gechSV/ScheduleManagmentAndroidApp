package ScheduleManagement.AndroidApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ActivityAddScheduleItem extends AppCompatActivity {
    // _ET_EventName - текстовое поле для ввода названия события
    EditText _ET_EventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_item);

        _ET_EventName = (EditText)findViewById(R.id.editTextEventName);


    }

}