package ScheduleManagement.AndroidApp;

import java.io.Serializable;

public class Note implements Serializable {
    private static final long serialVersionUID = 8824668725271032228L;
    private int _id;
    private String _name;
    private String _text; // содержание (текст) заметки
    private String _eventName; // Название события к которому относится заметка
    private int _colorId;

    public Note(){
        this._id = -1;
    };

    public void setId(int _id) {
        this._id = _id;
    }

    public int getId() {
        return this._id;
    }

    public void setName(String _name) {
        if (_name != null){
            this._name = _name;
        }
    }

    public String getName(){
        return this._name;
    }

    public void setText(String text) {
        if (text != null){
            this._text = text;
        }
    }

    public String getText(){
        return this._text;
    }

    public void setEventName(String eventName) {
        this._eventName = eventName;
    }

    public String getEventName(){
        return this._eventName;
    }

    public int getColorId(){
        return this._colorId;
    }

    public void setColorId(int colorId) {
            this._colorId = colorId;
    }
}
