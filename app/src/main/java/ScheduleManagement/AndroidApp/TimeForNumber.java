package ScheduleManagement.AndroidApp;

import java.io.Serializable;
import java.util.Calendar;

public class TimeForNumber implements Serializable {

    private static final long serialVersionUID = 4295071865382783177L;
    private Calendar _startTime;
    private Calendar _endTime;

    public TimeForNumber(){
        _startTime = Calendar.getInstance();
        _endTime = Calendar.getInstance();
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
}
