package ScheduleManagement.AndroidApp;

import android.telecom.Call;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.security.auth.callback.Callback;

import okhttp3.Response;

// Для синхронизации потоков, при вызове callback функций
class CallbackFuture extends CompletableFuture<Response> implements Callback, okhttp3.Callback {
    @Override
    public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
        super.completeExceptionally(e);
    }

    @Override
    public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
        super.complete(response);
    }
}