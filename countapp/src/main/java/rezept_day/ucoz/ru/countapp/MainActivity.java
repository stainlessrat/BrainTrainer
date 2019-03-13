package rezept_day.ucoz.ru.countapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewTimer;
    private TextView textViewOpinion0;
    private TextView textViewOpinion1;
    private TextView textViewOpinion2;
    private TextView textViewOpinion3;
    private ArrayList<TextView> options = new ArrayList<>();

    private String question;
    private int rightAnswer;
    private int rightAnswerPosition;
    private boolean isPositive;
    private int min = 5;
    private int max = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        playNext();//Запускаем игру
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
    }

    public void onClickAnswer(View view) {
        TextView textView = (TextView) view;
        String answer = textView.getText().toString();//получаем текст с нажатой кнопки
        int choseAnswer = Integer.parseInt(answer);//Преобразуем к int, чтобы сравнить с правильным ответом
        if (choseAnswer == rightAnswer){
            Toast.makeText(this, "Верно", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Неверно", Toast.LENGTH_SHORT).show();
        }
        playNext();//делаем перезапуск игры
    }
}
