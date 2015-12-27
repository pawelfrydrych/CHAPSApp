package activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import services.UpdateWidgetService;

import java.util.Calendar;

/**
 * Created by Pawe³ on 2015-12-20.
 */
public class WidgetActivity extends AppWidgetProvider {
    public static final String ACTION_AUTO_UPDATE = "AUTO_UPDATE";
    private PendingIntent service = null;


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);


        final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        final Calendar TIME = Calendar.getInstance();
        TIME.set(Calendar.HOUR_OF_DAY,0);
        TIME.set(Calendar.MINUTE, 0);
        TIME.set(Calendar.SECOND, 0);
        TIME.set(Calendar.MILLISECOND, 0);

        final Intent i = new Intent(context, UpdateWidgetService.class);

        if (service == null)
        {
            service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        }

        m.setRepeating(AlarmManager.RTC_WAKEUP, TIME.getTime().getTime(), AlarmManager.INTERVAL_DAY, service);



    }

}
