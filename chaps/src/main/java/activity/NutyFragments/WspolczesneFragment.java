package activity.NutyFragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import model.Nuty;
import pl.pawelfrydrych.CHAPS.R;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Pawe³ on 2015-12-19.
 */
public class WspolczesneFragment extends ListFragment {

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    Nuty nuty;
    private ArrayList<String> listaUtworow;
    private ProgressDialog mProgressDialog;
    public String nazwa;
    public boolean done;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nuty,container,false);

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nuty = new Nuty();
        ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();

        listaUtworow = new ArrayList<String>();
        for(String key : nuty.wspolczesna.keySet())
        {
            listaUtworow.add(key);
        }

        Collections.sort(listaUtworow);

        for(int i = 0; i < nuty.wspolczesna.size(); i++)
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("textViewNuty", listaUtworow.get(i));

            resultList.add(map);

        }

        ListAdapter adapter = new SimpleAdapter(
                getActivity().getBaseContext(), resultList,
                R.layout.list_nuty_item, new String[]
                {
                        "textViewNuty"
                },
                new int[]{  R.id.textViewNuty});
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // Log.d("debug", nuty.utwory.get(listaUtworow.get(position)) );
        nazwa = listaUtworow.get(position);
        startDownload(listaUtworow.get(position),nuty.wspolczesna.get(listaUtworow.get(position)));

    }
    public void getScore(String nazwa) {
        Log.d("debug", "nazwa; " + nazwa);
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/CHAPSApp/" + nazwa + ".pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        try {
            getActivity().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No Pdf Viewer", Toast.LENGTH_LONG).show();
        }
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                this.mProgressDialog = new ProgressDialog(getActivity());
                this.mProgressDialog.setMessage("Pobieranie pliku..");
                this.mProgressDialog.setProgressStyle(1);
                this.mProgressDialog.setCancelable(true);
                this.mProgressDialog.show();
                return this.mProgressDialog;
            default:
                return null;
        }
    }

    public void startDownload(String nazwa, String link) {
        if (new File(Environment.getExternalStorageDirectory().getPath() + "/CHAPSApp/" + nazwa + ".pdf").exists()) {
            getScore(nazwa);
            return;
        }
        new DownloadAndOpenPDF().execute(new String[]{link});
    }


    class DownloadAndOpenPDF extends AsyncTask<String,String,String>
    {

        @Override
        protected void onProgressUpdate(String... values) {
            mProgressDialog.setProgress(Integer.parseInt(values[0]));
            if (values[0].equals("100")) {
                done = true;
                getScore(nazwa);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onCreateDialog(0);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                int lenghtOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/CHAPSApp/" + nazwa + ".pdf");
                byte[] data = new byte[4096];
                long total = 0;
                while (true) {
                    int count = input.read(data);
                    if (count == -1) {
                        break;
                    }
                    total += (long) count;
                    String[] strArr = new String[1];
                    strArr[0] = "" + ((int) ((100 * total) / ((long) lenghtOfFile)));
                    publishProgress(strArr);
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressDialog.dismiss();
        }
    }

}



