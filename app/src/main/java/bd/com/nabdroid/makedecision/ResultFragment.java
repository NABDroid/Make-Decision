package bd.com.nabdroid.makedecision;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultFragment extends Fragment {
    private RecyclerView resultRecyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<Vote> votes;
    private AdapterForResult adapterForResult;


    public ResultFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_result, container, false);
        init(view);
        initRecyclerView();
        getResults();



        return view;
    }

    private void initRecyclerView() {
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultRecyclerView.setAdapter(adapterForResult);
    }

    private void getResults() {
        DatabaseReference resultsRef = databaseReference.child("Results");
        resultsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                votes.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Vote vote = data.getValue(Vote.class);
                        votes.add(vote);
                    }

                    adapterForResult.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init(View view) {
        resultRecyclerView = view.findViewById(R.id.resultRecyclerView);
        votes = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        adapterForResult = new AdapterForResult(votes, getContext());
    }

}
