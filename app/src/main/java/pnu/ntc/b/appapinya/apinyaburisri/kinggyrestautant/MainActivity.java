package pnu.ntc.b.appapinya.apinyaburisri.kinggyrestautant;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Bind Widget
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);

        //Request SQLite ต้องการใช้ฐานข้อมูล
        myManage = new MyManage(this);

        //Test Add Value
        //testAdd();
        //Delete SQLite
        deleteSQLite();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();


    } //Main Method

    public void clickLogin(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        // Check Space
        if (userString.equals("") || passwordString.equals("")) {
            //Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง",
                    "กรุณากรอกทุกช่อง ค่ะ");

        } else {
            //No Space
            checkUser();
        }

    }//clickLogin

    private void checkUser() {

        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'" + userString + "'",null);
            cursor.moveToFirst();
            String[] resultStrings = new String[cursor.getColumnCount()];
            for (int i=0;i<cursor.getColumnCount();i++) {
                resultStrings[i] = cursor.getString(i);
            }//for
            cursor.close(); //ปิดคืนแรมเครื่องจะได้ไม่ช้า
            //check password
            if (passwordString .equals(resultStrings[2])) {
                //password true
                Toast.makeText(this,"ยินดีต้อนรับ" + resultStrings[3],Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,ServiceActivity.class);
                intent.putExtra("Officer", resultStrings[3]);
                startActivity(intent);
                finish(); //เมื่อเคลื่นย้ายการทำงานแล้วกลับมาหน้านี้อัตโนมัติ
            } else {
                //password False
                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(this,"Password ผิด","กรุณากรอกใหม่ Password ผิด");

            }

        } catch (Exception e) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,"ไม่มี User นี้", "ไม่มี " + userString + "ในฐานข้อมูลของเรา");

        }


    }//checkUser


    private void synJSONtoSQLite() {

        MyMainConnected myMainConnected = new MyMainConnected();
        myMainConnected.execute();


    }


    //Inner Class
    public class MyMainConnected extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/9Apr/php_get_user_kinggy.php").build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();

            } catch (Exception e) {
                Log.d("Restaurant", "error ==>" + e.toString());
                return null;
            }

        }//doInBack

        @Override
        protected void onPostExecute(String strJSON) {
            super.onPostExecute(strJSON);

            Log.d("Restaurant", "strJSON ==>" + strJSON);

            try {

                JSONArray jsonArray = new JSONArray(strJSON);
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strUser = jsonObject.getString(MyManage.column_user);
                    String strPassword = jsonObject.getString(MyManage.column_pass);
                    String strName = jsonObject.getString(MyManage.column_name);

                    myManage.addValueToSQLite(0, strUser, strPassword, strName);


                } //for

            } catch (Exception e) {
                Log.d("Restaurant", "Error ==>" + e.toString());

            }//try

        } //onPost
    }//MyMainConnected Class

    private void deleteSQLite() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.food_table, null, null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);

    }


    private void testAdd() {
        myManage.addValueToSQLite(0, "user","pass","name");
        myManage.addValueToSQLite(1, "food","price","source");
    }

}//Main Class
