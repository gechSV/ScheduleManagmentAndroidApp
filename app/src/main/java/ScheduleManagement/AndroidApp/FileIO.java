package ScheduleManagement.AndroidApp;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Класс для работы с файловой системой андроид.
 * Запись и чтение файлов.
 */
public class FileIO {
    /**
     * Запись объекта EventSchedule в файл
     * @param eventSchedule объект события
     * @param fileName имя файла
     * @param context контекст (this)
     */
    public static void WriteScheduleEventInFile(EventSchedule eventSchedule, String fileName, Context context){
        try{
            FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(eventSchedule);
            oos.close();
            fos.close();
        }
        catch (IOException ex){
            throw new Error(ex.getMessage());
        }
    }


    /**
     * Чтение объекта EventSchedule из файла
     * @param fileName имя файла
     * @param context контекст (this)
     * @return объект класса EventSchedule
     */
    public static EventSchedule ReadScheduleEventInFile(String fileName, Context context){
        try{
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            EventSchedule eventSchedule = (EventSchedule) is.readObject();
            is.close();
            fis.close();
            return eventSchedule;
        }
        catch (IOException | ClassNotFoundException ex){
            throw new Error(ex.getMessage());
        }
    }

    /**
     * Запись списка объектов EventSchedule в файл
     * @param eventScheduleList Список объектов события
     * @param fileName имя файла
     * @param context контекст (this)
     */
    public static void WriteScheduleEventListInFile(ArrayList<EventSchedule> eventScheduleList, String fileName, Context context){
        try{
            FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(eventScheduleList);
            oos.close();
            fos.close();
        }
        catch (IOException ex){
            throw new Error(ex.getMessage());
        }
    }


    /**
     * Чтение списка объектов EventSchedule из файла
     * @param fileName имя файла
     * @param context контекст (this)
     * @return объект класса EventSchedule
     */
    public static EventScheduleList ReadScheduleEventListInFile(String fileName, Context context){
        try{
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            ArrayList<EventSchedule> EventList = (ArrayList<EventSchedule>)is.readObject();
            EventScheduleList eventScheduleList = new EventScheduleList(EventList);
            is.close();
            fis.close();
            return eventScheduleList;
        }
        catch (IOException | ClassNotFoundException ex){
            throw new Error(ex.getMessage());
        }
    }

    /**
     * Удаление элемента списка хранящегося в файле
     * @param fileName имя файла
     * @param context контекст
     * @param index индекс удаляемого элемента
     */
    public  static void DeleteItemInFileByIndex(String fileName, Context context, int index){
        if(index >= 0){
            EventScheduleList list = FileIO.ReadScheduleEventListInFile(fileName, context);
            list.RemoveEventsDayByIndex(index);
            FileIO.WriteScheduleEventListInFile(list.GetEventsDayList(), fileName, context);
        }
    }
}
