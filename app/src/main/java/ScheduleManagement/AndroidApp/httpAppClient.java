package ScheduleManagement.AndroidApp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ScheduleManagement.AndroidApp.middleware_class.Groups;
import ScheduleManagement.AndroidApp.middleware_class.Organization;
import ScheduleManagement.AndroidApp.middleware_class.Schedule;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

    public class httpAppClient {
    private  OkHttpClient client;
    private final String FILE_NAME_EVENT_LIST = "Event_Schedule_List_1.bin";

    // TODO: крашится и выдаёт ошибку кода
    private String _serverSocket = "186e-95-189-75-41.ngrok-free.app";

    public httpAppClient(Context context){
        _serverSocket = FileIO.getUrlAddress("urlAddress.bin", context);
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
    public Organization[] GetOrganizationNameByTypeName() throws ExecutionException, InterruptedException, IOException {
        Request request = new Request.Builder()
                .url(String.format("http://%s/api/getAllOrganizationByOrgType/Высшее учебное заведение", _serverSocket))
                .build();

        CallbackFuture future = new CallbackFuture();

        client.newCall(request).enqueue(future);

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

        /**
         * Получить наименования групп(названия расписания) по имени организации
         * @param OrgName наименование организации
         * @return Groups[]
         */
    public Groups[] GetGroupByNameOrganizations(String OrgName, String ScheduleType){
        String _jsonGroupName;

        Request request = new Request.Builder()
                .url(String.format("http://%s/api/getScheduleNameListByOrganizatonName/%s/%s", _serverSocket, OrgName, ScheduleType))
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


    public Schedule[] getScheduleByName(String groupName, String password) throws ExecutionException, InterruptedException, IOException {
        Request request = new Request.Builder()
                .url(String.format("http://%s/api/getScheduleByGroupName/%s", _serverSocket, groupName))
                .addHeader("password", password)
                .build();

        CallbackFuture future = new CallbackFuture();

        client.newCall(request).enqueue(future);

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

        public Schedule[] getUserScheduleByName(String groupName) throws ExecutionException, InterruptedException, IOException {
            Request request = new Request.Builder()
                    .url(String.format("http://%s/api/getUserSchedule/%s", _serverSocket, groupName))
                    .build();

            CallbackFuture future = new CallbackFuture();

            client.newCall(request).enqueue(future);

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

    public boolean addSchedule(String scheduleName, String password, Context context)
            throws ExecutionException, InterruptedException {
        EventScheduleList eventScheduleList = FileIO.ReadScheduleEventListInFile(FILE_NAME_EVENT_LIST, context);
        ArrayList<Schedule> scheduleList = Schedule.EventScheduleListToSchedule(eventScheduleList.GetEventsDayList());

        Gson gson = new Gson();
        String jsonSchedule = gson.toJson(scheduleList);

        RequestBody formBody = new FormBody.Builder()
                .add("ScheduleName", scheduleName)
                .add("Password", password)
                .add("Schedule", jsonSchedule)
                .build();

        Request request = new Request.Builder()
                .url(String.format("http://%s/api/addSchedule/", _serverSocket))
                .post(formBody)
                .build();

        CallbackFuture future = new CallbackFuture();

        client.newCall(request).enqueue(future);

        Response response = future.get();
        int code = response.code();

        switch (code){
            case 200:
                return true;
            default:
                return false;
        }
    };
}
