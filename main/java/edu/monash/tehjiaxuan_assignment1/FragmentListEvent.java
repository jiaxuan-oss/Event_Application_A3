package edu.monash.tehjiaxuan_assignment1;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.media.metrics.Event;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import edu.monash.tehjiaxuan_assignment1.provider.EventViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListEvent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<EventEntity> eventEntityArrayList = new ArrayList<>();
    EventViewModel eventViewModel;

    public FragmentListEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListEvent newInstance(String param1, String param2) {
        FragmentListEvent fragment = new FragmentListEvent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    public ArrayList<EventEntity> addCategoryToRecyclerView(){
//        Gson gson = new Gson();
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("EVENT_LIST",MODE_PRIVATE);
//        String eventEntityStr = sharedPreferences.getString("SAVE_EVENT_LIST", "[]");
//        if(!eventEntityStr.equals("\"[]\"")){
//            Type type = new TypeToken<ArrayList<EventEntity>>(){}.getType();
//            eventEntityArrayList = gson.fromJson(eventEntityStr, type);
//        }
//
//        return eventEntityArrayList;
//
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        View view = inflater.inflate(R.layout.fragment_list_event, container, false);
        recyclerView = view.findViewById(R.id.rvEvents);
        layoutManager = new LinearLayoutManager(this.getContext());
        EventRecyclerViewAdapter adapter = new EventRecyclerViewAdapter();
        eventViewModel.getEventCards().observe(this, newData ->{
            adapter.setData(new ArrayList<EventEntity>(newData));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        });

        return view;



    }
}