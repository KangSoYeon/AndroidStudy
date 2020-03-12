package com.example.userinputproject;

import android.content.Context;
import android.media.session.MediaSession;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Dictionary> cList;
    private Total total_count;
    private Context mContext;

    //ViewHolder: RecyclerView가 뷰를 재활용 할 때, 각 뷰 내용을 업데이트 하기 위해 findViewById를 매번 호출 해야하는 것이
    //성능 저하를 일으킴으로 ItemView의 각 요소를 바로 엑세스 할 수 있도록 저장해두고 사용하기 위한 객체

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        protected TextView name;
        protected TextView gender;
        protected TextView age;

        public CustomViewHolder(View view){
            super(view);
            this.name = (TextView) view.findViewById(R.id.name_listitem);
            this.gender = (TextView) view.findViewById(R.id.gender_listitem);
            this.age = (TextView) view.findViewById(R.id.age_listitem);


            //New를 어디서 해줄 것이냐,,, 그것이 문제,,
            //공통 객체인데 여기서 또 New해서 참조하니까 값이 초기화 됨

            //total_count = new Total();

            //OnCreateContextMenuListener 리스너를 현재 클래스에서 구현한다고 설정
            view.setOnCreateContextMenuListener(this);
        }



        //3. 컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록
        //ID 1001, 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
            MenuItem Delete = menu.add(Menu.NONE, 1002, 1, "삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);

        }


        //4. 컨텍스트 메뉴에서 항목 클릭시 동작을 설정
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item){


                if(item.getItemId() == 1002){ //삭제 항목 선택시

                    if(cList.get(getAdapterPosition()).getGender().equals("F")){ //여자가 지워지면
                        int temp = total_count.getTotal_f();

                        Log.d("여성 삭제전", +temp+"");
                        total_count.setTotal_f(temp-1);
                        Log.d("여성 삭제후", total_count.getTotal_f()+"");

                    }else{
                        int temp = total_count.getTotal_m();

                        Log.d("남성 삭제전", +temp+"");
                        total_count.setTotal_m(temp-1);
                        Log.d("남성 삭제후", total_count.getTotal_m()+"");
                    }

                    cList.remove(getAdapterPosition()); //ArrayList에서 해당 데이터를 삭제

//                    total_F.setText(total_count.getTotal_f()+"");
//                    total_M.setText(total_count.getTotal_m()+"");

                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), cList.size());

                }
                return true;
            }
        };

    }


    //provide a suitavle constructor
    public CustomAdapter(ArrayList<Dictionary> list){
        this.cList = list;
    }

    //create new views (invoked by the layout manager)
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    //replace the contents of a view
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25 );
        holder.gender.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        holder.age.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        holder.name.setGravity(Gravity.CENTER);
        holder.gender.setGravity(Gravity.CENTER);
        holder.age.setGravity(Gravity.CENTER);

        holder.name.setText(cList.get(position).getName());
        holder.gender.setText(cList.get(position).getGender());
        holder.age.setText(cList.get(position).getAge());

        //추가되는 애니메이션,,,~ ??


    }
    //Return the size of your dataset
    @Override
    public int getItemCount() {
        return (null != cList ? cList.size() : 0);
    }
}
