package com.shoppin.customer.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.shoppin.customer.R;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.utils.IConstants.IPushNotification;
import com.shoppin.customer.utils.Utils;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by ubuntu on 28/1/16.
 */
public class MyPushNotification {

    private static final String TAG = MyPushNotification.class.getSimpleName();

    private Bitmap remoteImg;
    private Context context;
    private JSONObject pushDataJObject;
    private NotificationCompat.Builder notificationBuilder;


    public MyPushNotification(Context context, String pushData) {
        try {
            this.context = context;
            this.pushDataJObject = new JSONObject(pushData);
            this.notificationBuilder = new NotificationCompat.Builder(
                    context);
            notificationBuilder
                    .setSmallIcon(getNotificationIcon())
                    .setContentTitle(pushDataJObject.getString(IPushNotification.TTITLE))
                    .setContentText(pushDataJObject.getString(IPushNotification.MESSAGE))
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setSound(RingtoneManager
                            .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setColor(Utils.getColor(context, R.color.app_theme_1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getNotificationIcon() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(Utils.getColor(context, R.color.app_theme_1));
        }
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
//        return useWhiteIcon ? R.drawable.notification_icon : R.mipmap.app_icon;
        //TODO
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }

    public void generateNotification() {
        try {
            if (pushDataJObject.has(IPushNotification.IMAGE)) {
                new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Void... params) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = BitmapFactory.decodeStream((InputStream) new URL(pushDataJObject.getString(IPushNotification.IMAGE)).getContent());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return bitmap;
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        remoteImg = bitmap;
                        whichNotification();
                    }
                }.execute();
            } else {
                Log.d(TAG, "else");
                whichNotification();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void whichNotification() {
        try {
            if (pushDataJObject.has(IPushNotification.NOTIFICATION_TYPE)) {
                if (pushDataJObject.getString(IPushNotification.NOTIFICATION_TYPE).equals(IPushNotification.NOTIFICATION_TYPE_OFFER)) {
                    newPollNotification();
                } else {
                    defaultNotification();
                }
            } else {
                defaultNotification();
            }

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
// Single notification
//            notificationManager.notify(0 /* ID of notification */,
//                    notificationBuilder.build());

//  Multiple notification
            notificationManager.notify((int) System.currentTimeMillis(),
                    notificationBuilder.build());

            Log.d(TAG, "notificationManager");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void defaultNotification() {
        try {
            Intent notificationIntent = new Intent(context,
                    NavigationDrawerActivity.class);
            notificationIntent.putExtra(IPushNotification.APP_LAUNCH_TYPE,
                    IPushNotification.APP_LAUNCH_NOTIFICATION);

//          Re-launch app
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0 /*
                         * Request code
                         */, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//          Single instance of app
//            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    | Intent.FLAG_ACTIVITY_SINGLE_TOP
//                    | Intent.FLAG_ACTIVITY_NEW_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context,
//                    0 /*
//                     * Request code
//					 */, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

//            remoteImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_bg);
            if (remoteImg != null) {
                Log.d(TAG, "defaultNotification remoteImg not null");
                NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
                bigPictureStyle.setBigContentTitle(pushDataJObject.getString(IPushNotification.TTITLE));
                bigPictureStyle.setSummaryText(pushDataJObject.getString(IPushNotification.MESSAGE));
                bigPictureStyle.bigPicture(remoteImg);
                notificationBuilder.setContentIntent(pendingIntent).setStyle(bigPictureStyle);
            } else {
                Log.d(TAG, "defaultNotification remoteImg null ");
                NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                bigTextStyle.setBigContentTitle(pushDataJObject.getString(IPushNotification.TTITLE));
                bigTextStyle.bigText(pushDataJObject.getString(IPushNotification.MESSAGE));
                bigTextStyle.setSummaryText(context.getString(R.string.app_name));
                notificationBuilder.setContentIntent(pendingIntent).setStyle(bigTextStyle);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void newPollNotification() {
        try {
            Intent notificationIntent = new Intent(context,
                    NavigationDrawerActivity.class);

            notificationIntent.putExtra(IPushNotification.APP_LAUNCH_TYPE,
                    IPushNotification.APP_LAUNCH_NOTIFICATION);
            notificationIntent.putExtra(IPushNotification.PUSHNOTIFICATION_DATA,
                    pushDataJObject.toString());

//          Re-launch app
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0 /*
                     * Request code
					 */, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            bigTextStyle.setBigContentTitle(pushDataJObject.getString(IPushNotification.TTITLE));
            bigTextStyle.bigText(pushDataJObject.getString(IPushNotification.MESSAGE));
            bigTextStyle.setSummaryText(" - " + context.getString(R.string.app_name));

            notificationBuilder.setContentIntent(pendingIntent).setStyle(bigTextStyle);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
