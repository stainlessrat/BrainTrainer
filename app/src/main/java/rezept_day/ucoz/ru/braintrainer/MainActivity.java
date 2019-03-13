package rezept_day.ucoz.ru.braintrainer;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTimer = findViewById(R.id.textViewTimer);

        CountDownTimer timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int)(millisUntilFinished / 1000);
                seconds++;//Увеличиваем на единицу, т.к. таймер работает еще секунду после того как выведет 0 (миллисекунды не отображает
                textViewTimer.setText(Integer.toString(seconds));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Таймер завершен", Toast.LENGTH_SHORT).show();
                textViewTimer.setText(Integer.toString(0));//Сбрасываем на 0 по завершению работы таймера   
            }
        };
        timer.start();

//        //Создаем объект типа SharedPreferences для хранения данных
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        //Занесение данных. 1. вызвать метод edit() - редактирование
//        preferences.edit().putInt("test", 5).apply();//2. затем ложим данные (putInt, putBoolean и т.д.) ключ-значение
//                                                    //3. сохраняем - метод apply()
//
//        //Получить данные из сохранения
//        int test = preferences.getInt("test", 0);//по ключу, и указать вторым параметром значение по умолчанию, если там еще ничего не сохранено
//        Toast.makeText(this, Integer.toString(test), Toast.LENGTH_SHORT).show();

    }
}
