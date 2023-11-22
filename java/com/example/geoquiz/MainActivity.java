package com.example.geoquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";//定义并初始化TAG
    private static final String KEY_INDEX = "index";//定义并初始化KEY_INDEX
    private static final String KEY_ANSWER = "KEY_ANSWER";//定义并初始化KEY_answer
    private static final String KEY_SCORE= "KEY_SCORE";//定义并初始化KEY_SCORE
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };
    private int mCurrentIndex = 0;
    private int mScore = 0;
    /*int num=0;
    num=num+answerList[i];
     if(num==mQuestionBank.length)
    {
        Toast toast= Toast.makeText(this,mScore/num*100,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,500);
        toast.show();
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//大概是activity调用父类表示一个窗口正在生成
        Log.d(TAG, "onCreate(Bundle) called");//调用 Log.d(...) 方法记录日志
        setContentView(R.layout.activity_main);//加载对应的xml资源到activity中

        //在 onCreate(Bundle) 方法中确认是否成功获取该数值。如果获取成功，就将它赋值
        //给变量 mCurrentIndex
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);//导出mCurrentIndex
            mScore=savedInstanceState.getInt(KEY_SCORE,0);//导出mScore

            int[] answerList = savedInstanceState.getIntArray(KEY_ANSWER);

            for (int i = 0; i < mQuestionBank.length; i++) {
                mQuestionBank[i].setIsAnswerd(answerList[i]);//将答题情况从question中取出来

            }
        }


        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);//引用问题
        //问题监听
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);//引用按钮


        //监听
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);

            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);

            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;

                updateQuestion();
            }
        });
        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex==0){
                    mCurrentIndex=mQuestionBank.length-1;
                    updateQuestion();
                }
                else
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        updateQuestion();
    }

    //封装更新问题
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        ButtonEnabled();
    }


    //判断对错
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        int num=0;
        if (userPressedTrue == answerIsTrue) {
            mQuestionBank[mCurrentIndex].setIsAnswerd(1);
            messageResId = R.string.correct_toast;
            mScore++;
        } else {
            mQuestionBank[mCurrentIndex].setIsAnswerd(1);
            messageResId = R.string.incorrect_toast;
        }
        ButtonEnabled();
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();

        for(int i=0;i<mQuestionBank.length;i++)
        {
            if(mQuestionBank[i].getIsAnswerd()==0){
                break;
            }
            else if(mQuestionBank[i].getIsAnswerd()==1&&i==mQuestionBank.length-1) {
                double correctMark = (double) mScore / mQuestionBank.length;
                correctMark = (double) ((int) (correctMark * 10000) / 100.0);//将correctMark变为百分数
                String text = "正确率" + String.valueOf(correctMark) + "%";//将correctMark通过String.valueOf变为字符串
                Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 500);
                toast.show();
            }
        }
       /* for (int i=0;i<mQuestionBank.length;i++){
            num+=mQuestionBank[i].getIsAnswerd();
        }
        if(num==mQuestionBank.length){
            {
                double correctMark = (double)mScore/mQuestionBank.length;
                correctMark = (double)((int)(correctMark * 10000)/100.0);//将correctMark变为百分数
                String text = "正确率" + String.valueOf(correctMark) + "%";//将correctMark通过String.valueOf变为字符串
                Toast toast = Toast.makeText(this,text, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 500);
                toast.show();
            }
        }*/
    }

    @Override
    public void onStart() {
        super.onStart();//先调用超类
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");

        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);//将mCurrentIndex储存到Bundle中。
        savedInstanceState.putInt(KEY_SCORE,mScore);//将mScore储存到Bundle中

        int[] answerList = new int[mQuestionBank.length];//存储是否已经答过该题的情况
        for (int i = 0; i < answerList.length; i++) {
            answerList[i] = mQuestionBank[i].getIsAnswerd();
        }
        savedInstanceState.putIntArray(KEY_ANSWER, answerList);

    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    public void ButtonEnabled() {
        if (mQuestionBank[mCurrentIndex].getIsAnswerd() > 0) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        } else {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);

        }
    }
}


