package ScheduleManagement.AndroidApp.activity_controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Objects;

import ScheduleManagement.AndroidApp.DpInPxDisplay;
import ScheduleManagement.AndroidApp.FileIO;
import ScheduleManagement.AndroidApp.R;
import ScheduleManagement.AndroidApp.TimeForNumber;
import ScheduleManagement.AndroidApp.TimeForNumberList;
import es.dmoral.toasty.Toasty;

public class ActivityTimeSetting extends AppCompatActivity implements View.OnClickListener{
    String TIME_LIST_FILE_NAME = "TimeList.bin";
    CardView _CV_ActionCon;
    private CardView _buttonBack;
    private CardView _addTime;
    private LinearLayout _LL_ButtonList;
    private TimeForNumberList _timeList;

    ImageView deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_setting);

        _CV_ActionCon = (CardView)findViewById(R.id.action_con);
        _CV_ActionCon.setBackgroundResource(R.drawable.menu_background);

        _buttonBack = (CardView)findViewById(R.id.backButton);
        _buttonBack.setBackgroundResource(R.drawable.style_for_button_setting);
        _buttonBack.setOnClickListener(this);

        _addTime = (CardView) findViewById(R.id.buttonAddTime);
        _addTime.setBackgroundResource(R.drawable.style_for_button_setting);
        _addTime.setOnClickListener(this);

        _LL_ButtonList = findViewById(R.id.button_list);

        this.buttonTimeBuild();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.backButton):
                this.finish();
                break;
            case (R.id.buttonAddTime):
                TimeForNumber newTime = new TimeForNumber(0);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
//                Toast.makeText(ActivityTimeSetting.this, simpleDateFormat.format(newTime.GetStartTime()), Toast.LENGTH_SHORT).show();
//                Log.d("MesLog", "" + simpleDateFormat.format(newTime.GetStartTime()));
                _timeList.AddTime(newTime);
                FileIO.WriteTimeForNumberList(_timeList.GetTimeForNumberList(),
                        TIME_LIST_FILE_NAME, this);
                this.buttonTimeBuild();
                break;
        }
    }

    /**
     * Чтение файла содержащего список времени и последующая
     * его запись в переменную
     */
    private TimeForNumberList ReadTimeListFromFile(String fileName){
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
     * Вызов таймпикера с последующей записью в файл
     * @param textView компонент текст которого необходимо изменить
     * @param time время для предустановки
     * @param id порядковый номер объекта для записи в файл
     * @param typeTime тип времени "st" - start, "end" - end
     */
    private void SetTime(TextView textView, Calendar time, int id, String typeTime){

        int hour = time.get(Calendar.HOUR_OF_DAY);
        int minute = time.get(Calendar.MINUTE);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    time.set(Calendar.MINUTE, minute);
                    textView.setText(simpleDateFormat.format(time.getTime()).replace(':', '꞉'));

                    if(Objects.equals(typeTime, "st")){
                        _timeList.SetStartTimeById(time, id);
                        _timeList.SortTimeList();
                        FileIO.WriteTimeForNumberList(_timeList.GetTimeForNumberList(), TIME_LIST_FILE_NAME,
                                ActivityTimeSetting.this);
                    }
                    else if (Objects.equals(typeTime, "end")){
                        _timeList.SetEndTimeById(time, id);
                        _timeList.SortTimeList();
                        FileIO.WriteTimeForNumberList(_timeList.GetTimeForNumberList(), TIME_LIST_FILE_NAME,
                                ActivityTimeSetting.this);
                    }
                }
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityTimeSetting.this, R.style.TimePickerTheme,
                myTimeListener, hour, minute, true);

        timePickerDialog.show();
    }

    private void buttonTimeBuild(){
        _LL_ButtonList.removeAllViews();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        try {
            _timeList = this.ReadTimeListFromFile(TIME_LIST_FILE_NAME);
            Objects.requireNonNull(_timeList).SortTimeList();
        }
        catch (Error err){
            Toasty.error(this, Objects.requireNonNull(err.getMessage()),
                    Toast.LENGTH_SHORT, true).show();
        }

        for(int i = 0; i < _timeList.length(); i++){

            Calendar timeStartBuf = _timeList.GetStartTimeById(i);
            Calendar timeEndBuf = _timeList.GetEndTimeById(i);

            LinearLayout LL = (LinearLayout) LayoutInflater
                    .from(this)
                    .inflate(R.layout.button_pattern_for_setting_time, null);

            TextView TV_count = (TextView)LL.findViewById(R.id.time_count);
            LinearLayout LL_TimeCon = (LinearLayout)LL.findViewById(R.id.TimeCon);
            TextView TV_startTime = (TextView)LL.findViewById(R.id.start_time);
            TextView TV_endTime = (TextView)LL.findViewById(R.id.end_time);
            CardView deleteButton = (CardView) LL.findViewById(R.id.deleteButton);

            LL.setId(View.generateViewId());

            TV_count.setText(String.valueOf(i + 1));

            LL_TimeCon.setId(View.generateViewId());

            TV_startTime.setId(View.generateViewId());
            TV_startTime.setText(simpleDateFormat.format(timeStartBuf.getTime()).replace(':', '꞉'));

            TV_endTime.setId(View.generateViewId());
            TV_endTime.setText(simpleDateFormat.format(timeEndBuf.getTime()).replace(':', '꞉'));

            deleteButton.setId(View.generateViewId());
            deleteButton.setBackgroundResource(R.drawable.style_for_delete_card);

            final int line_id = LL.getId();
            final int timeCon_id = LL_TimeCon.getId();
            final int startTime_id = TV_startTime.getId();
            final int endTime_id = TV_endTime.getId();
            final int delBtn_id = deleteButton.getId();
            final int id = i;

            _LL_ButtonList.addView(LL, i);

            LinearLayout timeCon = (LinearLayout)findViewById(timeCon_id);
            TextView txtViewStartTime = (TextView)findViewById(startTime_id);
            TextView txtViewEndTime = (TextView)findViewById(endTime_id);
            CardView delTime = (CardView) findViewById(delBtn_id);
            LinearLayout line = (LinearLayout)findViewById(line_id);

            txtViewStartTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SetTime(txtViewStartTime, timeStartBuf, id, "st");
                }
            });

            txtViewEndTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SetTime(txtViewEndTime, timeEndBuf, id, "end");
                }
            });

            delTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Настройка диалогового окна, предназначенного для подтверждения удаления
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTimeSetting.this,
                            R.style.AlertDialogCustom);
                    builder.setMessage("do you really want to remove the time pattern?");
                    builder.setCancelable(false);

                    // Кнопка ОТМЕНИТЬ
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    // Кнопка УДАЛИТЬ
                    builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FileIO.DeleteTimePatternInFileById(TIME_LIST_FILE_NAME, ActivityTimeSetting.this, id);
                            buttonTimeBuild();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                    // Настройка внешнего вида кнопок
                    Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                    Button pButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);

                    pButton.setTextColor(ContextCompat.getColor(ActivityTimeSetting.this, R.color.deep_orange_300));

                }
            });

            line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(delTime.getVisibility() == View.GONE){
                        // Анимация для кнопки удаления
                        TranslateAnimation animation = new TranslateAnimation
                                (DpInPxDisplay.ConvertDpToPixels(
                                        v.getContext(), 50), DpInPxDisplay.ConvertDpToPixels(
                                        v.getContext(), 0), 0, 0);

                        animation.setDuration(200);
                        animation.setFillAfter(true);

//                    delTime.setAnimation(animation);
                        timeCon.startAnimation(animation);
                        delTime.setVisibility(View.VISIBLE);
                    }
                    else if(delTime.getVisibility() == View.VISIBLE){
                        // Анимация для кнопки удаления
                        TranslateAnimation animation = new TranslateAnimation
                                (DpInPxDisplay.ConvertDpToPixels(
                                        v.getContext(), -50), DpInPxDisplay.ConvertDpToPixels(
                                        v.getContext(), 0), 0, 0);

                        animation.setDuration(200);
                        animation.setFillAfter(true);

//                    delTime.setAnimation(animation);
                        timeCon.startAnimation(animation);
                        delTime.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}