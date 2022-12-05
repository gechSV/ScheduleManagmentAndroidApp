package ScheduleManagement.AndroidApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public class ActivityAddScheduleItem extends AppCompatActivity implements View.OnClickListener{
    // _ET_EventName - текстовое поле для ввода названия события
    private EditText _ET_EventName;

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

    private LinearLayout _LL_TypeOfEvent;

    private int _saveColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_item);

        _ET_EventName = (EditText)findViewById(R.id.editTextEventName);

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

        _LL_TypeOfEvent = (LinearLayout)findViewById(R.id.linearLayoutTypeOfEvent);


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


    }

    public static int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            // Прослушивание кнопок выбора цвета
            case (R.id.buttonChoiceColorLime):
                SetSaveColor(0xFF8DC643);
                break;
            case (R.id.buttonChoiceColorCactus):
                SetSaveColor(0xFF009144);
                break;
            case (R.id.buttonChoiceColorBlue):
                SetSaveColor(0xFF00b2d6);
                break;
            case (R.id.buttonChoiceColorPurple):
                SetSaveColor(0xFF683093);
                break;
            case (R.id.buttonChoiceColorRose):
                SetSaveColor(0xFFd61e5e);
                break;
            case (R.id.buttonChoiceColorRed):
                SetSaveColor(0xFFed2528);
                break;
            case (R.id.buttonChoiceColorPeach):
                SetSaveColor(0xFFe63b43);
                break;
            case (R.id.buttonChoiceColorGray):
                SetSaveColor(0xFF999);
                break;
            case (R.id.buttonChoiceColorBlack):
                SetSaveColor(0xFF191919);
                break;
            case (R.id.buttonChoiceColorBrown):
                SetSaveColor(0xFF603a16);
                break;
        }
    }

    private void SetSaveColor(int color){
        _saveColor = color;
        _ET_EventName.setText(Integer.toString(color));
    }
}