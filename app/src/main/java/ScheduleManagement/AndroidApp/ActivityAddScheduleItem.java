package ScheduleManagement.AndroidApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ActivityAddScheduleItem extends AppCompatActivity {
    // _ET_EventName - текстовое поле для ввода названия события
    EditText _ET_EventName;
    LinearLayout _LayoutColorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_item);

        _ET_EventName = (EditText)findViewById(R.id.editTextEventName);
        _LayoutColorButton = (LinearLayout)findViewById(R.id.ButtonLayout);

    }

    /**
     * Добавление кнопок выбора цвета
     */
    protected void CreateColorButton() {

    }
}