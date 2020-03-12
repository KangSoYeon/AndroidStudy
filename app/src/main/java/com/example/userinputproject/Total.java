package com.example.userinputproject;

public class Total {

    //Singleton디자인 적용 : 다른 모든 클래스에서 하나의 total 정보를 읽고 수정할 수 있게

    //static 객체변수 instance 선언
    private static Total _Instance;

    //static 객체변수 getter선언 (항상 같은 객체 가져오게 됨)
    public static Total getInstance(){
        if(_Instance == null){
            _Instance = new Total();
        }
        return _Instance;
    }

    private int total_p; //전체 사람수
    private int total_f; //전체 여성수
    private int total_m; //전체 남성수

    public int getTotal_p() {
        return total_p;
    }

    public void setTotal_p(int total_p) {
        this.total_p = total_p;
    }

    public int getTotal_f() {
        return total_f;
    }

    public void setTotal_f(int total_f) {
        this.total_f = total_f;
    }

    public int getTotal_m() {
        return total_m;
    }

    public void setTotal_m(int total_m) {
        this.total_m = total_m;
    }

    public Total() {
    }

    public Total(int total_p, int total_f, int total_m) {
        this.total_p = total_p;
        this.total_f = total_f;
        this.total_m = total_m;
    }


}
