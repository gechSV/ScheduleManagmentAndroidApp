package ScheduleManagement.AndroidApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

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
}
