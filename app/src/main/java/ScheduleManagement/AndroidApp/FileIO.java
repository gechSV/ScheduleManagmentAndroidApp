package ScheduleManagement.AndroidApp;

import android.content.Context;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Класс для работы с файловой системой андроид.
 * Запись и чтение файлов.
 */
public class FileIO {
    /**
     * Запись нового события в файл
     * @param eventSchedule объект события
     * @return true - успех, false - ошибка
     */
    public static void WriteNewSchedule(EventSchedule eventSchedule, String fileName, Context context){

        FileOutputStream fos = null;

        try{
            byte[] data = ObjectInByte.convertObjectToBytes(12);

            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            fos.write(data);
            fos.flush();
            fos.close();
        }
        catch (IOException ex){
            throw new Error(ex.getMessage());
        }
    }
}
