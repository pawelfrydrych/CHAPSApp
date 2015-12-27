package activity;

import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import handler.XMLHandlerListaProb;
import model.TotalCalendar;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import pl.pawelfrydrych.CHAPS.R;

import java.io.IOException;


public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private String Login,Login2, Password;
    private String Url2;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private LocationManager locationMan;

    private Bundle bundle;
    private boolean autolog;
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout),mToolbar);
        drawerFragment.setDrawerListener(this);

        displayView(0);

        if(checkConnectivity(getApplicationContext()) != false)
        {
            Thread thread = new Thread()
            {
                @Override
                public void run() {
                    sendHttpRequest();
                }
            };

            thread.start();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            XMLHandlerListaProb xmlHandlerListaProb = new XMLHandlerListaProb();
            xmlHandlerListaProb.fetchXML();

            TotalCalendar calendar = new TotalCalendar(getApplicationContext());

            for(int i = 0; i < xmlHandlerListaProb.probaList.size(); i++ )
            {
                String dzien = xmlHandlerListaProb.probaList.get(i).getData().substring(0,2);
                String miesiac = xmlHandlerListaProb.probaList.get(i).getData().substring(3,5);
                String uwaga = xmlHandlerListaProb.probaList.get(i).getUwagi();
                String program = xmlHandlerListaProb.probaList.get(i).getProgram();
                String godzina = xmlHandlerListaProb.probaList.get(i).getGodzina();

                calendar.insert(dzien,miesiac,uwaga,"CHAPS",program,godzina);
            }




            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialogOnBackClose();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position)
    {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch(position)
        {
            case 0:
                fragment = new AdapterPlanFragment();

                bundle = new Bundle();

                fragment.setArguments(bundle);

                title = getString(R.string.app_name);
                break;
            case 1:

                fragment = new SimpleAuthFragment();
                bundle = new Bundle();
                fragment.setArguments(bundle);
                title = getString(R.string.app_name);

                break;
            case 2:

                fragment = new KontaktFragment();
                bundle = new Bundle();
                fragment.setArguments(bundle);
                title = getString(R.string.app_name);

                break;


            case 3:
                /*
                Intent i = new Intent(getApplicationContext(), AuthorizationActivity.class);
                savePreferences(false);
                startActivity(i);
                finish();
                break;

            default:
            */
                break;
        }

        if(fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body,fragment);
            fragmentTransaction.commit();

            getSupportActionBar().setTitle(title);

        }


    }


    public void showAlertDialogOnBackClose()
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getResources().getString(R.string.close_back_button_alert));
        dialog.setPositiveButton("Zamknij", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        dialog.setNegativeButton("Nie zamykaj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();

    }



    public void sendHttpRequest()
    {
        String urlKoledy = "http://chaps.zut.edu.pl/Nuty/chapsapp/koledy/koledy.php";
        String urlLudowa = "http://chaps.zut.edu.pl/Nuty/chapsapp/ludowa/ludowa.php";
        String urlRozrywkowa = "http://chaps.zut.edu.pl/Nuty/chapsapp/rozrywkowa/rozrywkowa.php";
        String urlSakralna = "http://chaps.zut.edu.pl/Nuty/chapsapp/sakralna/sakralna.php";
        String urlWspolczesna = "http://chaps.zut.edu.pl/Nuty/chapsapp/wspolczesna/wspolczesna.php";
        HttpClient client = new DefaultHttpClient();

        try{
            client.execute(new HttpGet(urlKoledy));
            client.execute(new HttpGet(urlLudowa));
            client.execute(new HttpGet(urlRozrywkowa));
            client.execute(new HttpGet(urlSakralna));
            client.execute(new HttpGet(urlWspolczesna));

        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public boolean checkConnectivity(Context context) {
        try {
            ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetwork = connect.getNetworkInfo(1);
            NetworkInfo MOBILEnetwork = connect.getNetworkInfo(0);
            NetworkInfo activeNetwork = connect.getActiveNetworkInfo();
            if (MOBILEnetwork != null && MOBILEnetwork.isConnected()) {
                return true;
            }
            if (wifiNetwork == null || !wifiNetwork.isConnected()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            Log.d("debug", "network error");
            Log.d("debug", "Mobile network and Wifi network isn't works.");
            return false;
        }
    }

}
