package ScheduleManagement.AndroidApp;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import ScheduleManagement.AndroidApp.middleware_class.Groups;
import ScheduleManagement.AndroidApp.middleware_class.Organization;
import ScheduleManagement.AndroidApp.middleware_class.Schedule;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

    public class httpAppClient {
    private  OkHttpClient client;

    private String _serverSocket = "192.168.0.11:8000";

    public httpAppClient(){
        this.client = new OkHttpClient();
    }


    public void GetScheduleNameListByName(Callback callback){
        Request request = new Request.Builder()
                .url(String.format("http://%s/api/getScheduleNameListByOrganizatonName/ЗабГУ", _serverSocket))
                .build();
        client.newCall(request).enqueue(callback);
    }

        /**
         * Получить список организаций
         * @return Organization[]
         */
    public Organization[] GetOrganizationNameByTypeName(){
        Request request = new Request.Builder()
                .url(String.format("http://%s/api/getAllOrganizationByOrgType/Высшее учебное заведение", _serverSocket))
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

        /**
         * Получить наименования групп(названия расписания) по имени организации
         * @param OrgName наименование организации
         * @return Groups[]
         */
    public Groups[] GetGroupByNameOrganizations(String OrgName){
        String _jsonGroupName;

        Request request = new Request.Builder()
                .url(String.format("http://%s/api/getScheduleNameListByOrganizatonName/%s", _serverSocket, OrgName))
                .build();

        CallbackFuture future = new CallbackFuture();

        client.newCall(request).enqueue(future);

        try {
            Response response = future.get();
            int code = response.code();

            switch (code){
                case 200:
                    Gson gson = new Gson();

                    _jsonGroupName = response.body().string();
                    // Конвертируем json в массив java
                    Groups[] groups = gson.fromJson(_jsonGroupName, Groups[].class);
                    Log.d("MesLog", groups[1].toString());
                    return groups;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Schedule[] getScheduleByName(String groupName, String password){
        Request request = new Request.Builder()
                .url(String.format("http://%s/api/getScheduleByGroupName/%s", _serverSocket, groupName))
                .addHeader("password", password)
                .build();

        CallbackFuture future = new CallbackFuture();

        client.newCall(request).enqueue(future);

        try {
            Response response = future.get();
            int code = response.code();

            switch (code){
                case 200:
                    Gson gson = new Gson();
                    String _jsonSchedule = response.body().string();
                    Schedule[] schedule = gson.fromJson(_jsonSchedule, Schedule[].class);
                    return schedule;
                default:
                    throw new RuntimeException("Ошибка сервера");
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
