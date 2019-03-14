package rezept_day.ucoz.ru.countapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewTimer;
    private TextView textViewOpinion0;
    private TextView textViewOpinion1;
    private TextView textViewOpinion2;
    private TextView textViewOpinion3;
    private ArrayList<TextView> options = new ArrayList<>();//Массив кнопок с ответами

    private String question;//переменная содержащая вопрос
    private int rightAnswer;//переменная содержащая правильный ответ
    private int rightAnswerPosition;//переменная содержащая кнопку с правильным ответом
    private boolean isPositive;//переменная отвечающая выбор сложения или вычитания
    private int min = 5;//минимально генерируемое число для примеров
    private int max = 30;//максимально генерируемое число для примеров
    private int countOfQuestions = 0;//Количество отвеченых вопросов
    private int countOfRightAnswers = 0;//Количество правильных ответов
    private boolean gameOver = false;//Переменная проверяющая закончилась ли игра

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        playNext();//Запускаем игру

        //Создаем таймер
        CountDownTimer timer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(getTime(millisUntilFinished));//Отображаем сколько осталось времени до конца игры
                if(millisUntilFinished < 10000){
                    textViewTimer.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }

            @Override
            public void onFinish() {
                gameOver = true;//по окончанию таймера останавливаем игру
                //Сохраняем лучший результат
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//не можем указать this, т.к. находимся в анонимном классе. И this будет относиться к нему, а не к активности
                //Нужно проверить не сохранили мы уже результат максимального значения
                int max = preferences.getInt("max", 0);
                if(countOfRightAnswers >= max){
                    preferences.edit().putInt("max", countOfRightAnswers).apply();
                }

                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                intent.putExtra("result", countOfRightAnswers);
                startActivity(intent);
            }
        };
        timer.start();
    }

    private void initUI() {
        textViewOpinion0 = findViewById(R.id.textViewOpinion0);
        textViewOpinion1 = findViewById(R.id.textViewOpinion1);
        textViewOpinion2 = findViewById(R.id.textViewOpinion2);
        textViewOpinion3 = findViewById(R.id.textViewOpinion3);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewScore = findViewById(R.id.textViewScore);
        textViewTimer = findViewById(R.id.textViewTimer);
        options.add(textViewOpinion0);
        options.add(textViewOpinion1);
        options.add(textViewOpinion2);
        options.add(textViewOpinion3);
    }

    private void generateQuestion(){
        int a = (int)(Math.random() * (max - min + 1) + min);//сгененрируем число от min до max
        int b = (int)(Math.random() * (max - min + 1) + min);
        int mark = (int)(Math.random() * 2);//сгенерирует только 0 или 1
        isPositive = mark == 1;//Если mark будет равен 1, то переменной присвоится true, иначе false;
        if(isPositive){//Если знак +
            rightAnswer = a + b;//считаем правильный ответ
            question = String.format("%s + %s", a, b);//Формируем вопрос
        } else {//Если знак -
            rightAnswer = a - b;
            question = String.format("%s - %s", a, b);
        }
        textViewQuestion.setText(question);
        rightAnswerPosition = (int)(Math.random() * 4);//генирируем позицию правильного ответа
    }

    private int generateWrongAnswer(){
        int result;
        do {
            result = (int)(Math.random() * max * 2 + 1) - (max - min); //сгенерирует число от -25 до 35
        } while (result == rightAnswer);
        return result;
    }

    private void playNext(){
        generateQuestion();//генерируем вопрос

        for(int i = 0; i < options.size(); i++){//Присваиваем ответы кнопкам
            if(i == rightAnswerPosition){
                options.get(i).setText(Integer.toString(rightAnswer));
            } else {
                options.get(i).setText(Integer.toString(generateWrongAnswer()));
            }
        }
        String score = String.format("%s / %s", countOfQuestions, countOfRightAnswers);
        textViewScore.setText(score);
    }

    public void onClickAnswer(View view) {
        if(!gameOver){//если игра не закончена
            TextView textView = (TextView) view;
            String answer = textView.getText().toString();//получаем текст с нажатой кнопки
            int choseAnswer = Integer.parseInt(answer);//Преобразуем к int, чтобы сравнить с правильным ответом
            if (choseAnswer == rightAnswer){
                countOfRightAnswers++;//Если ответ правильный - увеличиваем счетчик правильных ответов
                Toast.makeText(this, "Верно", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Неверно", Toast.LENGTH_SHORT).show();
            }
            countOfQuestions++;//Не зависимо от правильности ответа - увеличиваем счетчик отвеченых вопросов
            playNext();//делаем перезапуск игры
        }

    }

    private String getTime(long millis){//Преобразование миллисекунд в строковое представление минут и секунд
        int seconds = (int)(millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
    }
}
