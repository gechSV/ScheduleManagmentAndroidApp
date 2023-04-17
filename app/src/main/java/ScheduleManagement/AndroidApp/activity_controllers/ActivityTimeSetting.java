package ScheduleManagement.AndroidApp.activity_controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Objects;

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
                TimeForNumber newTime = new TimeForNumber();
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

        final Calendar currentTime = Calendar.getInstance();
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
                        FileIO.WriteTimeForNumberList(_timeList.GetTimeForNumberList(), TIME_LIST_FILE_NAME,
                                ActivityTimeSetting.this);
                    }
                    else if (Objects.equals(typeTime, "end")){
                        _timeList.SetEndTimeById(time, id);
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
            TextView TV_startTime = (TextView)LL.findViewById(R.id.start_time);
            TextView TV_endTime = (TextView)LL.findViewById(R.id.end_time);

            TV_count.setText(String.valueOf(i + 1));

            TV_startTime.setId(View.generateViewId());
            TV_startTime.setText(simpleDateFormat.format(timeStartBuf.getTime()).replace(':', '꞉'));

            TV_endTime.setId(View.generateViewId());
            TV_endTime.setText(simpleDateFormat.format(timeEndBuf.getTime()).replace(':', '꞉'));

            final int startTime_id = TV_startTime.getId();
            final int endTime_id = TV_endTime.getId();
            final int id = i;

            _LL_ButtonList.addView(LL, i);

            TextView txtViewStartTime = (TextView)findViewById(startTime_id);
            txtViewStartTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SetTime(txtViewStartTime, timeStartBuf, id, "st");
                }
            });

            TextView txtViewEndTime = (TextView)findViewById(endTime_id);
            txtViewEndTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SetTime(txtViewEndTime, timeEndBuf, id, "end");
                }
            });
//            Button btn1 = ((Button)findViewById(id_));

//            btn1.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    _PB_progress.setVisibility(ProgressBar.VISIBLE);
//                    if(!GetGroupName(btn1.getText().toString())){
//                        _PB_progress.setVisibility(ProgressBar.INVISIBLE);
//                        return;
//                    }
//                    _PB_progress.setVisibility(ProgressBar.INVISIBLE);
//                    _LL_ButtonBox.removeAllViews();
//                    buttonGroupBuild(groups);
//                }
//            });
        }
    }
}