package ScheduleManagement.AndroidApp.activity_controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import ScheduleManagement.AndroidApp.EventSchedule;
import ScheduleManagement.AndroidApp.EventScheduleList;
import ScheduleManagement.AndroidApp.FileIO;
import ScheduleManagement.AndroidApp.R;
import ScheduleManagement.AndroidApp.TimeForNumberList;
import ScheduleManagement.AndroidApp.middleware_class.Groups;
import ScheduleManagement.AndroidApp.middleware_class.Organization;
import ScheduleManagement.AndroidApp.middleware_class.Schedule;
import ScheduleManagement.AndroidApp.httpAppClient;

public class activity_choosing_teacher_schedule extends AppCompatActivity implements View.OnClickListener{

    private httpAppClient _httpAppClient;
    private CardView _CV_ActionCon;
    private CardView _buttonBack, _buttonSearchGroup, _CV_cardScheduleDemonstration,
            _CV_menuScheduleDemonstration, _CV_closeScheduleDemonstration, _CV_addSchedule;
    private LinearLayout _LL_ConnectErrorBox, _LL_backGrayBlur, _LL_ScheduleDemonstrationUp,
            _LL_ScheduleDemonstratioDown;
    private LinearLayout _LL_ButtonBox;
    private ProgressBar _PB_progress;
    private EditText _ET_SearchGroup;
    private Organization[] organization;
    private Groups[] groups;
    private Schedule[] _Schedule;
    int idButton = 0;

    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_teacher_schedule);

        _CV_ActionCon = (CardView)findViewById(R.id.action_con);
        _CV_ActionCon.setBackgroundResource(R.drawable.menu_background);

        _buttonBack = (CardView)findViewById(R.id.backButton);
        _buttonBack.setBackgroundResource(R.drawable.style_for_button_setting);
        _buttonBack.setOnClickListener(this);

        _buttonSearchGroup = (CardView)findViewById(R.id.buttonSearchTeacher);
        _buttonSearchGroup.setBackgroundResource(R.drawable.style_for_button_setting);
        _buttonSearchGroup.setOnClickListener(this);

        _LL_ConnectErrorBox = (LinearLayout)findViewById(R.id.connectErrorBox);
        _LL_ButtonBox = (LinearLayout)findViewById(R.id.button_list);
        _PB_progress = (ProgressBar)findViewById(R.id.progressBar);
        _PB_progress.setVisibility(ProgressBar.INVISIBLE);

        _ET_SearchGroup = (EditText)findViewById(R.id.etSearchTeacher);

        _httpAppClient = new httpAppClient(this);

        ActivitySetting.getInstance().HideProgressBar();

        if((!this.getOrganizationName()) || organization == null) {
            _LL_ConnectErrorBox.setVisibility(View.VISIBLE);
            return;
        }

        _LL_backGrayBlur = (LinearLayout)findViewById(R.id.backGrayBlur);
        _LL_backGrayBlur.setOnClickListener(this);

        _CV_cardScheduleDemonstration = (CardView)findViewById(R.id.cardScheduleDemonstration);
        _CV_cardScheduleDemonstration.setBackgroundResource(R.drawable.action_screen_menu);

        _CV_menuScheduleDemonstration = (CardView)findViewById(R.id.menuScheduleDemonstration);
        _CV_menuScheduleDemonstration.setBackgroundResource(R.drawable.action_screen_menu);

        _CV_closeScheduleDemonstration = (CardView)findViewById(R.id.closeScheduleDemonstration);
        _CV_closeScheduleDemonstration.setOnClickListener(this);
        _CV_closeScheduleDemonstration.setBackgroundResource(R.drawable.style_for_button_setting);

        _LL_ScheduleDemonstrationUp = (LinearLayout)findViewById(R.id.llScheduleDemonstrationUp);
        _LL_ScheduleDemonstratioDown = (LinearLayout)findViewById(R.id.llScheduleDemonstrationDown);

        _CV_addSchedule = (CardView)findViewById(R.id.addSchedule);
        _CV_addSchedule.setBackgroundResource(R.drawable.style_for_button_setting);
        _CV_addSchedule.setOnClickListener(this);
        this.buttonOrganizationBuild(organization);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.backButton):
                this.finish();
                break;
            case(R.id.buttonSearchTeacher):
                String searchGr = _ET_SearchGroup.getText().toString().toLowerCase(Locale.ROOT);
                ArrayList<Groups> ALSearchGR = new ArrayList<>();

                for(int i = 0; i < groups.length; i++){
                    if(groups[i].getName().toLowerCase().contains(searchGr)){
                        ALSearchGR.add(groups[i]);
                    }
                }

                Groups[] SearchGR = new Groups[ALSearchGR.size()];
                ALSearchGR.toArray(SearchGR);

                this.buttonGroupBuild(SearchGR);

                break;
            case(R.id.backGrayBlur):
                if(_LL_backGrayBlur.getVisibility() == View.VISIBLE){
                    CloseInformTable();
                }
                break;
            case(R.id.closeScheduleDemonstration):
                CloseInformTable();
                break;
            case (R.id.addSchedule):
                saveSchedule();
                break;
        }
    }

    private void CloseInformTable(){
        Animation animationAlphaRev = AnimationUtils.loadAnimation(this,
                R.anim.animation_background_time_choice_reverse);
        Animation animationTranslationRev = AnimationUtils.loadAnimation(this,
                R.anim.animation_emergence_time_choice_con_reverse);

        _LL_backGrayBlur.startAnimation(animationAlphaRev);
        _CV_menuScheduleDemonstration.startAnimation(animationTranslationRev);
        _CV_cardScheduleDemonstration.startAnimation(animationTranslationRev);

        _LL_backGrayBlur.setVisibility(View.GONE);
        _CV_menuScheduleDemonstration.setVisibility(View.GONE);
        _CV_menuScheduleDemonstration.setVisibility(View.GONE);

    }

    private boolean getOrganizationName(){
        try {
            // Запускаем дочерний поток для запроса к серверу
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // получаем массив наименований организаций
                        organization = _httpAppClient.GetOrganizationNameByTypeName();
                    }
                    catch (Exception err){
                        Log.d("MesLog", err.getMessage());
                    }
                }
            });
            t1.start();
            t1.join();
            return true;
        }
        catch(RuntimeException | InterruptedException err){
            return false;
        }
    }

    private boolean GetGroupName(String orgName, String ScheduleType){
        try {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        groups = _httpAppClient.GetGroupByNameOrganizations(orgName, ScheduleType);
                    }
                    catch (Exception err){
                        Log.d("MesLog", err.getMessage());
                    }
                }
            });
            t1.start();
            t1.join();
            Log.d("MesLog", groups[0].getName());
            return true;
        }
        catch (RuntimeException | InterruptedException err){
            _LL_ConnectErrorBox.setVisibility(View.VISIBLE);
            return false;
        }
    }

    private boolean GetSchedule(String groupName, String password){
        try {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        _Schedule = _httpAppClient.getScheduleByName(groupName, password);
                    }
                    catch (Exception err){
                        Log.d("MesLog", err.getMessage());
                    }
                }
            });
            t1.start();
            t1.join();
            return true;
        }
        catch (RuntimeException err){
            _LL_ConnectErrorBox.setVisibility(View.VISIBLE);
            return false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void buttonOrganizationBuild(Organization[] org){
        for(int i = 0; i < org.length; i++){

            Button btn = (Button) LayoutInflater
                    .from(this)
                    .inflate(R.layout.button_pattern_for_setting, null);

            btn.setId(idButton++);
            final int id_ = btn.getId();
            btn.setText(org[i].getName());

            _LL_ButtonBox.addView(btn, i);
            Button btn1 = ((Button)findViewById(id_));

            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    _PB_progress.setVisibility(ProgressBar.VISIBLE);
                    if(!GetGroupName(btn1.getText().toString(), "Расписание преподавателей")){
                        _PB_progress.setVisibility(ProgressBar.INVISIBLE);
                        return;
                    }
                    _PB_progress.setVisibility(ProgressBar.INVISIBLE);
                    _LL_ButtonBox.removeAllViews();
                    buttonGroupBuild(groups);

                    Animation animationMove = AnimationUtils.loadAnimation(activity_choosing_teacher_schedule.this,
                            R.anim.animation_open_search_panel);

                    _ET_SearchGroup.startAnimation(animationMove);
                    _buttonSearchGroup.startAnimation(animationMove);

                    _ET_SearchGroup.setVisibility(View.VISIBLE);
                    _buttonSearchGroup.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void buttonGroupBuild(Groups[] gr){
        _LL_ButtonBox.removeAllViews();
        for (int i = 0; i < gr.length; i++){
            Button btn = (Button) LayoutInflater
                    .from(this)
                    .inflate(R.layout.button_pattern_for_setting, null);

            btn.setId(idButton++);
            final int id_ = btn.getId();
            btn.setText(gr[i].getName());

            _LL_ButtonBox.addView(btn, i);
            Button btn1 = ((Button)findViewById(id_));

            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    groupName = btn1.getText().toString();
                    showScheduleDemonstration(btn1);
                }
            });
        }
    }

    private void showScheduleDemonstration(Button btn1){
        int cardId = 0;

        _LL_ConnectErrorBox.setVisibility(View.INVISIBLE);
        if(!GetSchedule(btn1.getText().toString(), "zabgu") || _Schedule == null){
            _LL_ButtonBox.setVisibility(View.INVISIBLE);
            _LL_ConnectErrorBox.setVisibility(View.VISIBLE);
            return;
        }

        TimeForNumberList timeList = FileIO.ReadTimeForNumberList("TimeListForZabGU.bin",
                activity_choosing_teacher_schedule.this);

        EventScheduleList eventList = new EventScheduleList();

        for(Schedule schedule: _Schedule){
            EventSchedule event = schedule.toEventScheduleForTeacher(timeList);
            event.setScheduleType(1);
            eventList.AppendEvent(event);
        }

        eventList.SetColorForIdenticalEvents();
        Collections.sort(eventList.GetEventsDayList());

        _LL_ScheduleDemonstrationUp.removeAllViews();
        _LL_ScheduleDemonstratioDown.removeAllViews();

        this.buildDemonstrationCards(eventList, cardId, _LL_ScheduleDemonstrationUp, 1);
        this.buildDemonstrationCards(eventList, cardId, _LL_ScheduleDemonstratioDown, 2);

        Animation animationAlpha = AnimationUtils.loadAnimation(this, R.anim.animation_background_time_choice);
        Animation animationTranslation = AnimationUtils.loadAnimation(this, R.anim.animation_emergence_time_choice_con);


        _LL_backGrayBlur.startAnimation(animationAlpha);
        _CV_menuScheduleDemonstration.startAnimation(animationTranslation);
        _CV_cardScheduleDemonstration.startAnimation(animationTranslation);

        _LL_backGrayBlur.setVisibility(View.VISIBLE);
        _CV_menuScheduleDemonstration.setVisibility(View.VISIBLE);
        _CV_cardScheduleDemonstration.setVisibility(View.VISIBLE);
    }

    private void buildDemonstrationCards(EventScheduleList eventList, int cardId,
                                         LinearLayout ScheduleDemonstration, int typeWeek){

        for (int i = 0; i < 7; i++){

            if(eventList.GetEventByDayWeekAndWeekType(i, typeWeek) ){
                LinearLayout weekDayCon = (LinearLayout)LayoutInflater
                        .from(activity_choosing_teacher_schedule.this)
                        .inflate(R.layout.week_day_text_view_pattern, null);

                TextView weekday = weekDayCon.findViewById(R.id.text);

                switch (i+1){
                    case(1):
                        weekday.setText("Monday");
                        break;
                    case(2):
                        weekday.setText("Tuesday");
                        break;
                    case(3):
                        weekday.setText("Wednesday");
                        break;
                    case(4):
                        weekday.setText("Thursday");
                        break;
                    case(5):
                        weekday.setText("Friday");
                        break;
                    case(6):
                        weekday.setText("Saturday");
                        break;
                    case(7):
                        weekday.setText("Sunday");
                        break;
                }

                ScheduleDemonstration.addView(weekDayCon, cardId);
                cardId++;
            }

            for(EventSchedule event: eventList.GetEventsDayList()){

                if(event.GetWeekDayPeekId() == i){
                    LinearLayout container = (LinearLayout)LayoutInflater
                            .from(activity_choosing_teacher_schedule.this)
                            .inflate(R.layout.card_pattern_for_schedule_demonstration, null);

                    TextView evName = container.findViewById(R.id.event_name);
                    TextView evType = container.findViewById(R.id.event_type);
                    TextView evHost = container.findViewById(R.id.event_host);
                    TextView evLocation = container.findViewById(R.id.event_location);
                    CardView timeCard = container.findViewById(R.id.card_time);
                    TextView stTime = container.findViewById(R.id.textViewCardTimeStart);
                    TextView endTime = container.findViewById(R.id.textViewCardTimeEnd);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                    // Установка цвета карточки. По умолчанию (если не выбран цвет) - серый
                    switch (event.GetColorForEvent()){
                        case 1:
                            timeCard.setBackgroundResource(R.drawable.style_for_card_time_lime);
                            break;
                        case 2:
                            timeCard.setBackgroundResource(R.drawable.style_for_card_time_green);
                            break;
                        case 3:
                            timeCard.setBackgroundResource(R.drawable.style_for_card_time_blue);
                            break;
                        case 4:
                            timeCard.setBackgroundResource(R.drawable.style_for_card_time_purple);
                            break;
                        case 5:
                            timeCard.setBackgroundResource(R.drawable.style_for_card_time_pink);
                            break;
                        case 6:
                            timeCard.setBackgroundResource(R.drawable.style_for_card_time_red);
                            break;
                        case 7:
                            timeCard.setBackgroundResource(R.drawable.style_for_card_time_orange);
                            break;
                        case 8:
                            timeCard.setBackgroundResource(R.drawable.style_for_card_time_gray);
                            break;
                        case 9:
                            timeCard.setBackgroundResource(R.drawable.style_for_card_time_teal);
                            break;
                        case 10:
                            timeCard.setBackgroundResource(R.drawable.style_for_card_time_brown);
                            break;
                        default:
                            timeCard.setBackgroundResource(R.drawable.style_for_card_time_gray);
                            break;
                    }

                    evName.setText(event.GetNameEvent());
                    evType.setText(event.GetTypeEvent());
                    evHost.setText(event.GetEventHost());
                    evLocation.setText(event.GetLocationEvent());
                    stTime.setText(simpleDateFormat.format(event.GetTimeEventStart().getTime())
                            .replace(':', '꞉'));
                    endTime.setText(simpleDateFormat.format(event.GetTimeEventEnd().getTime())
                            .replace(':', '꞉'));

                    if(event.getWeekId() == typeWeek){
                        ScheduleDemonstration.addView(container, cardId);
                        cardId++;
                    }
                }
            }
        }

    }

    private void saveSchedule(){
        _PB_progress.setVisibility(ProgressBar.VISIBLE);

        EventScheduleList eventList = FileIO.ReadScheduleEventListInFile
                ("Event_Schedule_List_1.bin", activity_choosing_teacher_schedule.this);
        TimeForNumberList timeList = FileIO.ReadTimeForNumberList("TimeListForZabGU.bin",
                activity_choosing_teacher_schedule.this);

        eventList.RemoveEventsByScheduleType(1);

        for(Schedule schedule: _Schedule){
            EventSchedule event = schedule.toEventScheduleForTeacher(timeList);
            event.setScheduleType(1);
            eventList.AppendEvent(event);
        }

        eventList.SetColorForIdenticalEvents();
        Collections.sort(eventList.GetEventsDayList());

        FileIO.WriteScheduleEventListInFile(eventList.GetEventsDayList(),
                "Event_Schedule_List_1.bin", activity_choosing_teacher_schedule.this);

        MainActivity.getInstance().ReloadViewPager_1();
        MainActivity.getInstance().ReloadViewPager_2();

        _PB_progress.setVisibility(ProgressBar.INVISIBLE);
        Toast.makeText(activity_choosing_teacher_schedule.this, "Schedule added successfully", Toast.LENGTH_SHORT).show();
    }
}