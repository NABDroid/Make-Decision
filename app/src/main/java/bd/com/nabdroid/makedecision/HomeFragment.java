package bd.com.nabdroid.makedecision;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class HomeFragment extends Fragment {

    //declaration
    private ImageView addVoteIV, menuIconIV, groupMenuIV;
    private RecyclerView activeVoteRecyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Vote> votes;
    private AdapertForHome adapertForHome;
    long currentDateMS, currentTimeMS;


    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        init(view);  //initialize declared elements
        findExpiredVote();
        initRecyclerView();  //initialize recyclerview
        getActiveVotes(); //get data from firebase


        return view;
    }


    //-------------------------------------------------------------------------------------------------------------------
    private void findExpiredVote() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        month = month + 1;
        String dateString = day + "/" + month + "/" + year;
        Date dateForDate = new Date();
        SimpleDateFormat simpleDateFormatForDate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateForDate = simpleDateFormatForDate.parse(dateString);
            currentDateMS = dateForDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String timeString = hour + ":" + minute;
        Date dateForTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        try {
            dateForTime = simpleDateFormat.parse(timeString);
            currentTimeMS = dateForTime.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Long totalCurrentTime = currentTimeMS + currentDateMS;
        Toast.makeText(getContext(), "MS: " + totalCurrentTime, Toast.LENGTH_LONG).show();


        DatabaseReference timeRef = databaseReference.child("Times");
        timeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        VoteTime voteTime = data.getValue(VoteTime.class);
                        if (voteTime.getEndTime() <= totalCurrentTime){
                            data.getRef().removeValue();
                            deleteVote(voteTime.getVoteId());

                        }
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void deleteVote(final int voteId) {
        DatabaseReference voteRef = databaseReference.child("Votes");
        voteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        final Vote vote = data.getValue(Vote.class);
                        if (vote.getVoteCode() == voteId){
                            data.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    publishResult(vote);
                                }
                            });
                        }
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void publishResult(Vote vote) {
        DatabaseReference resultRef = databaseReference.child("Results").child(Integer.toString(vote.getVoteCode()));
        resultRef.setValue(vote);


    }

    //initialize declared elements
    private void init(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        activeVoteRecyclerView = view.findViewById(R.id.activeVoteRecyclerViewID);
        votes = new ArrayList<>();
        adapertForHome = new AdapertForHome(votes, getContext());
    }


    //initialize recyclerview
    private void initRecyclerView() {
        activeVoteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        activeVoteRecyclerView.setAdapter(adapertForHome);
    }


    //get data from firebase
    private void getActiveVotes() {

        DatabaseReference activVoteRef = databaseReference.child("Votes");
        activVoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                votes.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Vote vote = data.getValue(Vote.class);
                        votes.add(vote);
                    }

                    adapertForHome.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



}
