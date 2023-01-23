package ScheduleManagement.AndroidApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Класс-список для хранения объектов EventSchedule
 */
public class EventScheduleList implements Serializable {

    private static final long serialVersionUID = -5971595872135145203L;

    private ArrayList<EventSchedule> _eventScheduleList; // Список объектов EventSchedule

    // Конструкторы
    /**
     * Конструктор без параметров
     */
    public EventScheduleList(){
        this._eventScheduleList = new ArrayList<EventSchedule>();
    }

    public EventScheduleList(ArrayList<EventSchedule> ListSchedule){
        this._eventScheduleList = ListSchedule;
    }

    // Методы
    /**
     * Перезаписать список
     * @param _eventsDay Список EventSchedule
     */
    public void OverwritingEventDaysList(ArrayList<EventSchedule> _eventsDay) {
        this._eventScheduleList = _eventsDay;
    }
    public ArrayList<EventSchedule> GetEventsDayList() {
        return this._eventScheduleList;
    }

    /**
     * Добавить элемент в список eventsDayList
     * @param _EventSchedule Событие
     */
    public void AppendEvent(EventSchedule _EventSchedule){
        this._eventScheduleList.add(_EventSchedule);
    }

    /**
     * Получить событие расписания по индексу
     * @param index индекс
     * @return Объект класса EventSchedule
     */
    public EventSchedule GetEventsDayByIndex(int index){
        return this._eventScheduleList.get(index);
    }

    /**
     * Удалить событие по индексу
     * @param index индекс события
     */
    public void  RemoveEventsDayByIndex(int index){
        this._eventScheduleList.remove(index);
    }

    /**
     * Удаление всего списка событий
     */
    public void  ClearEventsDayList(){
        this._eventScheduleList.clear();
    }

    public Boolean SortEventList(){
        Collections.sort(_eventScheduleList);
        return true;
    }

    /**
     * Возвращает список событий по индексу дня недели
     * @param i - 0-пн, 1-вт, 2-ср, 3-чт, 4-пт, 5-сб, 6-вт
     * @return Список событий
     */
    public ArrayList<EventSchedule> GetEventByDayWeek(int i){
        ArrayList<EventSchedule> returnEvent = new ArrayList<>();
        boolean[] weekDay;
        for(EventSchedule event : _eventScheduleList){
            weekDay = event.GetWeekDayPeek();
            if(weekDay[i]){
                returnEvent.add(event);
            }
        }
        return returnEvent;
    }

    // TODO: удаление элемента, отчистка списка, сортировка по датам класса EventSchedule
    //  и добавить день недели или иную дату. ПРОТЕСТИРОВАТЬ РАБОТУ!!!!!
}
