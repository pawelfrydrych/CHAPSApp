package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import handler.XMLHandler;
import model.Kalendarz;
import pl.pawelfrydrych.CHAPS.R;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class AdapterPlanFragment extends ListFragment{

    private XMLHandler obj;
    private ArrayList<HashMap<String,String>> resultList;
    public AdapterPlanFragment()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(checkConnectivity(getActivity()) != true)
        {
            File testDirectory = new File(Environment.getExternalStorageDirectory() + "/CHAPSApp/plan.xml");
            if(!testDirectory.exists())
            {
               showAlertInternetDialog();
            }else
            {
                new DownloadFile().execute();
            }
        }else
        {
            new DownloadFile().execute();
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_plan,container,false);


        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("debug","position: "  + i );

                if (android.os.Build.VERSION.SDK_INT < 14) {
                    Toast.makeText(getActivity(), "Kalendarz wymaga nowszej wersji Androida.", Toast.LENGTH_LONG).show();

                } else {
                    Kalendarz calendar = new Kalendarz(getActivity());

                    String dzien = obj.probaList.get(i).getData().substring(0,2);
                    String miesiac = obj.probaList.get(i).getData().substring(3,5);
                    String uwaga = obj.probaList.get(i).getUwagi();
                    String program = obj.probaList.get(i).getProgram();
                    String godzina = obj.probaList.get(i).getGodzina();

                    calendar.insert(dzien,miesiac,uwaga,"CHAPS",program,godzina);

                }

                return true;
            }
        });


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }






    public void DownloadFiles(){

        try {
            URL url = new URL("http://chaps.zut.edu.pl/portal/xml/proby.xml");
            URLConnection conexion = url.openConnection();
            conexion.connect();
            int lenghtOfFile = conexion.getContentLength();
            InputStream is = url.openStream();
            File testDirectory = new File(Environment.getExternalStorageDirectory() + "/CHAPSApp");
            if (!testDirectory.exists()) {
                testDirectory.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(testDirectory + "/plan.xml");
            byte data[] = new byte[1024];
            int count = 0;
            long total = 0;
            int progress = 0;
            while ((count = is.read(data)) != -1) {
                total += count;
                int progress_temp = (int) total * 100 / lenghtOfFile;
                if (progress_temp % 10 == 0 && progress != progress_temp) {
                    progress = progress_temp;
                }
                fos.write(data, 0, count);
            }
            is.close();
            fos.close();

            Log.d("debug","Pobrano plan");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }





    private class DownloadFile extends AsyncTask<String,Void,String>
    {

        private ProgressDialog progressDialog;

        public DownloadFile()
        {
            progressDialog = new ProgressDialog(getActivity());

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog.setMessage("Ladowanie..");
            this.progressDialog.show();

        }


        @Override
        protected String doInBackground(String... strings) {
            DownloadFiles();

            obj = new XMLHandler();
            obj.fetchXML();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    resultList = new ArrayList<HashMap<String, String>>();


                    for(int i = 0; i < obj.probaList.size(); i++)
                    {

                        HashMap<String,String> map = new HashMap<String, String>();
                        map.put("imageViewPlan", Integer.toString(R.drawable.ic_launcher));
                        map.put("dzien", obj.probaList.get(i).getDzien() + " " + obj.probaList.get(i).getData());
                        map.put("godzina", obj.probaList.get(i).getGodzina());
                        map.put("rodzaj" , obj.probaList.get(i).getRodzaj());
                        map.put("program", obj.probaList.get(i).getProgram());
                        map.put("uwagi",obj.probaList.get(i).getUwagi());

                        resultList.add(map);

                    }

                    ListAdapter adapter = new SimpleAdapter(
                            getActivity().getBaseContext(), resultList,
                            R.layout.list_item, new String[]
                            {
                                    "imageViewPlan" , "dzien", "godzina", "rodzaj", "program", "uwagi"
                            },
                            new int[]{  R.id.imageViewPtak, R.id.dzien, R.id.godzina, R.id.rodzaj, R.id.program, R.id.uwagi});
                    setListAdapter(adapter);

                }
            });



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
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

    public void showAlertInternetDialog()
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage(getResources().getString(R.string.map_dialog_location));
        dialog.setPositiveButton("Ustawienia", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent myIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(myIntent);
            }
        });
        dialog.setNegativeButton(getResources().getString(R.string.wyjdz), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();

    }


}
