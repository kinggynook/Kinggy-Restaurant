package pnu.ntc.b.appapinya.apinyaburisri.kinggyrestautant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Request SQLite ต้องการใช้ฐานข้อมูล
        myManage = new MyManage(this);
    } //Main Method

}//Main Class
