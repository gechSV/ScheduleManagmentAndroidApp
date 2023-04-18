package ScheduleManagement.AndroidApp;

import java.io.Serializable;
import java.util.Calendar;

public class TimeForNumber implements Serializable, Comparable<TimeForNumber>{

    private static final long serialVersionUID = 4295071865382783177L;
    private Calendar _startTime;
    private Calendar _endTime;
    // тип времени 0 - используется для пользовательского паттерна времени
    // тип времени 1 - используется для автоматического добавления расписания
    private int _typeTime;


    public TimeForNumber(int typeTime){
        this._startTime = CalendarConstructor.GetNewCalendar();
        this._endTime = CalendarConstructor.GetNewCalendar();
        this.SetTypeTime(typeTime);
    }

    public TimeForNumber(Calendar newTimeStart, Calendar newTimeEnd, int typeTime){
        if((newTimeStart != null) && (newTimeEnd != null)){
            this._startTime = newTimeStart;
            this._endTime = newTimeEnd;
            this.SetTypeTime(typeTime);
        }
        else {
            throw new Error("param == null");
        }

    }

    public void SetStartTime(Calendar _startTime) {
        if (_startTime != null) {
            this._startTime = _startTime;
        }
        else {
            throw new Error("_startTime == null");
        }
    }

    public Calendar GetStartTime() {
        return _startTime;
    }

    public void SetEndTime(Calendar _endTime) {
        if(_endTime != null){
            this._endTime = _endTime;
        }
        else {
            throw new Error("_endTime == null");
        }
    }
    public Calendar GetEndTime() {
        return _endTime;
    }

    public long GetTimeStartMs(){
        return _startTime.getTimeInMillis();
    }

    public void SetTypeTime(int type){
        this._typeTime = type;
    }

    public int GetTypeTime(){
        return this._typeTime;
    }

     /**
     * Метод интерфейса Comparable для сортировки списка
     * @param o объект класса TimeForNumber
     * @return > 0, < 0, = 0
     */
    @Override
    public int compareTo(TimeForNumber o) {
        return (int)(this.GetTimeStartMs() - o.GetTimeStartMs());
    }
}
