package ScheduleManagement.AndroidApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    /**
     * Конструктор с параметрами
     * @param ListSchedule список событий
     */
    public EventScheduleList(ArrayList<EventSchedule> ListSchedule){
        this._eventScheduleList = ListSchedule;
        // проставляем id
        SetAllId();
    }

    // Методы
    /**
     * Перезаписать список
     * @param _eventsDay Список EventSchedule
     */
    public void OverwritingEventDaysList(ArrayList<EventSchedule> _eventsDay) {
        this._eventScheduleList = _eventsDay;
        SetAllId();
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
        this._eventScheduleList.get(_eventScheduleList.size()-1).SetId(this.GetLastId() + 1);
    }

    /**
     * Получить событие расписания по индексу
     * @param id индекс
     * @return Объект класса EventSchedule
     */
    public EventSchedule GetEventsDayById(int id){
        // если id и index не совпали то ищем вручную
        // Я бы мог написать сложный алгоритм поиска, а не просто перебор по порядку
        // Но не думаю, что в расписании шкило или студентика будет более 30 записей в списке
        // так что рисуем перебор и не выёбываемся, китайский сяоми справится ещё до того, как
        // пользователь захочет удалить событие
        for(int i = 0; i <= this._eventScheduleList.size();  i++){
            if(this._eventScheduleList.get(i).GetId() == id){
                return this._eventScheduleList.get(i);
            }
        }
        return null;
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
     * Удалить событие по id
     * @param id id события
     */
    public void  RemoveEventsDayById(int id){
        // Я бы мог написать сложный алгоритм поиска, а не просто перебор по порядку
        // Но не думаю, что в расписании шкило или студентика будет более 30 записей в списке
        // так что рисуем перебор и не выёбываемся, китайский сяоми справится ещё до того, как
        // пользователь захочет удалить событие
        for(int i = 0; i <= this._eventScheduleList.size();  i++) {
            if (this._eventScheduleList.get(i).GetId() == id) {
                this.RemoveEventsDayByIndex(i);
                return;
            }
        }
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

    /**
     * Возвращает список событий по индексу дня недели
     * @param weekDayId - 0-пн, 1-вт, 2-ср, 3-чт, 4-пт, 5-сб, 6-вт
     * @return Список событий
     */
    public boolean GetEventByDayWeekAndWeekType(int weekDayId, int weekDayType){

        ArrayList<EventSchedule> returnEvent = new ArrayList<>();
        boolean[] weekDay;

        for(EventSchedule event : _eventScheduleList){
            weekDay = event.GetWeekDayPeek();
            if(weekDay[weekDayId]){
                returnEvent.add(event);
            }
        }

        for(EventSchedule event: returnEvent){
            if(event.getWeekId() == weekDayType){
                return true;
            }
        }

        return false;
    }

    /**
     * Получить размер списка событий
     * @return размер списка
     */
    public int size(){
        return _eventScheduleList.size();
    }

//    private void SetAllId(){
//        int counterId = 0;
//        for (EventSchedule event: this._eventScheduleList){
//            event.SetId(counterId);
//            counterId++;
//        }
//    }

    private void SetAllId()
    {
        int counterId = 0;
        for (EventSchedule event: this._eventScheduleList){
            if(event.GetId() <= 0){
                event.SetId(this.GetLastId() + 1);
            }
        }
    }

    /**
     * Получить наибольший id в списке
     * @return maxId
     */
    private int GetLastId(){
        int maxId = 0;
        for (EventSchedule event: this._eventScheduleList){
            if(maxId < event.GetId()){
                maxId = event.GetId();
            }
        }
        return maxId;
    }

    public EventScheduleList addAll(EventScheduleList a){
        if(a != null){
            this.GetEventsDayList().addAll(a.GetEventsDayList());
            return this;
        }
        else
        {
            throw new Error("The argument _typeEvent cannot be null.");
        }
    }

    /**
     * Получить список, который содержит только элементы верхней, либо нижней недели
     * @param typeId
     */
    public EventScheduleList GetEventScheduleByWeekType(int typeId){
        EventScheduleList newEventList = new EventScheduleList();

        for(EventSchedule event: _eventScheduleList){
            if(event.getWeekId() == typeId){
                newEventList.AppendEvent(event);
            }
        }

        return newEventList;
    }

    /**
     * Ужалить из списка все элементы имеющие ShceduleTypeId
     * @param ScheduleTypeId
     */
    public void RemoveEventsByScheduleType(int ScheduleTypeId){
        _eventScheduleList.removeIf(event -> event.getScheduleType() == ScheduleTypeId);
    }

    /**
     * Выставляет цвета у событий имеющих одно название
     */
    public void SetColorForIdenticalEvents(){
        String nameBuf;
        Set<String> nameListSet = new HashSet<>();
        ArrayList<String> nameList = new ArrayList<>();

        for(EventSchedule event: _eventScheduleList){
            nameBuf = event.GetNameEvent();
            if(!nameListSet.add(nameBuf)){
                nameList.add(nameBuf);
            }
        }

        int color = 0;
        for(String name: nameListSet){
            color++;

            if(color > 10){
                color=1;
            }

            for(EventSchedule event: _eventScheduleList){
                if(Objects.equals(name, event.GetNameEvent())){
                    event.SetColorForEvent(color);
                }
            }

        }

    }

    public int getColorEventByName(String name){
        for(EventSchedule event: _eventScheduleList){
            if(Objects.equals(event.GetNameEvent(), name)){
                return event.GetColorForEvent();
            }
        }
        return -1;
    }
}
