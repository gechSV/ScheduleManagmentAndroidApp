package ScheduleManagement.AndroidApp;

import static java.sql.DriverManager.println;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

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
}
