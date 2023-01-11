package ScheduleManagement.AndroidApp;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
}
