package com.money.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.money.app.models.Ask;
import com.money.app.models.DataModel;
import com.money.app.models.Price;
import com.money.app.models.UpdateModel;
import com.money.app.servers.Constant;
import com.money.app.servers.Requestor;
import com.money.app.servers.ServerResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service implements ServerResponse {
    public static final long NOTIFY_INTERVAL = 1 * 1000; // 10 seconds

    private static final String TAG = MyService.class.getSimpleName();
    private final static String FOREGROUND_CHANNEL_ID = "foreground_channel_id";
    private NotificationManager mNotificationManager;
    private static int stateService = Constants.STATE_SERVICE.NOT_CONNECTED;
    List<UpdateModel> updateModels = new ArrayList<>();
    boolean isTimerStarted = false;
    /*
        private double oldPrice;
        private double newPrice;

        private long timeValue;
        private double upValue;
        private double downValue;*/
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    LocalBroadcastManager localBroadcastManager;
    DecimalFormat decimalFormat = new DecimalFormat("#.#####");

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (localBroadcastManager == null)
            localBroadcastManager = LocalBroadcastManager.getInstance(this);

        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        stateService = Constants.STATE_SERVICE.NOT_CONNECTED;
    }

    @Override
    public void onDestroy() {
        stateService = Constants.STATE_SERVICE.NOT_CONNECTED;
        if (mTimer != null) mTimer.cancel();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            stopForeground(true);
            stopSelf();
            return START_NOT_STICKY;
        }

        // if user starts the service
        switch (intent.getAction()) {
            case Constants.ACTION.START_ACTION:
                Log.d(TAG, "Received user starts foreground intent");

                handleNewRequest(intent);
                if (!isTimerStarted)
                    connect();
                isTimerStarted = true;
                break;
            case Constants.ACTION.STOP_ACTION:
                if (updateModels.size() > 0)
                    if (updateModels.size() == 1) {
                        updateModels.remove(0);
                        stopForeground(true);
                        stopSelf();
                    } else {
                        int i = -1;
                        for (UpdateModel updateModel : updateModels) {
                            i++;
                            if (updateModel.getInstruement().equals(intent.getStringExtra("pair"))) {
                                updateModels.remove(i);
                                break;
                            }
                        }
                    }
                break;
            default:
                stopForeground(true);
                stopSelf();
        }

        return START_NOT_STICKY;
    }

    private void handleNewRequest(Intent intent) {
        if (intent.hasExtra("pair")) ;
        {
            for (UpdateModel um : updateModels)
                if (um.getInstruement().equals(intent.getStringExtra("pair"))) {
                    return;
                }
            UpdateModel updateModel = new UpdateModel();
            updateModel.setInstruement(intent.getStringExtra("pair"));
            updateModels.add(updateModel);
        }
    }

    // its connected, so change the notification text
    private void connect() {
        // after 5 seconds its connected
        stateService = Constants.STATE_SERVICE.CONNECTED;
        startForeground(Constants.NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification());
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);

/*        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Log.d(TAG, "Bluetooth Low Energy device is connected!!");
                        Toast.makeText(getApplicationContext(), "Connected!", Toast.LENGTH_SHORT).show();
                        stateService = Constants.STATE_SERVICE.CONNECTED;
                        startForeground(Constants.NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification());
                    }
                }, 10000);*/

    }


    private Notification prepareNotification() {
        // handle build version above android oreo
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O &&
                mNotificationManager.getNotificationChannel(FOREGROUND_CHANNEL_ID) == null) {
            CharSequence name = getString(R.string.text_name_notification);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(FOREGROUND_CHANNEL_ID, name, importance);
            channel.enableVibration(false);
            mNotificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(this, SelectedCurrencyActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // if min sdk goes below honeycomb
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }*/

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // make a stop intent
        Intent stopIntent = new Intent(this, MyService.class);
        stopIntent.setAction(Constants.ACTION.STOP_ACTION);
        PendingIntent pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        remoteViews.setOnClickPendingIntent(R.id.btn_stop, pendingStopIntent);

        // if it is connected
        switch (stateService) {
            case Constants.STATE_SERVICE.NOT_CONNECTED:
                remoteViews.setTextViewText(R.id.tv_state, "DISCONNECTED");
                break;
            case Constants.STATE_SERVICE.CONNECTED:
                remoteViews.setTextViewText(R.id.tv_state, "CONNECTED");
                break;
        }

        // notification builder
        NotificationCompat.Builder notificationBuilder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationBuilder = new NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID);
        } else {
            notificationBuilder = new NotificationCompat.Builder(this);
        }
        notificationBuilder
                .setContent(remoteViews)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
        }

        return notificationBuilder.build();
    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case Constant.REQUESTCODE_1:
                DataModel dataModel = (DataModel) o;
                if (dataModel != null) {
                    if (dataModel.getPrices() != null) {

                        for (int i = 0; i < dataModel.getPrices().size(); i++) {
                            Price price = dataModel.getPrices().get(i);
                            if (price != null) {
                                List<Ask> asks = price.getAsks();
                                if (asks != null) {
                                    Ask ask = asks.get(0);
                                    try {
                                        int index = -1;
                                        for (UpdateModel model : updateModels) {
                                            index++;
                                            if (model.getInstruement().equals(price.getInstrument()))
                                                break;

                                        }

                                        if (index > -1) {

                                            //   double newPrice = updateModels.get(index).getNewPrice();
                                            double oldPrice = updateModels.get(index).getOldPrice();
                                            double downValue = updateModels.get(index).getDownValue();
                                            double upValue = updateModels.get(index).getUpValue();
                                            long timeValue = updateModels.get(index).getTimeValue();
                                            String currenyPair = updateModels.get(index).getInstruement();

                                            double newPrice = Double.parseDouble(ask.getPrice());

                                            //find out multiplier
                                            int digitCount = String.valueOf(newPrice).split("\\.")[1].length();
                                            int multiplier = 1;
                                            for (int h = 1; h <= digitCount; h++) {
                                                multiplier *= 10;
                                            }

                                            //calculate difference
                                            double difference = newPrice - oldPrice;
                                            difference = Double.parseDouble(decimalFormat.format(difference));
                                            difference = difference * multiplier;

                                            long difference_final = Math.round(difference);

                                            if (oldPrice > 0)//skiping first request
                                                if (difference_final > 0) {
                                                    upValue += difference_final;
                                                }
                                            if (difference_final < 0) {
                                                downValue += Math.abs(difference_final);

                                            }

                                            oldPrice = newPrice;



                                            /*double newPrice = updateModels.get(index).getNewPrice();
                                            double oldPrice = updateModels.get(index).getOldPrice();
                                            double downValue = updateModels.get(index).getDownValue();
                                            double upValue = updateModels.get(index).getUpValue();


                                            newPrice = Double.parseDouble(ask.getPrice());
                                            if (oldPrice != 0)
                                                if (oldPrice > newPrice) {
                                                    double diff = oldPrice - newPrice;
                                                    downValue = downValue + diff;
                                                } else {
                                                    double diff = newPrice - oldPrice;
                                                    upValue = upValue + diff;
                                                }*/

                                            updateModels.get(index).setNewPrice(newPrice);
                                            updateModels.get(index).setOldPrice(oldPrice);
                                            updateModels.get(index).setDownValue(downValue);
                                            updateModels.get(index).setUpValue(upValue);

                                            // Create intent with action
                                            Intent localIntent = new Intent("mydata");

                                            localIntent.putExtra("price", ask.getPrice());
                                            localIntent.putExtra("down", downValue);
                                            localIntent.putExtra("up", upValue);
                                            localIntent.putExtra("pair", price.getInstrument());
                                            localIntent.putExtra("time", updateModels.get(index).getTimeValue());

                                            Log.d("recievedpair",price.getInstrument());

                                            localBroadcastManager.sendBroadcast(localIntent);
                                         //   Thread.sleep(100);
                                            Log.d("currentprice", ask.getPrice() + "");

                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            }

                        }//loop price

                    }
                }

        }
    }

    @Override
    public void error(Object o, int code) {

    }


    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {

            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    StringBuilder builder = new StringBuilder();
                    for (UpdateModel model : updateModels) {
                        model.setTimeValue(model.getTimeValue() + 1);
                        builder.append(model.getInstruement() + "%2C");
                    }

                    Requestor requestor = new Requestor(Constant.REQUESTCODE_1, MyService.this);
                    requestor.setClassOf(DataModel.class);
                    requestor.requestSendToServer(Requestor.apis.getCurrencydata("https://api-fxpractice.oanda.com/v3/accounts/101-001-11208661-001/pricing?instruments=" + builder.toString()));

                }

            });
        }
    }
}
