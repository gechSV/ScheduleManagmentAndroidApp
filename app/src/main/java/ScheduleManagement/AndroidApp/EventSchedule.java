package ScheduleManagement.AndroidApp;

import java.util.Date;

/**
 * Класс для управления событием расписания
 */
public class EventSchedule implements Comparable<EventSchedule>{

    private String _nameEvent; // Имя события (низвание пары, урока и т.п.)
    private String _typeEvent; // Тип события (лекция, практика, собрание, концерт и т.д.)
    private String _placeEvent; // Место проведения мероприятия (аудитория, улица, корпус)
    private String _teacherName; // Имя руководителя мероприятия
    private Date _timeEventBegin; // Время начала события
    private Date _timeEventEnd; // Время окончания события

    /**
     * Конструктор без параметров
     */
    public EventSchedule(){
        this._timeEventBegin = new Date();
        this._timeEventEnd = new Date();
    }

    /**
     * Метод интерфейса Comparable для сортировки списка
     * @param o обьект EventSchedule
     * @return > 0, < 0, = 0
     */
    @Override
    public int compareTo(EventSchedule o){
        return (int)(this.GetTimeEventBeginLong() - o.GetTimeEventBeginLong());
    }

    // Методы
    // Имя события
    public void SetNameEvent(String _nameEvent) {
        this._nameEvent = _nameEvent;
    }
    public String GetNameEvent() {
        return _nameEvent;
    }

    // Тип мероприятия
    public void SetTypeEvent(String _typeEvent) {
        this._typeEvent = _typeEvent;
    }
    public String GetTypeEvent() {
        return _typeEvent;
    }

    // Место проведения события
    public void SetPlaceEvent(String _placeEvent) {
        this._placeEvent = _placeEvent;
    }
    public String GetPlaceEvent() {
        return _placeEvent;
    }

    /**
     * Присвоение имени руководителя
     * @param name имя
     */
    public void SetTeacherName(String name){
        this._teacherName = name;
    }

    /**
     * Получить имя руководителя мероприятия
     * @return String
     */
    public String GetTeacherName(){
        return this._teacherName;
    }

    // Время начала событитя
    /**
     * Установка времени начала события
     * @param timeEventBegin type Date
     */
    public void SetTimeEventBegin(Date timeEventBegin) {
        if (timeEventBegin.getTime() > 86399999L){
            throw new Error("Incorrect format of the number _timeEventBegin");
        }
        else {
            this._timeEventBegin = timeEventBegin;
        }
    }

    /**
     * Установка времени начала события
     * @param h Часы
     * @param m Минуты
     */
    public void SetTimeEventBegin(int h, int m) {
        if ((h < 0) || (h > 23)){
            throw new Error("Incorrect format of the number h");
        }
        else if ((m < 0) || (m > 59)){
            throw new Error("Incorrect format of the number m");
        }
        else{
            this._timeEventBegin.setTime(h * 3600000 + m * 60000);
        }
    }

    /**
     * Установка времени начала события
     * @param t количество миллисекунд
     */
    public void SetTimeEventBegin(long t){
        if( (t < 0L) || (t > 86399999L) ){
            throw new Error("Incorrect format of the number t");
        }
        else{
            this._timeEventBegin.setTime(t);
        }
    }

    /**
     * Возвращает время начала события
     * @return Экземпляр класса Date
     */
    public Date GetTimeEventBegin() {
        return _timeEventBegin;
    }

    public long GetTimeEventBeginLong(){
        return _timeEventBegin.getTime();
    }

    // Время окончания события
    public void SetTimeEventEnd(Date timeEventEnd) {
        if (timeEventEnd.getTime() > 86399999L){
            throw new Error("Incorrect format of the number timeEventEnd");
        }
        else {
            this._timeEventBegin = timeEventEnd;
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
            this._timeEventEnd.setTime(h * 3600000 + m * 60000);
        }
    }

    /**
     * Установка времени окончания события
     * @param t количество миллисекунд
     */
    public void SetTimeEventEnd(long t){
        if( (t < 0L) || (t > 86399999L) ){
            throw new Error("Incorrect format of the number t");
        }
        else{
            this._timeEventEnd.setTime(t);
        }
    }

    /**
     * Возврвщает время окончания события
     * @return Экземпляр класса Date
     */
    public Date GetTimeEventEnd() {
        return _timeEventEnd;
    }
}
