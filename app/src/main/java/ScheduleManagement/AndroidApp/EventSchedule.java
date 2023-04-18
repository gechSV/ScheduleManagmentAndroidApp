package ScheduleManagement.AndroidApp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Класс для управления событием расписания
 */
public class EventSchedule implements Comparable<EventSchedule>, Serializable {

    private static final long serialVersionUID = 6024437629835836202L;
    private int _id;
    private String _nameEvent; // Имя события (название пары, урока и т.п.)
    private String _typeEvent; // Тип события (лекция, практика, собрание, концерт и т.д.)
    private String _EventLocation; // Место проведения мероприятия (аудитория, улица, корпус)
    private String _EventHost; // Имя руководителя мероприятия
    private Calendar _timeEventStart; // Время начала мероприятия
    private Calendar _timeEventEnd; // Время окончания мероприятия
    // Массив флагов для хранения выбранного дня недели
    private boolean[] _weekDayPeek; // 0-пн, 1-вт, 2-ср, 3-чт, 4-пт, 5-сб, 6-вт
    private int _weekId;// id недели 1 - верхняя или 2 - нижняя и тд
    private int _scheduleType;// 0 - пользовательское, 1 - расписание, которое скачиваем с сервера
    private int _colorForEvent = 0; // Цвет для отображения в расписании

    /**
     * Конструктор без параметров
     */
    public EventSchedule(){
        this._id = -1;
        this._timeEventStart = CalendarConstructor.GetNewCalendar();
        this._timeEventEnd = CalendarConstructor.GetNewCalendar();
        _weekDayPeek = new boolean[7];
        Arrays.fill(_weekDayPeek, false);
    }


    /**
     * Метод интерфейса Comparable для сортировки списка
     * @param o объект класса EventSchedule
     * @return > 0, < 0, = 0
     */
    @Override
    public int compareTo(EventSchedule o){
        return (int)(this.GetTimeEventStartMs() - o.GetTimeEventStartMs());
    }

    //  Индивидуальный идентификатор события
    /**
     * Установить id
     * @param id - индивидуальный идентификатор события
     */
    public void SetId(int id){
        if(id >= 0){
            this._id = id;
        }
        else
        {
            throw new Error("Error: _id < 0");
        }
    }

    /**
     * Получить id
     * @return индивидуальный идентификатор события
     */
    public int GetId(){
        return this._id;
    }

    // Методы
    // Имя события
    public void SetNameEvent(String _nameEvent) {
        if (_nameEvent != null){
            this._nameEvent = _nameEvent;
        }
        else
        {
            throw new Error("The argument _nameEvent cannot be null.");
        }
    }
    public String GetNameEvent() {
        return _nameEvent;
    }

    // Тип мероприятия
    public void SetTypeEvent(String _typeEvent) {
        if (_typeEvent != null){
            this._typeEvent = _typeEvent;
        }
        else
        {
            throw new Error("The argument _typeEvent cannot be null.");
        }
    }

    public String GetTypeEvent() {
        return _typeEvent;
    }

    // Место проведения события
    public void SetLocationEvent(String _placeEvent) {
        if (_placeEvent != null){
            this._EventLocation = _placeEvent;
        }
        else
        {
            throw new Error("The argument _placeEvent cannot be null.");
        }
    }

    public String GetLocationEvent() {
        return _EventLocation;
    }

    /**
     * Присвоение имени руководителя
     * @param name имя
     */
    public void SetHostEvent(String name){
        if (name != null){
            this._EventHost = name;
        }
        else
        {
            throw new Error("The argument name cannot be null.");
        }
    }

    /**
     * Получить имя руководителя мероприятия
     * @return String
     */
    public String GetEventHost(){
        return this._EventHost;
    }

    // Время начала события
    /**
     * Установка времени начала события
     * @param timeEventStart type Calendar
     */
    public void SetTimeEventStart(Calendar timeEventStart) {
        if (timeEventStart == null){
            throw new Error("The argument timeEventStart cannot be null.");
        }
        else {
            // Устанавливаем одну и ту же дату для всех событий
            timeEventStart.set(Calendar.DAY_OF_WEEK, 1);
            timeEventStart.set(Calendar.MONTH, 1);
            timeEventStart.set(Calendar.YEAR, 1);
            this._timeEventStart = timeEventStart;
        }
    }

//    /**
//     * Установка времени начала события
//     * @param h Часы
//     * @param m Минуты
//     */
//    public void SetTimeEventStart(int h, int m) {
//        if ((h < 0) || (h > 23)){
//            throw new Error("Incorrect format of the number h");
//        }
//        else if ((m < 0) || (m > 59)){
//            throw new Error("Incorrect format of the number m");
//        }
//        else{
//            this._timeEventStart.set(Calendar.HOUR_OF_DAY, h);
//            this._timeEventStart.set(Calendar.MINUTE, m);
//        }
//    }

    /**
     * Возвращает время начала события
     * @return Экземпляр класса Calendar
     */
    public Calendar GetTimeEventStart() {
        return _timeEventStart;
    }

    /**
     * Получить Миллисекунды
     * @return Long
     */
    public long GetTimeEventStartMs(){
        return _timeEventStart.getTimeInMillis();
    }

    // Время окончания события
    public void SetTimeEventEnd(Calendar timeEventEnd) {
        if (timeEventEnd == null){
            throw new Error("The argument timeEventEnd cannot be null.");
        }
        else {
            timeEventEnd.set(Calendar.DAY_OF_WEEK, 1);
            timeEventEnd.set(Calendar.MONTH, 1);
            timeEventEnd.set(Calendar.YEAR, 1);
            this._timeEventEnd = timeEventEnd;
        }
    }

    /**
     * Утановка времени окончания события
     * @param h Часы
     * @param m Минуты
     */
//    public void SetTimeEventEnd(int h, int m) {
//        if ((h < 0) || (h > 23)){
//            throw new Error("Incorrect format of the number h");
//        }
//        else if ((m < 0) || (m > 59)){
//            throw new Error("Incorrect format of the number m");
//        }
//        else{
//            this._timeEventEnd.set(Calendar.HOUR_OF_DAY, h);
//            this._timeEventEnd.set(Calendar.MINUTE, m);
//        }
//    }

    /**
     * Возврвщает время окончания события
     * @return Экземпляр класса Date
     */
    public Calendar GetTimeEventEnd() {
        return _timeEventEnd;
    }

    /**
     * Получить Миллисекунды
     * @return Long
     */
    public long GetTimeEventEndMs(){
        return _timeEventEnd.getTimeInMillis();
    }

    public String GetStartTimeEvent(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return  (simpleDateFormat.format(_timeEventStart.getTime()));
    }

    public String GetEndTimeEvent(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return  (simpleDateFormat.format(_timeEventEnd.getTime()));
    }

    /**
     * Установить цвет
     * @param _colorForEvent 1 - Lime, 2 - Green, 3 - Blue, 4 - purple, 5 - pink, 6 - red, 7 - orange
     *                       8 - gray, 9 - Teal, 10 - brown
     */
    public void SetColorForEvent(int _colorForEvent) {
            this._colorForEvent = _colorForEvent;
    }

    /**
     * Получить цвет события
     * @return 1 - Lime, 2 - Green, 3 - Blue, 4 - purple, 5 - pink, 6 - red, 7 - orange
     *         8 - gray, 9 - black, 10 - brown
     */
    public int GetColorForEvent() {
        return _colorForEvent;
    }

    /**
     * Установка флагов дней недели 0-пн, 1-вт, 2-ср, 3-чт, 4-пт, 5-сб, 6-вт
     * @param weekDayPeek массив из 7 объектов boolean
     */
    public void SetWeekDayPeek(boolean[] weekDayPeek){
        if(weekDayPeek != null){
            this._weekDayPeek = weekDayPeek;
        }else {
            throw new Error("The argument weekDayPeek cannot be null.");
        }
    }

    /**
     * Установка флагов дней недели
     * @param dayNumb 0-пн, 1-вт, 2-ср, 3-чт, 4-пт, 5-сб, 6-вт
     */
    public void SetWeekDayPeek(int dayNumb){
        if((dayNumb >= 0) && (dayNumb < 7)){
            _weekDayPeek[dayNumb] = true;
        }
    }

    /**
     * Получить массив флагов
     * @return массив из 7 объектов boolean
     */
    public boolean[] GetWeekDayPeek(){
        return this._weekDayPeek;
    }

    /**
     * Получить идентификатор дня недели ( 0-пн, 1-вт, 2-ср, 3-чт, 4-пт, 5-сб, 6-вт)
     * @return идентификатор дня
     */
    public int GetWeekDayPeekId(){
        for(int i = 0; i < _weekDayPeek.length; i++){
            if(_weekDayPeek[i]){
                return i;
            }
        }
        return -1;
    }

    public void setWeekId(int weekId){
        this._weekId = weekId;
    }
    public int getWeekId(){
        return this._weekId;
    }

    public void setScheduleType(int scheduleTypeId){
        this._scheduleType = scheduleTypeId;
    }
    public int getScheduleType(){
        return this._scheduleType;
    }
}
