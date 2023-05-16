package ScheduleManagement.AndroidApp.middleware_class;

public class Organization {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "Organisation [name=" + name + "]";
    }
}
