package ScheduleManagement.AndroidApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.Calendar;

import ScheduleManagement.AndroidApp.activity_controllers.MainActivity;

public class ActivityScheduleConstructor extends AppCompatActivity implements View.OnClickListener {

    private FlowLayout container_for_button;
    private LinearLayout backGrayBlur, container_add_event;

    private LinearLayout monday_up_week, monday_down_week, tuesday_up_week, tuesday_down_week,
            wednesday_up_week,  wednesday_down_week, thursday_up_week, thursday_down_week,
            friday_up_week, friday_down_week, saturday_up_week, saturday_down_week, sunday_up_week,
            sunday_down_week;
    private CardView card_add_event, save_event, close_add_event, menu_add_event, backButton,
                    save_schedule, action_con;
    private EditText pattern_name, pattern_host;
    private EventScheduleList schedulePatternList;
    private String FILE_NAME_FOR_PATTERN_CONSTRUCTOR = "PatternEventListForConstructor.bin";
    private final String FILE_NAME_EVENT_LIST_1 = "Event_Schedule_List_1.bin";
    private final String FILE_NAME_TIME_LIST_ZABGU = "TimeListForZabGU.bin";
    private EventSchedule currentSchedulePattern = null;
    private ArrayList<EventSchedule> bufferEventScheduleList;
    private ArrayList<ImageView> patternEventImageList;
    TimeForNumberList timeList;
    int buttonIdEvent = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_constructor);

        patternEventImageList = new ArrayList<>();

        container_for_button = (FlowLayout) findViewById(R.id.container_for_button);
        backGrayBlur = (LinearLayout)findViewById(R.id.backGrayBlur);
        backGrayBlur.setVisibility(View.GONE);
        container_add_event = (LinearLayout)findViewById(R.id.container_add_event);

        card_add_event = (CardView)findViewById(R.id.card_add_event);
        save_event= (CardView)findViewById(R.id.save_event);
        save_event.setOnClickListener(this);
        close_add_event = (CardView)findViewById(R.id.close_add_event);
        close_add_event.setOnClickListener(this);
        menu_add_event= (CardView)findViewById(R.id.menu_add_event);

        pattern_name = (EditText)findViewById(R.id.pattern_name);
        pattern_host = (EditText)findViewById(R.id.pattern_host);

        monday_up_week = (LinearLayout)findViewById(R.id.monday_up_week);
        monday_down_week= (LinearLayout)findViewById(R.id.monday_down_week);
        tuesday_up_week= (LinearLayout)findViewById(R.id.tuesday_up_week);
        tuesday_down_week = (LinearLayout)findViewById(R.id.tuesday_down_week);
        wednesday_up_week = (LinearLayout)findViewById(R.id.wednesday_up_week);
        wednesday_down_week = (LinearLayout)findViewById(R.id.wednesday_down_week);
        thursday_up_week= (LinearLayout)findViewById(R.id.thursday_up_week);
        thursday_down_week= (LinearLayout)findViewById(R.id.thursday_down_week);
        friday_up_week  = (LinearLayout)findViewById(R.id.friday_up_week);
        friday_down_week = (LinearLayout)findViewById(R.id.friday_down_week);
        saturday_up_week = (LinearLayout)findViewById(R.id.saturday_up_week);
        saturday_down_week = (LinearLayout)findViewById(R.id.saturday_down_week);
        sunday_down_week = (LinearLayout)findViewById(R.id.sunday_down_week);
        sunday_up_week= (LinearLayout)findViewById(R.id.sunday_up_week);

        backButton = (CardView)findViewById(R.id.backButton);
        backButton.setBackgroundResource(R.drawable.style_for_button_setting);
        backButton.setOnClickListener(this);

        save_schedule = (CardView)findViewById(R.id.save_schedule);
        save_schedule.setBackgroundResource(R.drawable.style_for_button_setting);
        save_schedule.setOnClickListener(this);

        action_con = (CardView)findViewById(R.id.action_con);
        action_con.setBackgroundResource(R.drawable.style_for_button_setting);
        action_con.setBackgroundResource(R.drawable.menu_background);

        timeList = FileIO.ReadTimeForNumberList(FILE_NAME_TIME_LIST_ZABGU, this);
        schedulePatternList = FileIO.ReadScheduleEventListInFile(this.FILE_NAME_FOR_PATTERN_CONSTRUCTOR, this);

        this.buildPatternButton();

        this.buildWeekDayButton(monday_up_week, 1, 0);
        this.buildWeekDayButton(monday_down_week, 2, 0);
        this.buildWeekDayButton(tuesday_up_week, 1, 1);
        this.buildWeekDayButton(tuesday_down_week, 2,1 );
        this.buildWeekDayButton(wednesday_up_week, 1, 2);
        this.buildWeekDayButton(wednesday_down_week, 2, 2);
        this.buildWeekDayButton(thursday_up_week, 1, 3);
        this.buildWeekDayButton(thursday_down_week, 2, 3);
        this.buildWeekDayButton(friday_up_week, 1, 4);
        this.buildWeekDayButton(friday_down_week, 2, 4);
        this.buildWeekDayButton(saturday_up_week, 1, 5);
        this.buildWeekDayButton(saturday_down_week, 2, 5);
        this.buildWeekDayButton(sunday_down_week, 1, 6);
        this.buildWeekDayButton(sunday_up_week, 2, 6);

        bufferEventScheduleList = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            bufferEventScheduleList.add(null);
        }
    }

    private void buildButtonAddEvent(){

        LinearLayout llPattern = (LinearLayout) LayoutInflater
                .from(this)
                .inflate(R.layout.pattern_for_button_name_constructor, null);

        CardView btnPattern =  (CardView)llPattern.findViewById(R.id.button);
        TextView textButton = (TextView)llPattern.findViewById(R.id.text_button);
        ImageView checkImage = (ImageView)llPattern.findViewById(R.id.check_image);

        textButton.setText(R.string.AddEvent);
        checkImage.setVisibility(View.GONE);

        container_for_button.addView(llPattern);

        final int buttonId = View.generateViewId();
        btnPattern.setId(buttonId);

        CardView onClickBtn = (CardView) findViewById(buttonId);
        onClickBtn.setBackgroundResource(getBackgroundResourceById(0));

        onClickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backGrayBlur.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addPatternEvent(){
        String name = pattern_name.getText().toString();
        String host = pattern_host.getText().toString();

        if (name.length() == 0){
            Toast.makeText(this, "name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (host.length() == 0){
            Toast.makeText(this, "host!", Toast.LENGTH_SHORT).show();
            return;
        }

        EventSchedule newPattern = new EventSchedule();
        newPattern.SetNameEvent(name);
        newPattern.SetHostEvent(host);
        int color = schedulePatternList.GetEventsDayList().size() + 1;
        if (color > 10){
            color = color % 10;
        }

        newPattern.SetColorForEvent(color);

        schedulePatternList.AppendEvent(newPattern);

        FileIO.WriteScheduleEventListInFile(schedulePatternList.GetEventsDayList(),
                FILE_NAME_FOR_PATTERN_CONSTRUCTOR, this);

        this.buildPatternButton();
    }

    private int getBackgroundResourceById(int colorId){
        switch (colorId){
            case 1:
                return R.drawable.color_button_12dp_lime;
            case 2:
                return R.drawable.color_button_12dp_green;
            case 3:
                return R.drawable.color_button_12dp_blue;
            case 4:
                return R.drawable.color_button_12dp_purple;
            case 5:
                return R.drawable.color_button_12dp_pink;
            case 6:
                return R.drawable.color_button_12dp_red;
            case 7:
                return R.drawable.color_button_12dp_orange;
            case 8:
                return R.drawable.color_button_12dp_gray;
            case 9:
                return R.drawable.color_button_12dp_teal;
            case 10:
                return R.drawable.color_button_12dp_brown;
            default:
                return R.drawable.style_for_hintbutton;
        }
    }

    private void buildPatternButton(){
        this.container_for_button.removeAllViews();
        this.buildButtonAddEvent();

        for(EventSchedule event: schedulePatternList.GetEventsDayList()){
            LinearLayout llPattern = (LinearLayout) LayoutInflater
                    .from(this)
                    .inflate(R.layout.pattern_for_button_name_constructor, null);

            CardView btnPattern =  (CardView)llPattern.findViewById(R.id.button);
            TextView textButton = (TextView)llPattern.findViewById(R.id.text_button);
            ImageView checkImage = (ImageView)llPattern.findViewById(R.id.check_image);

            textButton.setText(event.GetNameEvent());
            textButton.setTextColor(getColor(R.color.text_color));
            checkImage.setVisibility(View.GONE);

            container_for_button.addView(llPattern);

            final int buttonId = View.generateViewId();
            final int imgId = View.generateViewId();
            final EventSchedule curSchedulePat = event;
            btnPattern.setId(buttonId);
            checkImage.setId(imgId);

            CardView onClickBtn = (CardView) findViewById(buttonId);
            ImageView onClickCheckImage = (ImageView)findViewById(imgId);
            onClickBtn.setBackgroundResource(getBackgroundResourceById(event.GetColorForEvent()));
            patternEventImageList.add(onClickCheckImage);

            onClickBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickCheckImage.getVisibility() == View.GONE){
                        currentSchedulePattern = curSchedulePat;
                        delImg();
                        onClickCheckImage.setVisibility(View.VISIBLE);
                    }
                    else {
                        currentSchedulePattern = null;
                        onClickCheckImage.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void delImg(){
        for(ImageView img: patternEventImageList){
            img.setVisibility(View.GONE);
        }
    }

    private void buildWeekDayButton(LinearLayout linearLayout, int weekType, int weekDay){
        linearLayout.removeAllViews();

        for(int i = 0; i < 7; i++){

            LinearLayout llPattern = (LinearLayout) LayoutInflater
                    .from(this)
                    .inflate(R.layout.pattern_button_week_day_constructor, null);

            CardView btnPattern =  (CardView)llPattern.findViewById(R.id.button);
            TextView textButton = (TextView)llPattern.findViewById(R.id.text_button);
            ImageView checkImage = (ImageView)llPattern.findViewById(R.id.check_image);

            final int CurNum = i + 1;
            final int timeId = i;
            final String defText = CurNum + "";
            textButton.setText(defText);
            checkImage.setVisibility(View.GONE);

            linearLayout.addView(llPattern);

            final int buttonId = View.generateViewId();
            final int idEvent = buttonIdEvent;
            final int idText =  View.generateViewId();
            final int weekTypeFin = weekType;
            final int weekDayFin = weekDay;
            buttonIdEvent++;
            btnPattern.setId(buttonId);
            textButton.setId(idText);

            CardView onClickBtn = (CardView) findViewById(buttonId);
            TextView onClickText = (TextView) findViewById(idText);
            onClickBtn.setBackgroundResource(getBackgroundResourceById(0));

            onClickBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(currentSchedulePattern == null){
                        return;
                    }
                    onClickBtn.setBackgroundResource(getBackgroundResourceById(currentSchedulePattern.GetColorForEvent()));
                    onClickText.setText(CurNum + " " + currentSchedulePattern.GetNameEvent());
                    EventSchedule newEvent = new EventSchedule();
                    newEvent.SetNameEvent(currentSchedulePattern.GetNameEvent());
                    newEvent.SetHostEvent(currentSchedulePattern.GetEventHost());
                    newEvent.SetColorForEvent(currentSchedulePattern.GetColorForEvent());
                    newEvent.SetId(idEvent);
                    newEvent.setWeekId(weekTypeFin);
                    Calendar start = timeList.GetStartTimeById(timeId);
                    Calendar end = timeList.GetEndTimeById(timeId);
                    newEvent.SetWeekDayPeek(weekDayFin);
                    newEvent.SetTimeEventStart(start);
                    newEvent.SetTimeEventEnd(end);
                    bufferEventScheduleList.set(idEvent, newEvent);

                }
            });

            onClickBtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClickBtn.setBackgroundResource(getBackgroundResourceById(0));
                    onClickText.setText(defText);
                    bufferEventScheduleList.set(idEvent, null);
                    return true;
                }
            });
        }
    }

    private void saveScheduleEvent(){

        EventScheduleList eventScheduleList = FileIO.ReadScheduleEventListInFile(FILE_NAME_EVENT_LIST_1, this);

        for(EventSchedule event: bufferEventScheduleList){
            if(event == null){
                continue;
            }

            eventScheduleList.AppendEvent(event);
        }

        FileIO.WriteScheduleEventListInFile(eventScheduleList.GetEventsDayList(),
                FILE_NAME_EVENT_LIST_1, this);

        MainActivity.getInstance().ReloadViewPager_1();
        MainActivity.getInstance().ReloadViewPager_2();
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.save_event):
                this.addPatternEvent();
                break;
            case (R.id.close_add_event):
                backGrayBlur.setVisibility(View.GONE);
                break;
            case(R.id.backButton):
                this.finish();
                break;
            case(R.id.save_schedule):
                this.saveScheduleEvent();
                break;
        }
    }
}