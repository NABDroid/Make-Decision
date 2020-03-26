package bd.com.nabdroid.makedecision;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    private ImageView logOutIV;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private TextView userNameTV, emailTV, mobileTV, dobTV;
    private ImageView userImageIV;
    private String uId;
    private RecyclerView historyRecyclerView;
    private Context context;
    private AdaptarForUserHistory adaptarForUserHistory;
    private ArrayList<Vote> votes;

    public ProfileFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        init(view);
        initRecyclerView();
        showUserData();
        getHistoryFromDB();


        logOutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), ActivityLogIn.class));
            }
        });






        return view;
    }

    private void getHistoryFromDB() {
        final DatabaseReference historyRef = databaseReference.child("UserInfo").child(uId).child("myVotes");
        historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                votes.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Vote vote = data.getValue(Vote.class);
                        votes.add(vote);
                    }

                    adaptarForUserHistory.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init(View view) {
        firebaseAuth = FirebaseAuth.getInstance();
        uId = firebaseAuth.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        votes = new ArrayList<>();
        logOutIV = view.findViewById(R.id.logOutIV);
        userNameTV = view.findViewById(R.id.userNamePFTV);
        emailTV = view.findViewById(R.id.emailPFTV);
        mobileTV = view.findViewById(R.id.mobileNumberPFTV);
        userImageIV = view.findViewById(R.id.userImagePFIV);
        dobTV = view.findViewById(R.id.dobPFTV);
        historyRecyclerView = view.findViewById(R.id.userHistoryRecyclerView);

    }

    private void initRecyclerView() {
        adaptarForUserHistory = new AdaptarForUserHistory(votes, context);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        historyRecyclerView.setAdapter(adaptarForUserHistory);
    }




    private void showUserData() {
        DatabaseReference userInfo = databaseReference.child("UserInfo").child(uId);
        userInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userNameTV.setText(dataSnapshot.child("userName").getValue().toString());
                emailTV.setText(dataSnapshot.child("email").getValue().toString());
                mobileTV.setText(dataSnapshot.child("mobileNumber").getValue().toString());
                dobTV.setText(dataSnapshot.child("dateOfBirth").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
