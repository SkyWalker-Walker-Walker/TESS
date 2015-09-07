package jp.co.skai.tess;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;



/**
 * A simple {@link Fragment} subclass.
 */
public class PrefecturesFragment extends Fragment {


    public PrefecturesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_prefectures, container, false);

        //ListView list = (ListView)root.findViewById(R.id.listView2);

        //list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
        //    @Override
        //    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //        ListView listView = (ListView)parent;

        //        String item = (String)listView.getItemAtPosition(position);
        //       Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
        //    }
        //});

        return  root;
    }


}
