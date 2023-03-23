package ScheduleManagement.AndroidApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActivitySetting extends AppCompatActivity implements View.OnClickListener{
    private Button TestButton;
    private TextView TestText;
    private httpAppClient _httpAppClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        _httpAppClient = new httpAppClient();
        TestButton = findViewById(R.id.buttonTest);
        TestText = findViewById(R.id.textViewTest);

        TestButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Прослушивание кнопок выбора цвета
            case (R.id.buttonTest):
                _httpAppClient.GetScheduleNameListByName(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        TestText.setText(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        switch (response.code()){
                            case 200:
                                TestText.setText(response.body().string());
                                break;
                            case 400:
                                TestText.setText("400");
                                break;
                            case 404:
                                TestText.setText("404");
                                break;
                        }
                    }
                });
        }
    }
}