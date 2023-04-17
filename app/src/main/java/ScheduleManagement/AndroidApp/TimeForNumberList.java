package ScheduleManagement.AndroidApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeForNumberList implements Serializable {

    private static final long serialVersionUID = 5353514434465599008L;

    private ArrayList<TimeForNumber> _timeForNumberList;

    public TimeForNumberList(){
        _timeForNumberList = new ArrayList<>();
    }

    public TimeForNumberList(ArrayList<TimeForNumber> timeList){
        if(timeList != null){
            _timeForNumberList = timeList;
        }
        else
        {
            throw new Error("_timeList == null");
        }
    }

    public void SetTimeForNumberList(ArrayList<TimeForNumber> _timeForNumberList) {
        if (_timeForNumberList != null){
            this._timeForNumberList = _timeForNumberList;
        }
        else {
            throw new Error("_timeForNumberList == null");
        }
    }

    public ArrayList<TimeForNumber> GetTimeForNumberList() {
        return _timeForNumberList;
    }

    public void AddTime(TimeForNumber timeForNumber){
        if(timeForNumber != null){
            this._timeForNumberList.add(timeForNumber);
        }
        else{
            throw new Error("timeForNumber == null");
        }
    }

    public TimeForNumber GetTimeByIndex(int index){
        if(index >= 0){
            return _timeForNumberList.get(index);
        }
        else{
            throw new Error("incorrect index");
        }
    }

    public int length(){
        return this._timeForNumberList.size();
    }

    public void SetStartTimeById(Calendar time, int id){
        if(time != null){
            this._timeForNumberList.get(id).SetStartTime(time);
        }
        else{
            throw new Error("time == null");
        }
    }

    public Calendar GetStartTimeById(int id){
        return this._timeForNumberList.get(id).GetStartTime();
    }

    public void SetEndTimeById(Calendar time, int id){
        if(time != null){
            this._timeForNumberList.get(id).SetEndTime(time);
        }
        else{
            throw new Error("time == null");
        }
    }

    public Calendar GetEndTimeById(int id){
        return this._timeForNumberList.get(id).GetEndTime();
    }

    public void FirstSetTimeList(){
        Calendar timeSt1 = new GregorianCalendar();
        Calendar timeEnd1 = new GregorianCalendar();

        // 1 пара
        timeSt1.set(Calendar.HOUR_OF_DAY, 8);
        timeSt1.set(Calendar.MINUTE, 30);
        timeEnd1.set(Calendar.HOUR_OF_DAY, 10);
        timeEnd1.set(Calendar.MINUTE, 5);
        this._timeForNumberList.add(new TimeForNumber(timeSt1, timeEnd1));

        // 2 пара
        Calendar timeSt2 = new GregorianCalendar();
        Calendar timeEnd2 = new GregorianCalendar();
        timeSt2.set(Calendar.HOUR_OF_DAY, 10);
        timeSt2.set(Calendar.MINUTE, 15);
        timeEnd2.set(Calendar.HOUR_OF_DAY, 11);
        timeEnd2.set(Calendar.MINUTE, 50);
        this._timeForNumberList.add(new TimeForNumber(timeSt2, timeEnd2));

        // 3 пара
        Calendar timeSt3 = new GregorianCalendar();
        Calendar timeEnd3 = new GregorianCalendar();
        timeSt3.set(Calendar.HOUR_OF_DAY, 12);
        timeSt3.set(Calendar.MINUTE, 0);
        timeEnd3.set(Calendar.HOUR_OF_DAY, 13);
        timeEnd3.set(Calendar.MINUTE, 35);
        this._timeForNumberList.add(new TimeForNumber(timeSt3, timeEnd3));

        // 4 пара
        Calendar timeSt4 = new GregorianCalendar();
        Calendar timeEnd4 = new GregorianCalendar();
        timeSt4.set(Calendar.HOUR_OF_DAY, 14);
        timeSt4.set(Calendar.MINUTE, 5);
        timeEnd4.set(Calendar.HOUR_OF_DAY, 15);
        timeEnd4.set(Calendar.MINUTE, 40);
        this._timeForNumberList.add(new TimeForNumber(timeSt4, timeEnd4));

        // 5 пара
        Calendar timeSt5 = new GregorianCalendar();
        Calendar timeEnd5 = new GregorianCalendar();
        timeSt5.set(Calendar.HOUR_OF_DAY, 15);
        timeSt5.set(Calendar.MINUTE, 50);
        timeEnd5.set(Calendar.HOUR_OF_DAY, 17);
        timeEnd5.set(Calendar.MINUTE, 25);
        this._timeForNumberList.add(new TimeForNumber(timeSt5, timeEnd5));

        // 6 пара
        Calendar timeSt6 = new GregorianCalendar();
        Calendar timeEnd6 = new GregorianCalendar();
        timeSt6.set(Calendar.HOUR_OF_DAY, 17);
        timeSt6.set(Calendar.MINUTE, 35);
        timeEnd6.set(Calendar.HOUR_OF_DAY, 19);
        timeEnd6.set(Calendar.MINUTE, 10);
        this._timeForNumberList.add(new TimeForNumber(timeSt6, timeEnd6));
    }
}
