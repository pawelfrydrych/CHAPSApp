package activity;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import pl.pawelfrydrych.CHAPS.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DeviceListFragment extends ListFragment {

  //  private ArrayList<MyMarker> Markers;
    private String URL;
    private ArrayList<HashMap<String,String>> resultList;
    public DeviceListFragment()
    {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("debug","onActivityCreated DeviceListFragment");
        resultList = new ArrayList<HashMap<String, String>>();


        Bundle bundle = this.getArguments();
     //   Markers = (ArrayList<MyMarker>) bundle.getSerializable("Markers");
        URL = bundle.getString("URL");


    //    for(int i = 0; i < Markers.size(); i++)
      //  {
            /*
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("id", "ID: "+Markers.get(i).getID() + "");
            map.put("lat", "Lat: "+Markers.get(i).getPosition().latitude + "");
            map.put("lon", "Lon: "+Markers.get(i).getPosition().longitude +"");
            map.put("level" , "Level: "+Markers.get(i).getLevel() + "");
            map.put("name", "Name: " + Markers.get(i).getName() + "");

            resultList.add(map);
            */
    //    }

/*

        ListAdapter adapter = new SimpleAdapter(
                getActivity().getBaseContext(), resultList,
                R.layout.list_item, new String[]
                {
                        "id" , "lat", "lon", "level", "name"
                },
                new int[]{ R.id.id, R.id.lon, R.id.lat, R.id.level, R.id.name});
        setListAdapter(adapter);*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_devicelist,container,false);

        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        /*
        Fragment fragment = new SingleJSONFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("MyMarker",Markers.get(position));
        bundle.putSerializable("Markers", Markers);
        bundle.putString("URL",URL);

        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_body, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        */
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }




}
