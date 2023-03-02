package ScheduleManagement.AndroidApp;

import java.io.Serializable;
import java.util.ArrayList;

public class TimeForNumberList implements Serializable {

    private static final long serialVersionUID = 5353514434465599008L;

    private ArrayList<TimeForNumber> _timeForNumberList;

    public TimeForNumberList(){
        _timeForNumberList = new ArrayList<>();
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
}
