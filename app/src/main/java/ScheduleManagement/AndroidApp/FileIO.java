package ScheduleManagement.AndroidApp;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

import es.dmoral.toasty.Toasty;

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
            // Проверка существования файла содержащего список событий
            File file = new File(context.getFilesDir(), fileName);
            if (file.exists()){
                    FileInputStream fis = context.openFileInput(fileName);
                    ObjectInputStream is = new ObjectInputStream(fis);
                    ArrayList<EventSchedule> EventList = (ArrayList<EventSchedule>)is.readObject();
                    EventScheduleList eventScheduleList = new EventScheduleList(EventList);
                    is.close();
                    fis.close();
                    return eventScheduleList;
            }
            else{
                EventScheduleList newScheduleList = new EventScheduleList();
                FileIO.WriteScheduleEventListInFile(newScheduleList.GetEventsDayList(), fileName, context);
                return newScheduleList;
            }
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

    /**
     * Удаление элемента списка хранящегося в файле
     * @param fileName имя файла
     * @param context контекст
     * @param id индекс удаляемого элемента
     */
    public  static void DeleteItemInFileById(String fileName, Context context, int id){
        if(id >= 0){
            EventScheduleList list = FileIO.ReadScheduleEventListInFile(fileName, context);
            list.RemoveEventsDayById(id);
            FileIO.WriteScheduleEventListInFile(list.GetEventsDayList(), fileName, context);
        }
    }

    public static  void WriteTimeForNumberList(ArrayList<TimeForNumber> time, String fileName, Context context){
        try{
            FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(time);
            oos.close();
            fos.close();
        }
        catch (IOException ex){
            throw new Error(ex.getMessage());
        }
    }

    public static TimeForNumberList ReadTimeForNumberList(String fileName, Context context){
        try{
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            ArrayList<TimeForNumber> time = (ArrayList<TimeForNumber>)is.readObject();
            TimeForNumberList timeList = new TimeForNumberList(time);
            is.close();
            fis.close();
            return timeList;
        }
        catch (IOException | ClassNotFoundException ex){
            throw new Error(ex.getMessage());
        }
    }

    /**
     * Удаление элемента списка хранящегося в файле
     * @param fileName имя файла
     * @param context контекст
     * @param id индекс удаляемого элемента
     */
    public static void DeleteTimePatternInFileById(String fileName, Context context, int id){
        if(id >= 0){
            TimeForNumberList list = FileIO.ReadTimeForNumberList(fileName, context);
            list.RemoveByIndex(id);
            FileIO.WriteTimeForNumberList(list.GetTimeForNumberList(), fileName, context);
        }
    }

    public static boolean CheckRunAppFlag(String fileName, Context context){
        try{
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            boolean flag = (boolean)is.readObject();
            is.close();
            fis.close();
            return flag;
        }
        catch (IOException | ClassNotFoundException ex){
            throw new Error(ex.getMessage());
        }
    }

    public static void SetRunAppFlag(boolean flag, String fileName, Context context){
        try{
            FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(flag);
            oos.close();
            fos.close();
        }
        catch (IOException ex){
            throw new Error(ex.getMessage());
        }
    }

    /**
     * Запись списка объектов NoteList в файл
     * @param noteList Список объектов события
     * @param fileName имя файла
     * @param context контекст (this)
     */
    public static void WriteNoteListInFile(ArrayList<Note> noteList, String fileName, Context context){
        try{
            FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(noteList);
            oos.close();
            fos.close();
        }
        catch (IOException ex){
            throw new Error(ex.getMessage());
        }
    }


    /**
     * Чтение списка объектов Note из файла
     * @param fileName имя файла
     * @param context контекст (this)
     * @return объект класса EventSchedule
     */
    public static NoteList ReadNoteListInFile(String fileName, Context context){
        try{
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            ArrayList<Note> NoteArrayList = (ArrayList<Note>)is.readObject();
            NoteList noteList = new NoteList(NoteArrayList);
            is.close();
            fis.close();
            return noteList;
        }
        catch (IOException | ClassNotFoundException ex){
            throw new Error(ex.getMessage());
        }
    }

    /**
     * Чтение файла содержащего список заметок и последующая
     * его запись в переменную - _eventScheduleList
     */
    static public NoteList ReadNodeListFromFile(String fileName, Context context){
        try {
            // Проверка существования файла содержащего список событий
            File file = new File(context.getFilesDir(), fileName);

            // Проверка на существование файла
            if(file.exists()){
                // Файл существует (был создан ранее)

                // Читаем список из файла и записываем в новый объект
                return ReadNoteListInFile(fileName, context);
            }
            else
            {
                // Файл не существует (не был создан)
                // Cоздаём новый пустой объект и записываем его в файл
                NoteList newNoteList = new NoteList();
                FileIO.WriteNoteListInFile(newNoteList.getNoteList(), fileName, context);
                return newNoteList;
//                Toasty.error(this, "Не существ", Toast.LENGTH_SHORT, true).show();
            }
        }
        catch (Error err){
            Toasty.error(context, Objects.requireNonNull(err.getMessage()), Toast.LENGTH_SHORT,
                    true).show();
        }
        return null;
    }

    /**
     * Удаление элемента списка хранящегося в файле
     * @param fileName имя файла
     * @param context контекст
     * @param id индекс удаляемого элемента
     */
    public  static void DeleteNoteInFileById(String fileName, Context context, int id){
        if(id >= 0){
            NoteList list = FileIO.ReadNodeListFromFile(fileName, context);
            list.RemoveNoteById(id);
            FileIO.WriteNoteListInFile(list.getNoteList(), fileName, context);
        }
    }

    public static String getUrlAddress( String fileName, Context context){
        try{
            // Проверка существования файла содержащего список событий
            File file = new File(context.getFilesDir(), fileName);
            if (file.exists()){

                FileInputStream fis = context.openFileInput(fileName);
                ObjectInputStream is = new ObjectInputStream(fis);
                String url = (String)is.readObject();
                is.close();
                fis.close();

                return url;
            }
            else{
                FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject("127.0.0.1:8000");
                oos.close();
                fos.close();
                return "127.0.0.1:8000";
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setUrlAddress(String url, String fileName, Context context){
        try {
            FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(url);
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getTypeWeekFlag( String fileName, Context context){
        try{
            // Проверка существования файла содержащего список событий
            File file = new File(context.getFilesDir(), fileName);
            if (file.exists()){

                FileInputStream fis = context.openFileInput(fileName);
                ObjectInputStream is = new ObjectInputStream(fis);
                boolean flag = (boolean)is.readObject();
                is.close();
                fis.close();

                return flag;
            }
            else{
                FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(true);
                oos.close();
                fos.close();
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setTypeWeekFlag(boolean flag, String fileName, Context context){
        try {
            FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(flag);
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
