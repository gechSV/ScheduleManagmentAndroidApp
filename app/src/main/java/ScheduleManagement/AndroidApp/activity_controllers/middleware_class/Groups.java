package ScheduleManagement.AndroidApp.activity_controllers.middleware_class;

public class Groups{
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "Group [name=" + name + "]";
    }
}
