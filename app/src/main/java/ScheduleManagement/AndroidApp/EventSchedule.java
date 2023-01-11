package ScheduleManagement.AndroidApp;

import android.graphics.Color;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Класс для управления событием расписания
 */
public class EventSchedule implements Comparable<EventSchedule>, Serializable {

    private String _nameEvent; // Имя события (низвание пары, урока и т.п.)
    private String _typeEvent; // Тип события (лекция, практика, собрание, концерт и т.д.)
    private String _EventLocation; // Место проведения мероприятия (аудитория, улица, корпус)
    private String _EventHost; // Имя руководителя мероприятия
    private Calendar _timeEventStart; // Время начала события
    private Calendar _timeEventEnd; // Время окончания события
    private Color _colorForEvent; // Цвет для отображения расписания

    /**
     * Конструктор без параметров
     */
    public EventSchedule(){
        this._timeEventStart = Calendar.getInstance();
        this._timeEventEnd = Calendar.getInstance();
    }


    /**
     * Метод интерфейса Comparable для сортировки списка
     * @param o обьект EventSchedule
     * @return > 0, < 0, = 0
     */
    @Override
    public int compareTo(EventSchedule o){
        return (int)(this.GetTimeEventBeginMs() - o.GetTimeEventBeginMs());
    }

    // Методы
    // Имя события
    public void SetNameEvent(String _nameEvent) {
        if (_nameEvent != null){
            this._nameEvent = _nameEvent;
        }
        else
        {
            throw new Error("The argument _nameEvent cannot be null.");
        }
    }
    public String GetNameEvent() {
        return _nameEvent;
    }

    // Тип мероприятия
    public void SetTypeEvent(String _typeEvent) {
        if (_typeEvent != null){
            this._typeEvent = _typeEvent;
        }
        else
        {
            throw new Error("The argument _typeEvent cannot be null.");
        }
    }

    public String GetTypeEvent() {
        return _typeEvent;
    }

    // Место проведения события
    public void SetEventLocation(String _placeEvent) {
        if (_placeEvent != null){
            this._EventLocation = _placeEvent;
        }
        else
        {
            throw new Error("The argument _placeEvent cannot be null.");
        }
    }

    public String GetPlaceEvent() {
        return _EventLocation;
    }

    /**
     * Присвоение имени руководителя
     * @param name имя
     */
    public void SetEventHost(String name){
        if (name != null){
            this._EventHost = name;
        }
        else
        {
            throw new Error("The argument name cannot be null.");
        }
    }

    /**
     * Получить имя руководителя мероприятия
     * @return String
     */
    public String GetTeacherName(){
        return this._EventHost;
    }

    // Время начала событитя
    /**
     * Установка времени начала события
     * @param timeEventStart type Calendar
     */
    public void SetTimeEventStart(Calendar timeEventStart) {
        if (timeEventStart == null){
            throw new Error("The argument timeEventStart cannot be null.");
        }
        else {
            this._timeEventStart = timeEventStart;
        }
    }

    /**
     * Установка времени начала события
     * @param h Часы
     * @param m Минуты
     */
    public void SetTimeEventStart(int h, int m) {
        if ((h < 0) || (h > 23)){
            throw new Error("Incorrect format of the number h");
        }
        else if ((m < 0) || (m > 59)){
            throw new Error("Incorrect format of the number m");
        }
        else{
            this._timeEventStart.set(Calendar.HOUR_OF_DAY, h);
            this._timeEventStart.set(Calendar.MINUTE, m);
        }
    }

    /**
     * Возвращает время начала события
     * @return Экземпляр класса Calendar
     */
    public Calendar GetTimeEventBegin() {
        return _timeEventStart;
    }

    /**
     * Получить Миллисекунды
     * @return Long
     */
    public long GetTimeEventBeginMs(){
        return _timeEventStart.getTimeInMillis();
    }

    // Время окончания события
    public void SetTimeEventEnd(Calendar timeEventEnd) {
        if (timeEventEnd == null){
            throw new Error("The argument timeEventEnd cannot be null.");
        }
        else {
            this._timeEventStart = timeEventEnd;
        }
    }

    /**
     * Утановка времени окончания события
     * @param h Часы
     * @param m Минуты
     */
    public void SetTimeEventEnd(int h, int m) {
        if ((h < 0) || (h > 23)){
            throw new Error("Incorrect format of the number h");
        }
        else if ((m < 0) || (m > 59)){
            throw new Error("Incorrect format of the number m");
        }
        else{
            this._timeEventEnd.set(Calendar.HOUR_OF_DAY, h);
            this._timeEventEnd.set(Calendar.MINUTE, m);
        }
    }

    /**
     * Возврвщает время окончания события
     * @return Экземпляр класса Date
     */
    public Calendar GetTimeEventEnd() {
        return _timeEventEnd;
    }

    /**
     * Получить Миллисекунды
     * @return Long
     */
    public long GetTimeEventEndMs(){
        return _timeEventEnd.getTimeInMillis();
    }

    public void SetColorForEvent(Color _colorForEvent) {
        if (_colorForEvent != null){
            this._colorForEvent = _colorForEvent;
        }
        else{
            throw new Error("The argument _colorForEvent cannot be null.");
        }
    }

    public Color GetColorForEvent() {
        return _colorForEvent;
    }
}
