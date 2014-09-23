package com.example.slysop.wearableround2;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MyActivity extends Activity {
    final int notificationId = 001;
    final static String GROUP_KEY_EMAILS = "group_key_emails";
    private static final String EXTRA_VOICE_REPLY = "extra_voice_reply";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_wear);
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_icon);

        String replyLabel = getResources().getString(R.string.reply_label);

        Button myButton = (Button) findViewById(R.id.button);

        //RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
        //        .setLabel(replyLabel)
        //        .setChoices(getResources().getStringArray(R.array.reply_choices))
        //        .build();


        Intent viewIntent = new Intent(this, MyActivity.class);
        viewIntent.putExtra("extra", "extra");

        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);



       // Intent replyIntent = new Intent(this, ReplyActivity.class);



       // PendingIntent replyPendingIntent =
       //         PendingIntent.getActivities(this, 0, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

      //  NotificationCompat.Action action = new Notification.Action.Builder(R.drawable.ic_reply_icon,
       //         getString(R.string.label, replyPendingIntent))
       //         .addRemoteInput(remoteInput)
       //         .build();

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.title))
                .setContentInfo("Hey")
                .setSmallIcon(R.drawable.ic_icon)
                .setContentText(getString(R.string.contentText))
                .setContentIntent(viewPendingIntent)
                .extend(new NotificationCompat.WearableExtender().setBackground(myBitmap));

        NotificationCompat.BigTextStyle secondPageStyle = new NotificationCompat.BigTextStyle();
            secondPageStyle.setBigContentTitle("Page 2")
                    .bigText("A lot of text....");

        Notification secondPageNotification =
                new NotificationCompat.Builder(this)
                .setStyle(secondPageStyle)
                .setSmallIcon(R.drawable.ic_icon)
                .setLargeIcon(myIcon)
                .build();

      //  Notification replyNotification =
      //          new NotificationCompat.Builder(this)
      //          .setSmallIcon(R.drawable.ic_reply)
      //          .setContentTitle(getString(R.string.title))
      //          .setContentText(getString(R.string.content))
      //          .build();

       final Notification twopage =
                new NotificationCompat.WearableExtender()
                .setBackground(myBitmap)
                .addPage(secondPageNotification)
                .extend(notificationBuilder)
                .build();

        final NotificationManagerCompat nmc = NotificationManagerCompat.from(this);

        nmc.notify(notificationId, twopage);


        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nmc.notify(notificationId, twopage);
            }
        });
    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if(remoteInput != null){
            return remoteInput.getCharSequence(EXTRA_VOICE_REPLY);
        }
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
