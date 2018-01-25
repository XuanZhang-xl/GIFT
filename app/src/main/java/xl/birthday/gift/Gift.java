package xl.birthday.gift;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import xl.birthday.gift.provider.GiftContentProvider;
import xl.birthday.gift.receiver.GiftReceiver;
import xl.birthday.gift.service.GiftService;

public class Gift extends AppCompatActivity {

    String msg = "Android : ";

    private IntentFilter intentFilter;

    private LocalBroadcastManager localBroadcastManager;

    private GiftReceiver localReceive;


    /**
     * 当活动第一次被创建时调用
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);

        //获取到LocalBroadcastManager实例对象
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //创建Intent对象，指定广播内容
                Intent intent = new Intent("xl.birthday.gift.CUSTOM_INTENT");
                //发送本地广播
                localBroadcastManager.sendBroadcast(intent);
            }
        });
        intentFilter = new IntentFilter();
        intentFilter.addAction("xl.birthday.gift.CUSTOM_INTENT");
        localReceive = new GiftReceiver();
        //注册本地广播接收器
        localBroadcastManager.registerReceiver(localReceive, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * 此方法用于初始化菜单，其中menu参数就是即将要显示的Menu实例。 返回true则显示该menu,false 则不显示;
         * (只会在第一次初始化菜单时调用) Inflate the menu; this adds items to the action bar
         * if it is present.
         */
        return true;
    }

    // Method to start the service
    public void startService(View view) {
        startService(new Intent(getBaseContext(), GiftService.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), GiftService.class));
    }

    /**
     * 生成并发送自定义意图
     * 如果你使用sendStickyBroadcast(Intent)方法，则意图是持久的(sticky)，这意味者你发出的意图在广播完成后一直保持着。TODO:
     * cn.uprogrammer.CUSTOM_INTENT: 自定义意图
     *
     * @param view
     */
    public void broadcastIntent(View view) {
        Log.d(msg, "The broadcastIntent() intent");
        Intent intent = new Intent();
        intent.setAction("xl.birthday.gift.CUSTOM_INTENT");
        sendBroadcast(intent);
    }

    public void imageButton(View view) {
        Toast.makeText(this, "Image Button is clicked", Toast.LENGTH_SHORT).show();
    }


    /**
     * 当活动即将可见时调用
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }

    /**
     * 当活动可见时调用
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
    }

    /**
     * 当其他活动获得焦点时调用
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    /**
     * 当活动不再可见时调用
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    /**
     * 当活动将被销毁时调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
        //动态注册，同样需要取消注册广播
        localBroadcastManager.unregisterReceiver(localReceive);
    }

    // 以下两个方法用于内容提供者
    public void onClickAddName(View view) {
        // Add a new student record
        ContentValues values = new ContentValues();

        values.put(GiftContentProvider.NAME,
                ((EditText) findViewById(R.id.editText2)).getText().toString());

        values.put(GiftContentProvider.GRADE,
                ((EditText) findViewById(R.id.editText3)).getText().toString());

        Uri uri = getContentResolver().insert(
                GiftContentProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void onClickRetrieveStudents(View view) {

        // Retrieve student records
        String URL = "content://xl.birthday.gift/gifts";

        Uri students = Uri.parse(URL);
        Cursor c = managedQuery(students, null, null, null, "name");

        if (c.moveToFirst()) {
            do {
                Toast.makeText(this,
                    c.getString(c.getColumnIndex(GiftContentProvider._ID)) +
                            ", " + c.getString(c.getColumnIndex(GiftContentProvider.NAME)) +
                            ", " + c.getString(c.getColumnIndex(GiftContentProvider.GRADE)),
                    Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }


}
