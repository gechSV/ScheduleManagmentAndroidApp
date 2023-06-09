package ScheduleManagement.AndroidApp.activity_controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import ScheduleManagement.AndroidApp.EventScheduleList;
import ScheduleManagement.AndroidApp.FileIO;
import ScheduleManagement.AndroidApp.NoteList;
import ScheduleManagement.AndroidApp.R;

public class ActivityApplicationSettings extends AppCompatActivity implements View.OnClickListener {

    private CardView _CV_ActionCon, _buttonBack, _CV_button_save_server_address;
    private Button _BT_downWeek, _BT_upWeek;
    private EditText _ET_new_url;
    private Button clear_schedule, clear_note;
    private int currentNightMode;

    private final String FILE_NAME_EVENT_LIST_1 = "Event_Schedule_List_1.bin";
    private final String FILE_NAME_TIME_LIST = "TimeList.bin";
    private final String FILE_NAME_NOTE_LIST = "Node_List.bin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_settings);

        _CV_ActionCon = (CardView)findViewById(R.id.action_con);
        _CV_ActionCon.setBackgroundResource(R.drawable.menu_background);

        _CV_button_save_server_address = (CardView)findViewById(R.id.button_save_server_address);
        _CV_button_save_server_address.setBackgroundResource(R.drawable.style_for_button_setting);
        _CV_button_save_server_address.setOnClickListener(this);

        _ET_new_url = (EditText)findViewById(R.id.new_url);
        _ET_new_url.setHint(FileIO.getUrlAddress("urlAddress.bin", this));

        _buttonBack = (CardView)findViewById(R.id.backButton);
        _buttonBack.setBackgroundResource(R.drawable.style_for_button_setting);
        _buttonBack.setOnClickListener(this);

        _BT_upWeek = findViewById(R.id.button_up_week);
        _BT_upWeek.setOnClickListener(this);
        _BT_downWeek = findViewById(R.id.button_down_week);
        _BT_downWeek.setOnClickListener(this);

        clear_schedule = (Button)findViewById(R.id.clear_schedule);
        clear_schedule.setOnClickListener(this);

        clear_note = (Button)findViewById(R.id.clear_note);
        clear_note.setOnClickListener(this);

        currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

        boolean weekFlag = FileIO.getTypeWeekFlag("TypeWeekFlag.bin", this);
        Calendar dateNow = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
//        Toast.makeText(this, dateNow.get(Calendar.WEEK_OF_YEAR) + "" , Toast.LENGTH_SHORT).show();
        boolean even = dateNow.get(Calendar.WEEK_OF_YEAR) % 2 == 0;

        if(even){
            if(weekFlag){
                setActiveButtonWeekChoice(2, false);
            }
            else{
                setActiveButtonWeekChoice(1, false);
            }
        }
        else{
            if(!weekFlag){
                setActiveButtonWeekChoice(1, false);
            }
            else{
                setActiveButtonWeekChoice(2, false);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.backButton):
                this.finish();
                break;
            case(R.id.button_save_server_address):
                String newUrl = _ET_new_url.getText().toString();
                if(newUrl.length() > 0){
                    FileIO.setUrlAddress(newUrl, "urlAddress.bin", this);
                    Toast.makeText(this, R.string.The_server_address_has_been_updated,
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, R.string.The_address_field_cannot_be_empty,
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case(R.id.button_up_week):
                setActiveButtonWeekChoice(1, true);
                break;
            case(R.id.button_down_week):
                setActiveButtonWeekChoice(2, true);
                break;
            case(R.id.clear_schedule):
                ClearSchedule();
                break;
            case(R.id.clear_note):
                ClearNote();
                break;
        }
    }

    private void ClearSchedule(){
        // Настройка диалогового окна, предназначенного для подтверждения удаления
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityApplicationSettings.this,
                R.style.AlertDialogCustom);
        builder.setMessage(R.string.scelule_clear_text);
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
                EventScheduleList eventScheduleList = new EventScheduleList();
                FileIO.WriteScheduleEventListInFile(eventScheduleList.GetEventsDayList(), FILE_NAME_EVENT_LIST_1, ActivityApplicationSettings.this);
                // Вызываем метод MainActivity, который обновляет ViewPager
                MainActivity.getInstance().ReloadViewPager_1();
                MainActivity.getInstance().ReloadViewPager_2();
                Toast.makeText(ActivityApplicationSettings.this, "Done", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

        // Настройка внешнего вида кнопок
        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button pButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);

        pButton.setTextColor(ContextCompat.getColor(ActivityApplicationSettings.this, R.color.deep_orange_300));
    }

    private void ClearNote(){
        // Настройка диалогового окна, предназначенного для подтверждения удаления
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityApplicationSettings.this,
                R.style.AlertDialogCustom);
        builder.setMessage(R.string.delete_note_list);
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
                NoteList noteList = new NoteList();
                FileIO.WriteNoteListInFile(noteList.getNoteList(), FILE_NAME_NOTE_LIST, ActivityApplicationSettings.this);
                // Вызываем метод MainActivity, который обновляет ViewPager
                MainActivity.getInstance().ReloadViewPager_1();
                MainActivity.getInstance().ReloadViewPager_2();
                Toast.makeText(ActivityApplicationSettings.this, "Done", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

        // Настройка внешнего вида кнопок
        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button pButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);

        pButton.setTextColor(ContextCompat.getColor(ActivityApplicationSettings.this, R.color.deep_orange_300));
    }

    /**
     * Метод нужен для измеенения внешнего вида кнопок выбора недели
     * @param weekFlag 1- первая неделя; 2- вторая неделя
     */
    private void setActiveButtonWeekChoice(int weekFlag, boolean reloadFile){
        if (weekFlag == 1){

            _BT_upWeek.setBackgroundResource(R.drawable.style_for_button_up_week_active);
            _BT_downWeek.setBackgroundResource(R.drawable.style_for_button_down_week_inactive);

            switch (currentNightMode){
                case Configuration.UI_MODE_NIGHT_NO:
                    _BT_upWeek.setTextColor(getResources().getColor(R.color.deep_orange_300));
                    _BT_downWeek.setTextColor(getResources().getColor(R.color.defoultTextColor));
                case  Configuration.UI_MODE_NIGHT_YES:
                    break;
            }
        }
        else if(weekFlag == 2) {

            _BT_upWeek.setBackgroundResource(R.drawable.style_for_button_up_inactive);
            _BT_downWeek.setBackgroundResource(R.drawable.style_for_button_down_week_active);

            switch (currentNightMode){
                case Configuration.UI_MODE_NIGHT_NO:
                    _BT_upWeek.setTextColor(getResources().getColor(R.color.defoultTextColor));
                    _BT_downWeek.setTextColor(getResources().getColor(R.color.deep_orange_300));
                    break;
            }
        }

        if(reloadFile){
            Calendar dateNow = Calendar.getInstance();
            boolean flag = dateNow.get(Calendar.WEEK_OF_YEAR) % 2 == 0;

            if(flag && weekFlag == 2){
                FileIO.setTypeWeekFlag(true, "TypeWeekFlag.bin", this);
            }
            else if(!flag && weekFlag == 1)
            {
                FileIO.setTypeWeekFlag(true, "TypeWeekFlag.bin", this);
            }
            else{
                FileIO.setTypeWeekFlag(false, "TypeWeekFlag.bin", this);
            }
        }

    }
}