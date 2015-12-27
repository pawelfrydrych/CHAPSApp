package activity;

import activity.NutyFragments.*;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import model.KategorieNut;
import pl.pawelfrydrych.CHAPS.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Pawe³ on 2015-12-19.
 */
public class PodzialNutFragment extends ListFragment {

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    KategorieNut kategorieNut;
    private ArrayList<String> listaKategorii;
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

        kategorieNut = new KategorieNut();
        ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();

        listaKategorii = new ArrayList<String>();
        for(String key : kategorieNut.kategorie.keySet())
        {
            listaKategorii.add(key);
        }


        Collections.sort(listaKategorii);

        for(int i = 0; i < kategorieNut.kategorie.size(); i++)
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("textViewNuty", listaKategorii.get(i));

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

        Fragment fragment = null;


        nazwa = listaKategorii.get(position);
        Log.d("debug", nazwa);


        switch (position)
        {
            case 0:
                fragment = new KoledyFragment();
                break;

            case 1:
                fragment = new LudoweFragment();
                break;

            case 2:
                fragment = new RozrywkoweFragment();
                break;

            case 3:
                fragment = new SakralneFragment();
                break;

            case 4:
                fragment = new WspolczesneFragment();
                break;
        }

        if(fragment != null)
        {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body,fragment);
            fragmentTransaction.commit();

        }


    }





}



