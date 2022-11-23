package ScheduleManagement.AndroidApp;

import java.util.ArrayList;

public class ColorForEventList {
    private ArrayList<ColorForEvent> _colorList;

    public ColorForEventList(){
        this._colorList = new ArrayList<ColorForEvent>();
    }

    public void AppendColor(String stringHex){
        try{
            ColorForEvent setColot = new ColorForEvent(stringHex);
            this._colorList.add(setColot);
        }catch(Error e){
            throw new Error(e);
        }
    }

    public String GetColorByIndex(int index){
        return this._colorList.get(index).getColorHex();
    }

    public int Length(){
        return this._colorList.size();
        // Работает?
    }
}
