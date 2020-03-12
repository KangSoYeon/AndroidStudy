package com.example.userinputproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Dictionary> cArrayList;
    private CustomAdapter cAdapter;
    //private Total total_count;
    private static final Total total_count = new Total();
    InputMethodManager imm;
    EditText name, age, total_M, total_F;
    LinearLayout whole0, whole1, whole2;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView cRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);

        //use linear layout manager
        LinearLayoutManager cLinearLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLinearLayoutManager);

        cArrayList = new ArrayList<>();

        //specify an adapter
        cAdapter = new CustomAdapter(cArrayList);
        cRecyclerView.setAdapter(cAdapter);

        //total_count = new Total(0,0,0);

        //구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(cRecyclerView.getContext(), cLinearLayoutManager.getOrientation());
        cRecyclerView.addItemDecoration(dividerItemDecoration);

        //사용자가 입력한 값 editText에서 가져오기
//       final EditText name = (EditText)findViewById(R.id.name);
//       final EditText gender = (EditText)findViewById(R.id.gender);
//       final EditText age = (EditText)findViewById(R.id.age);

        name = (EditText)findViewById(R.id.name);
        rg = (RadioGroup)findViewById(R.id.gender);
        age = (EditText)findViewById(R.id.age);

        total_M = (EditText)findViewById(R.id.mtotal_num);
        total_F = (EditText)findViewById(R.id.ftotal_num);

        total_F.setClickable(false);
        total_M.setClickable(false);

        //키보드 숨기기
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //배경 클릭시 키보드 내려가기,, 왠일인지 먹지 않음...
        whole1 = (LinearLayout)findViewById(R.id.whole1);
        whole1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                hideKeyboard();
            }
        });

        Button insert_b = (Button)findViewById(R.id.save);

        insert_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //사용자가 입력한 내용을 가져와서
                String strName = name.getText().toString();
                RadioButton gen = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                String strGender = gen.getText().toString();
                String strAge = age.getText().toString();

//                //기존의 total값 가져와서
//                String tF = total_F.getText().toString();
//                String tM = total_M.getText().toString();
//


                boolean f = true;
                //입력한 값 예외처리
                if(strName.equals("") ||strGender.equals("") || strAge.equals("")){
                    f = false;
                    Toast.makeText(getApplicationContext(), "값을 모두 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                //나이 받은 값이 숫자인지 예외처리
                if(!isNumber(strAge)){
                    f = false;
                    Toast.makeText(getApplicationContext(), "나이는 숫자값을 입력해 주세요", Toast.LENGTH_SHORT).show();
                }

                if(f){
                    Dictionary dict = new Dictionary(strName, strGender, strAge);
                    cArrayList.add(0, dict);  //첫번째 줄에 삽입됨

                    if(strGender.equals("F")){
                        int temp = total_count.getTotal_f(); //현재 여성수를 가져와서
                        Log.d("main에 있는 여성", temp+"");
                        total_count.setTotal_f(temp+1); //하나 키우고 집어넣기
                        Log.d("main에 있는 여성 추가후", total_count.getTotal_f()+"");
                    }else if(strGender.equals("M")){
                        int temp = total_count.getTotal_m(); //현재 남성수를 가져와서
                        total_count.setTotal_m(temp+1); //하나 키우고 집어넣기
                    }

                    //어댑터에서 RecyclerView에 반영되도록 하기
                    // cAdapter.notifyItemInserted(0);
                    cAdapter.notifyDataSetChanged();

                    //값 하나 입력 받았으면 edittext 창 비워주기
                    name.setText(null);
                    age.setText(null);


                    //인원수 총계 다시 반영
                    total_F.setText(total_count.getTotal_f()+"");
                    total_M.setText(total_count.getTotal_m()+"");

                    hideKeyboard(); //keyboard내리기
                }

                /*//로그 찍어보기
                Log.d("name", strName);
                Log.d("gender", strGender);
                Log.d("age", strAge);*/

            }
        });

    }
    private void hideKeyboard(){
        imm.hideSoftInputFromWindow(name.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(age.getWindowToken(), 0);
    }

    private boolean isNumber(String input){
        for(int i=0; i<input.length(); i++){ //하나하나 수가 0~9사이가 아니면
            if(0>input.charAt(i)-'0' || 9<input.charAt(i)-'0'){
                return false; //숫자가 아닌경우 false 반환
            }
        }
        return true;
    }

}
