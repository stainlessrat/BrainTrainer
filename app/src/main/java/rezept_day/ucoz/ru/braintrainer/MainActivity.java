package rezept_day.ucoz.ru.braintrainer;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Создаем объект типа SharedPreferences для хранения данных
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Занесение данных. 1. вызвать метод edit() - редактирование
        preferences.edit().putInt("test", 5).apply();//2. затем ложим данные (putInt, putBoolean и т.д.) ключ-значение
                                                    //3. сохраняем - метод apply()

        //Получить данные из сохранения
        int test = preferences.getInt("test", 0);//по ключу, и указать вторым параметром значение по умолчанию, если там еще ничего не сохранено
        Toast.makeText(this, Integer.toString(test), Toast.LENGTH_SHORT).show();

    }
}
