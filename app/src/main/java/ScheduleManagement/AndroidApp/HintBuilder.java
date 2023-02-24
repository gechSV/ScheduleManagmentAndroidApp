package ScheduleManagement.AndroidApp;

import java.util.ArrayList;
import java.util.Locale;

public final class HintBuilder {
    public static ArrayList<String> getHintForAllName(EventScheduleList eventList){
        ArrayList<String> bufferList = new ArrayList<String>();

        // Получаем список всех наименований событий
        for(EventSchedule event: eventList.GetEventsDayList()){
            bufferList.add(event.GetNameEvent());
        }
        // Убираем дубликаты, если они имеются
        return  RemoveDuplicate(bufferList);
    };

    public static  ArrayList<String> getHintForNameByStr(String nameStr, EventScheduleList eventList){
        ArrayList<String> newNameList = new ArrayList<>();
        String bufferStr_1 = nameStr.toUpperCase(), bufferStr_2;

        for(EventSchedule event: eventList.GetEventsDayList()){

            bufferStr_2 = event.GetNameEvent().toUpperCase();

            if(bufferStr_2.contains(bufferStr_1))
            {
                newNameList.add(event.GetNameEvent());
            }
        }

        return RemoveDuplicate(newNameList);
    }

    private static ArrayList<String> RemoveDuplicate(ArrayList<String> arrayList){
        ArrayList<String> list = new ArrayList<String>();
        for(String elem: arrayList){
            if(!list.contains(elem)){
                list.add(elem);
            }
        }
        return list;
    }
}
