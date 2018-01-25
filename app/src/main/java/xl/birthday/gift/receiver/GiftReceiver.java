package xl.birthday.gift.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by xzhang on 2018/1/25.
 */

public class GiftReceiver extends BroadcastReceiver {

    String msg = "Android : ";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(msg, "received a new intent");
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_SHORT).show();
    }
}
