package ScheduleManagement.AndroidApp;

import java.util.ArrayList;
import java.util.Locale;

public final class HintBuilder {

    /**
     * Получить список строк, содержащий наименования событий, содержащих подстроку nameStr
     * @param nameStr строка по которой производится поиск
     * @param eventList список событий в котором ищем совпадения
     * @return список строк - наименований
     */
    public static  ArrayList<String> getHintForNameByStr(String nameStr, EventScheduleList eventList){
        ArrayList<String> newNameList = new ArrayList<>();
        String bufferStr_1 = nameStr.toUpperCase(), bufferStr_2;

        for(EventSchedule event: eventList.GetEventsDayList()){
            if (event.GetNameEvent() != null){
                bufferStr_2 = event.GetNameEvent().toUpperCase();

                if(bufferStr_2.contains(bufferStr_1))
                {
                    newNameList.add(event.GetNameEvent());
                }
            }
        }
        return RemoveDuplicate(newNameList);
    }

    /**
     * Получить список строк, содержащий наименования типов событий, содержащих подстроку typeStr
     * @param typeStr строка по которой производится поиск
     * @param eventList список событий в котором ищем совпадения
     * @return список строк - наименований типов
     */
    public static ArrayList<String> getHintForTypeByStr(String typeStr, EventScheduleList eventList){
        ArrayList<String> newTypeList = new ArrayList<>();
        String buffStr_1 = typeStr.toUpperCase(), buffStr_2;

        for(EventSchedule event: eventList.GetEventsDayList()){
            if(event.GetTypeEvent() != null){
                buffStr_2 = event.GetTypeEvent().toUpperCase();

                if(buffStr_2.contains(buffStr_1)){
                    newTypeList.add(event.GetTypeEvent());
                }
            }
        }

        return (newTypeList);
    }

    /**
     * Получить список строк, содержащий наименования типов событий, содержащих подстроку locationStr
     * @param locationStr строка по которой производится поиск
     * @param eventList список событий в котором ищем совпадения
     * @return список строк - наименований типов
     */
    public static ArrayList<String> getHintForLocationByStr(String locationStr, EventScheduleList eventList){
        ArrayList<String> newTypeList = new ArrayList<>();
        String buffStr_1 = locationStr.toUpperCase(), buffStr_2;

        for(EventSchedule event: eventList.GetEventsDayList()){
            if(event.GetLocationEvent() != null){
                buffStr_2 = event.GetLocationEvent().toUpperCase();

                if(buffStr_2.contains(buffStr_1)){
                    newTypeList.add(event.GetLocationEvent());
                }
            }
        }

        return (newTypeList);
    }

    /**
     * Получить список строк, содержащий наименования типов событий, содержащих подстроку hostStr
     * @param hostStr строка по которой производится поиск
     * @param eventList список событий в котором ищем совпадения
     * @return список строк - наименований типов
     */
    public static ArrayList<String> getHintForHostByStr(String hostStr, EventScheduleList eventList){
        ArrayList<String> newTypeList = new ArrayList<>();
        String buffStr_1 = hostStr.toUpperCase(), buffStr_2;

        for(EventSchedule event: eventList.GetEventsDayList()){
            if(event.GetEventHost() != null){
                buffStr_2 = event.GetEventHost().toUpperCase();

                if(buffStr_2.contains(buffStr_1)){
                    newTypeList.add(event.GetEventHost());
                }
            }
        }

        return (newTypeList);
    }

    private static ArrayList<String> RemoveDuplicate(ArrayList<String> arrayList){
        ArrayList<String> list = new ArrayList<>();
        for(String elem: arrayList){
            if(!list.contains(elem)){
                list.add(elem);
            }
        }
        return list;
    }
}
