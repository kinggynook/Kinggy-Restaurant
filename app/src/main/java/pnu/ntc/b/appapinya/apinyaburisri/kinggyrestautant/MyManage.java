package pnu.ntc.b.appapinya.apinyaburisri.kinggyrestautant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by apinyaburisri on 4/9/2016 AD.
 */
public class MyManage {

    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MyManage(Context context) {
        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();


    } //Constructor
}//Main Class
