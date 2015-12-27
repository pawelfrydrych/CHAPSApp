package services;

import activity.WidgetActivity;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RemoteViews;
import handler.XMLHandlerListaProb;
import org.joda.time.DateTime;
import pl.pawelfrydrych.CHAPS.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Pawe³ on 2015-12-20.
 */
public class UpdateWidgetService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        buildUpdate();
        return super.onStartCommand(intent, flags, startId);
    }

    private void buildUpdate() {

        XMLHandlerListaProb obj = new XMLHandlerListaProb();
        obj.fetchXML();

        String lastUpdated = DateFormat.format("MMMM dd, yyyy h:mmaa", new Date()).toString();

        RemoteViews view = new RemoteViews(getPackageName(), R.layout.mywidgetlayout);


        String data = nastepnaProba(obj.listaDat);
        for(int i = 0; i < obj.probaList.size(); i++)
        {
            if(data.equals(obj.probaList.get(i).getData()))
            {
                view.setTextViewText(R.id.dzien, obj.probaList.get(i).getDzien() +" " + obj.probaList.get(i).getData());
                view.setTextViewText(R.id.godzina, obj.probaList.get(i).getGodzina());
                view.setTextViewText(R.id.rodzaj, obj.probaList.get(i).getRodzaj());
                view.setTextViewText(R.id.program, obj.probaList.get(i).getProgram());
                view.setTextViewText(R.id.uwagi, obj.probaList.get(i).getUwagi());
            }
        }


        // Push update for this widget to the home screen
        ComponentName thisWidget = new ComponentName(this, WidgetActivity.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);

        manager.updateAppWidget(thisWidget, view);
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public String nastepnaProba(ArrayList<String> dniProb)
    {

        DateTime timePoint = new DateTime();
        int currentDay = timePoint.getDayOfMonth();
        int currentMonth = timePoint.getMonthOfYear();
        String wynik = "";


        for(int i = 0; i < dniProb.size(); i++)
        {

            String[] tab = dniProb.get(i).split("\\.");

            int[] tab2 = new int[2];
            tab2[0] = Integer.parseInt(tab[0]);
            tab2[1] = Integer.parseInt(tab[1]);


            if(tab2[1] >= currentMonth)
            {
                if(tab2[0] >= currentDay)
                {
                    if(tab2[0] > 0 && tab2[0] < 10)
                    {
                        if(tab2[1] > 0 && tab2[1] < 10)
                        {
                            wynik = "0"+tab2[0] + ".0" + tab2[1];
                        }
                    }else
                    {
                        wynik = tab2[0] + "." + tab2[1];
                    }
                    break;
                }
            }else
            {
                if( currentMonth == 12 )
                {
                    i=0;
                    currentMonth = 1;
                    currentDay = 1;

                    if( tab2[1] == 1)
                    {
                        if( tab2[1] >= 1 && tab2[0] >= 1)
                        {

                            if(tab2[0] > 0 && tab2[0] < 10)
                            {
                               if(tab2[1] > 0 && tab2[1] < 10)
                               {
                                   wynik = "0"+tab2[0] + ".0" + tab2[1];
                               }
                            }else
                            {
                                wynik = tab2[0] + "." + tab2[1];
                            }


                            break;
                        }
                    }
                }
            }


        }

        Log.d("debug", "Nastepna proba: " + wynik);
        return wynik;
    }
}
