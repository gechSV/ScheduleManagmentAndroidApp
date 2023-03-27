package ScheduleManagement.AndroidApp;

import static java.sql.DriverManager.println;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class httpAppClient {
    private  OkHttpClient client;

    public httpAppClient(){
        this.client = new OkHttpClient();
    }


    public void GetScheduleNameListByName(Callback callback){
        Request request = new Request.Builder()
                .url("http://192.168.0.11:8000/api/getScheduleNameListByOrganizatonName/ЗабГУ")
                .build();
        client.newCall(request).enqueue(callback);
    }

    public Organization[] GetOrganizationNameByTypeName(){
        Request request = new Request.Builder()
                .url("http://192.168.0.11:8000/api/getAllOrganizationByOrgType/Высшее учебное заведение")
                .build();

        CallbackFuture future = new CallbackFuture();

        client.newCall(request).enqueue(future);

        try {
            Response response = future.get();
            int code = response.code();

            switch (code){
                case 200:
                    Gson gson = new Gson();
                    String _jsonOrgName = response.body().string();
                    // Конвертируем json в массив java
                    Organization[] organization = gson.fromJson(_jsonOrgName, Organization[].class);
                    return organization;
                default:
                    throw new RuntimeException("Сервер недоступен");
            }
        }
        catch (ExecutionException e) {
            Log.d("MesLog1", e.getMessage());
            // выполняется если сервер недоступен
            throw new RuntimeException(e);
        }
        catch (InterruptedException e) {
            Log.d("MesLog2", e.getMessage());
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            Log.d("MesLog3", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
