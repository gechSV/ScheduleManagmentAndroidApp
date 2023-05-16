package ScheduleManagement.AndroidApp.activity_controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.res.ColorStateList;
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
import java.util.Objects;

import ScheduleManagement.AndroidApp.EventSchedule;
import ScheduleManagement.AndroidApp.EventScheduleList;
import ScheduleManagement.AndroidApp.FileIO;
import ScheduleManagement.AndroidApp.HintBuilder;
import ScheduleManagement.AndroidApp.Note;
import ScheduleManagement.AndroidApp.NoteList;
import ScheduleManagement.AndroidApp.R;
import es.dmoral.toasty.Toasty;

public class ActivityAddNote extends AppCompatActivity implements View.OnClickListener {

    private CardView _CV_ActionCon, _buttonSaveNote, _buttonBack;
    private EditText _ET_note_text, _ET_noteName;
    private FlowLayout _LL_schedule_name_button;
    private EventScheduleList eventList;
    private final String FILE_NAME_EVENT_LIST_1 = "Event_Schedule_List_1.bin";
    private final String FILE_NAME_NOTE_LIST = "Node_List.bin";

    private ArrayList<CardView> buttonList = new ArrayList<>();
    private ArrayList<ImageView> imgList = new ArrayList<>();// Для сброса чекбоксов
    private ArrayList<String>  textButtonList = new ArrayList<>();
    private String currentEventName = "";
    private int currentColorId = -1;

    ColorStateList colorStateListRed = ColorStateList.valueOf(0xFFFF9494);
    ColorStateList colorStateListOrange = ColorStateList.valueOf(0xFFFFCCBC);

    private boolean editFlag = false;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        _CV_ActionCon = (CardView)findViewById(R.id.action_con);
        _CV_ActionCon.setBackgroundResource(R.drawable.menu_background);

        _buttonSaveNote = (CardView) findViewById(R.id.buttonSaveNote);
        _buttonSaveNote.setBackgroundResource(R.drawable.style_for_button_setting);
        _buttonSaveNote.setOnClickListener(this);

        _buttonBack = (CardView)findViewById(R.id.backButton);
        _buttonBack.setBackgroundResource(R.drawable.style_for_button_setting);
        _buttonBack.setOnClickListener(this);

        _LL_schedule_name_button = (FlowLayout) findViewById(R.id.ll_schedule_name_button);

        _ET_noteName = (EditText)findViewById(R.id.note_name);
        _ET_note_text = (EditText)findViewById(R.id.note_text);

        this.buttonScheduleNameBuild();

        Bundle bundle = getIntent().getExtras();
        editFlag = bundle.getBoolean("editFlag");
        String currentNoteName = bundle.getString("currentNoteName");

        if(editFlag){
            noteId = bundle.getInt("nodeId");

            NoteList noteList = FileIO.ReadNoteListInFile(FILE_NAME_NOTE_LIST, this);
            Note note = noteList.getNoteById(noteId);

            _ET_noteName.setText(note.getName());
            _ET_note_text.setText(note.getText());

            for(int i = 0; i < buttonList.size(); i++){
                if(textButtonList.get(i).equals(note.getEventName())){
                    buttonList.get(i).performClick();
                }
            }
        }

        if(currentNoteName != null){
            for(int i = 0; i < buttonList.size(); i++){
                if(textButtonList.get(i).equals(currentNoteName)){
                    buttonList.get(i).performClick();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.backButton):
                this.finish();
                break;
            case(R.id.buttonSaveNote):
                this.SaveNote();
                break;
        }
    }

    private void buttonScheduleNameBuild(){

        this.buttonScheduleNoCategory();

        try {
            eventList = FileIO.ReadScheduleEventListInFile(FILE_NAME_EVENT_LIST_1,
                    ActivityAddNote.this);
        }
        catch (Error err){
            Toasty.error(this, Objects.requireNonNull(err.getMessage()),
                    Toast.LENGTH_SHORT, true).show();
        }

        ArrayList<String> eventNames = HintBuilder.getHintForNameByStr("", eventList);


        for(String name: eventNames){
            LinearLayout llPattern = (LinearLayout)LayoutInflater
                    .from(this)
                    .inflate(R.layout.button_pattern_for_note_choice, null);

            CardView btnPattern =  (CardView)llPattern.findViewById(R.id.button);
            TextView textButton = (TextView)llPattern.findViewById(R.id.text_button);
            ImageView checkImage = (ImageView)llPattern.findViewById(R.id.check_image);

            textButton.setText(name);
            btnPattern.setBackgroundResource(getBackgroundResourceByName(name));
            checkImage.setVisibility(View.GONE);

            _LL_schedule_name_button.addView(llPattern);

            final int  btnId = View.generateViewId();
            final int txtBtn = View.generateViewId();
            final int imgId = View.generateViewId();
            final String eventName = name;
            final int colorId = getIdColorByNameEvent(name);

            btnPattern.setId(btnId);
            textButton.setId(txtBtn);
            checkImage.setId(imgId);

            CardView btnOnClick = (CardView)findViewById(btnId);
            TextView textButtonOnClick = (TextView)findViewById(txtBtn);
            ImageView imgOnClick = (ImageView)findViewById(imgId);

            imgList.add(imgOnClick);
            buttonList.add(btnOnClick);
            textButtonList.add(name);
            btnOnClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(ImageView img: imgList){
                        img.setVisibility(View.GONE);
                    }
                    imgOnClick.setVisibility(View.VISIBLE);
                    currentEventName = eventName;
                    currentColorId = colorId;
                }
            });
        }
    }

    private void buttonScheduleNoCategory(){
        LinearLayout llPattern = (LinearLayout)LayoutInflater
                .from(this)
                .inflate(R.layout.button_pattern_for_note_choice, null);

        CardView btnPattern =  (CardView)llPattern.findViewById(R.id.button);
        TextView textButton = (TextView)llPattern.findViewById(R.id.text_button);
        ImageView checkImage = (ImageView)llPattern.findViewById(R.id.check_image);

        textButton.setText("No category");
        btnPattern.setBackgroundResource(R.drawable.style_for_hintbutton);
        checkImage.setVisibility(View.VISIBLE);

        _LL_schedule_name_button.addView(llPattern);

        final int  btnId = View.generateViewId();
        final int imgId = View.generateViewId();

        btnPattern.setId(btnId);
        checkImage.setId(imgId);

        CardView btnOnClick = (CardView)findViewById(btnId);
        ImageView imgOnClick = (ImageView)findViewById(imgId);

        imgList.add(imgOnClick);

        btnOnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ImageView img: imgList){
                    img.setVisibility(View.GONE);
                }
                imgOnClick.setVisibility(View.VISIBLE);
                currentEventName = "";
            }
        });
    }

    private int getBackgroundResourceByName(String name){
        int idColor = getIdColorByNameEvent(name);

        switch (idColor){
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

    private int getIdColorByNameEvent(String name){
        for(EventSchedule event: eventList.GetEventsDayList()){
            if(Objects.equals(event.GetNameEvent(), name)){
                return event.GetColorForEvent();
            }
        }

        return -1;
    }

    private void SaveNote(){

        _ET_noteName.setBackgroundTintList(colorStateListOrange);

        NoteList noteList = FileIO.ReadNodeListFromFile(FILE_NAME_NOTE_LIST, this);
        Note newNote = new Note();

        String name = _ET_noteName.getText().toString();
        String text = _ET_note_text.getText().toString();

        newNote.setName(name);

        if(text.length() != 0){
            newNote.setText(text);
        }
        else{
            _ET_note_text.setBackgroundTintList(colorStateListRed);
            return;
        }

        newNote.setEventName(currentEventName);
        newNote.setColorId(currentColorId);

        if(editFlag){
            for(Note note: noteList.getNoteList()){
                if(note.getId() == noteId){
                    note.setName(name);
                    note.setText(text);
                    note.setEventName(currentEventName);
                    note.setColorId(currentColorId);
                    FileIO.WriteNoteListInFile(noteList.getNoteList(), FILE_NAME_NOTE_LIST, this);
                    MainActivity.getInstance().reloadNoteDemo();
                }
            }
        }
        else{
            assert noteList != null;
            noteList.addNote(newNote);
            FileIO.WriteNoteListInFile(noteList.getNoteList(), FILE_NAME_NOTE_LIST, this);
        }

        this.finish();

    }


}