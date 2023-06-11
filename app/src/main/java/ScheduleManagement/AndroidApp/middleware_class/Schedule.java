package ScheduleManagement.AndroidApp.middleware_class;

import java.util.ArrayList;

import ScheduleManagement.AndroidApp.EventSchedule;
import ScheduleManagement.AndroidApp.TimeForNumberList;

public class Schedule {
    private int WeekDayNumber;
    private int WeekType;
    private int EventNumber;
    private String Name;
    private String Type;
    private String Host;
    private String faculty;
    private String Location;
    private String Group;
    private long TimeStart = 0;
    private long TimeEnd = 0;

    public Schedule(){}

    static public ArrayList<Schedule> EventScheduleListToSchedule(ArrayList<EventSchedule> schedule){
        ArrayList<Schedule> newSchedule = new ArrayList<>();

        for(EventSchedule eventInArray: schedule){
            Schedule newEvent = new Schedule();
            newEvent.setWeekDayNumber(eventInArray.GetWeekDayPeekId());
            newEvent.setWeekType(eventInArray.getWeekId());
            newEvent.setTimeStart(eventInArray.GetTimeEventStartMs());
            newEvent.setTimeEnd(eventInArray.GetTimeEventEndMs());
            newEvent.setName(eventInArray.GetNameEvent());
            newEvent.setType(eventInArray.GetTypeEvent());
            newEvent.setHost(eventInArray.GetEventHost());
            newEvent.setLocation(eventInArray.GetLocationEvent());

            newSchedule.add(newEvent);
        }

        return newSchedule;
    }

    public void setWeekDayNumber(int weekDayNumber) {
        this.WeekDayNumber = weekDayNumber;
    }
    public int getWeekDayNumber() {
        return this.WeekDayNumber;
    }

    public void setWeekType(int weekType) {
        this.WeekType = weekType;
    }
    public int getWeekType() {return this.WeekType;}

    public void setEventNumber(int eventNumber) {
        this.EventNumber = eventNumber;
    }
    public int getEventNumber() {return this.EventNumber;}

    public void setName(String name) {
        this.Name = name;
    }
    public String getName() {
        return this.Name;
    }

    public void setType(String type) {
        this.Type = type;
    }
    public String getType() {
        return this.Type;
    }

    public void setHost(String host) {
        this.Host = host;
    }
    public String getHost() {
        return this.Host;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    public String getFaculty() {
        return this.faculty;
    }

    public void setLocation(String location) {
        this.Location = location;
    }
    public String getLocation() {
        return this.Location;
    }

    public void setGroup(String name){ this.Group = name; }
    public String getGroup() {
        return this.Group;
    }
    public EventSchedule toEventSchedule(TimeForNumberList timeList){
        EventSchedule newEvent = new EventSchedule();

        newEvent.setWeekId(WeekType);
        newEvent.SetWeekDayPeek(WeekDayNumber - 1);
        newEvent.SetColorForEvent(7);
        newEvent.SetNameEvent(this.Name);
        newEvent.SetTypeEvent(this.Type);
        newEvent.SetHostEvent(this.Host);
        newEvent.SetLocationEvent(this.Location);
        newEvent.SetTimeEventStart(timeList.GetStartTimeById(this.EventNumber - 1));
        newEvent.SetTimeEventEnd(timeList.GetEndTimeById(this.EventNumber - 1));
        return newEvent;
    }

    public EventSchedule toEventScheduleForTeacher(TimeForNumberList timeList){
        EventSchedule newEvent = new EventSchedule();

        newEvent.setWeekId(WeekType);
        newEvent.SetWeekDayPeek(WeekDayNumber - 1);
        newEvent.SetColorForEvent(7);
        newEvent.SetNameEvent(this.Name);
        newEvent.SetTypeEvent(this.Type + " (" + this.Group + ')');
        newEvent.SetHostEvent(this.Host);
        newEvent.SetLocationEvent(this.Location);
        newEvent.SetTimeEventStart(timeList.GetStartTimeById(this.EventNumber - 1));
        newEvent.SetTimeEventEnd(timeList.GetEndTimeById(this.EventNumber - 1));
        newEvent.setGroupName(this.Group);
        return newEvent;
    }

    public EventSchedule toUsersSchedule(){
        EventSchedule newEvent = new EventSchedule();

        newEvent.setWeekId(WeekType);
        newEvent.SetWeekDayPeek(WeekDayNumber);
        newEvent.SetColorForEvent(7);

        if(this.getName() != null){
            newEvent.SetNameEvent(this.Name);
        }
        else{
            newEvent.SetNameEvent("");
        }

        if(this.getType() != null){
            newEvent.SetTypeEvent(this.Type);
        }
        else{
            newEvent.SetTypeEvent("");
        }

        if(this.getHost() != null){
            newEvent.SetHostEvent(this.Host);
        }
        else{
            newEvent.SetHostEvent("");
        }

        if(this.getLocation() != null){
            newEvent.SetLocationEvent(this.Location);
        }
        else{
            newEvent.SetLocationEvent("");
        }

        if(this.getGroup() != null){
            newEvent.setGroupName(this.Group);
        }
        else{
            newEvent.setGroupName("");
        }

        newEvent.SetTimeEventStart(this.TimeStart);
        newEvent.SetTimeEventEnd(this.TimeEnd);
        return newEvent;
    }

    @Override
    public String toString(){
        return
                "Schedule [WeekDayNumber=" + WeekDayNumber + "]\n" +
                        "[WeekType=" + WeekType + "]\n" +
                        "[EventNumber=" + EventNumber + "]\n" +
                        "[Name=" + Name + "]\n" +
                        "[Type=" + Type + "]\n" +
                        "[Host=" + Host + "]\n" +
                        "[Faculty=" + faculty + "]\n" +
                        "[Location=" + Location + "]\n";
    }

    public long getTimeStart() {
        return TimeStart;
    }

    public void setTimeStart(long timeStart) {
        TimeStart = timeStart;
    }

    public long getTimeEnd() {
        return TimeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        TimeEnd = timeEnd;
    }
}
