package bd.com.nabdroid.makedecision;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.Date;

public class AskOpinionFragment extends Fragment {
    private EditText voteTopicET;
    private Button startVoteBTN;
    private TextView pickDateTV, pickTimeTV;
    private CheckBox checkBoxForNotification;
    private String voteTopic, creatorName, creatorId, endTimeString;
    private long timeFromDatePicker, timeFromTimePicker;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String timeString, dateString;




    public AskOpinionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_ask_opinion, container, false);
        init(view);
        getCreatorName();
        pickDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });
        pickTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime();
            }
        });


        startVoteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getDataFromField();
                getUniqueVoteId();
//                startActivity(new Intent(PostVoteActivity.this, HomeActivity.class));


            }
        });





        return view;
    }

    private void init(View view) {
        voteTopicET = view.findViewById(R.id.voteTopicPVETID);
        startVoteBTN = view.findViewById(R.id.startVotePVBTN);
        pickDateTV = view.findViewById(R.id.pickDatePVTVID);
        pickTimeTV = view.findViewById(R.id.pickTimePVTVID);
        checkBoxForNotification = view.findViewById(R.id.checkboxPVID);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        creatorId = firebaseAuth.getCurrentUser().getUid();

    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------
    private void getCreatorName() {
        final DatabaseReference userInfoRef = databaseReference.child("UserInfo").child(creatorId);
        userInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                creatorName = dataSnapshot.child("userName").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    //-------------------------------------------------------------------------------------------------------------------------------------------------------
    private void pickTime() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeString = i + ":" + i1;
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
                try {
                    date = simpleDateFormat.parse(timeString);
                    timeFromTimePicker = date.getTime();
                    pickTimeTV.setText(date.toString() + "///" + timeFromTimePicker);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };

        Calendar mTime = Calendar.getInstance();
        int hour = mTime.get(Calendar.HOUR_OF_DAY);
        int minute = mTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, hour, minute, false);
        timePickerDialog.show();


    }


    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    public void pickDate() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dateString = dayOfMonth + "/" + month + "/" + year;
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    date = simpleDateFormat.parse(dateString);
                    timeFromDatePicker = date.getTime();
                    pickDateTV.setText(date.toString() + "///" + timeFromDatePicker);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), onDateSetListener, year, month, day);
        datePickerDialog.show();

    }


    //-------------------------------------------------------------------------------------------------------------------------------------------------------
    private void getDataFromField() {
        voteTopic = voteTopicET.getText().toString().trim();
    }


    //-------------------------------------------------------------------------------------------------------------------------------------------------------

    private void getUniqueVoteId() {
        final int[] idForNewVote = new int[1];
        final DatabaseReference totalVoteRef = databaseReference.child("TotalVotes");
        totalVoteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String previousVoteID = dataSnapshot.getValue().toString();
                int lastVoteNumber = Integer.parseInt(previousVoteID);
                idForNewVote[0] = lastVoteNumber + 1;
                totalVoteRef.setValue(idForNewVote[0]);
                Toast.makeText(getContext(), "OnDataChange: " + idForNewVote[0], Toast.LENGTH_SHORT).show();
                postVote(idForNewVote[0]);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to count", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postVote(int i) {
        final long endTime = timeFromDatePicker + timeFromTimePicker;
        final int idForNewVote = i;
        endTimeString = ""+dateString+" "+timeString+"";
        Vote vote = new Vote(idForNewVote, voteTopic, creatorId, creatorName, endTime, 0, 0, endTimeString);

        DatabaseReference userRef = databaseReference.child("Votes").child(String.valueOf(idForNewVote));
        userRef.setValue(vote).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "Posted successfully", Toast.LENGTH_SHORT).show();
                DatabaseReference timeRef = databaseReference.child("Times").child(Integer.toString(idForNewVote));
                VoteTime voteTime = new VoteTime(endTime, idForNewVote);
                timeRef.setValue(voteTime);
            }
        });

    }



}
