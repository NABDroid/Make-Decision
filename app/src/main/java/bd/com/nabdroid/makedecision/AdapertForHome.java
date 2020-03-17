package bd.com.nabdroid.makedecision;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapertForHome extends RecyclerView.Adapter<AdapertForHome.ViewHolder> {

    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ArrayList<Vote> votes;
    private Context context;
    String commentWriterName;
    String currentUserId = firebaseAuth.getCurrentUser().getUid();

    public AdapertForHome(ArrayList<Vote> votes, Context context) {
        this.votes = votes;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapertForHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_vote, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapertForHome.ViewHolder holder, int position) {

        //variables
        final String topic, creatorId, creatorName, endTime;
        final int  yesVote, noVote;
        final int uniqueCode;


        //collecting commentWritenName
        collectCommentWriterName();
        //name collected


        //collecting form HomeActivity
        final Vote vote = votes.get(position);
        uniqueCode = vote.getVoteCode();
        topic = vote.getTopic();
        creatorId = vote.getCreatorId();
        creatorName = vote.getCreatorName();
        endTime = vote.getEndtimeString();
        yesVote = vote.getYesVote();
        noVote = vote.getNoVote();


        //showing data to List
        holder.userNameTV.setText(creatorName);
        holder.topicTV.setText(topic);
        holder.titleTV.setText(Integer.toString(uniqueCode));
        holder.lifetimeTV.setText(endTime);
        String agreedVote = Integer.toString(vote.getYesVote());
        String disagreedVote = Integer.toString(vote.getNoVote());
        holder.voteCountTV.setText(agreedVote + " people agreed with you and " + disagreedVote + " people disagreed with you!");


        //sending vote and comment data to db
        holder.submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //get selected option from radioGroup
                int voteId = holder.radioGroup.getCheckedRadioButtonId();

                //if agreed
                if (voteId == R.id.agreeRBHAUI) {
                    final int newVote = yesVote + 1;

                    //upgrade yes vote to database
                    DatabaseReference currentNodeRef = databaseReference.child("Votes").child(Integer.toString(uniqueCode)).child("yesVote");
                    currentNodeRef.setValue(newVote).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String agreedVoteForShow = Integer.toString(newVote);
                            String disagreedVoteForShow = Integer.toString(vote.getNoVote());
                            holder.voteCountTV.setText(agreedVoteForShow + " people agreed with you and " + disagreedVoteForShow + " people disagreed with you!");

                            //collecting comment and necessary data for comment
                            String commentFor = "Yes";
                            String comment = holder.commentET.getText().toString().trim();
                            String creationTime = "Time Unknown";


                            Comment commentObj = new Comment(commentWriterName, creatorId, commentFor, comment, creationTime);


                            //pushing comment into database
                            DatabaseReference commentRef = databaseReference.child("Comment").child(Integer.toString(uniqueCode)).child(currentUserId);
                            commentRef.setValue(commentObj);

                        }
                    });
                }

                //if disagreed
                else if (voteId == R.id.disagreeRBHAUI) {
                    int newVote = noVote + 1;

                    //upgrade no vote to database
                    DatabaseReference currentNodeRef = databaseReference.child("Votes").child(Integer.toString(uniqueCode)).child("noVote");
                    currentNodeRef.setValue(newVote).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String agreedVote = Integer.toString(vote.getYesVote());
                            String disagreedVote = Integer.toString(vote.getNoVote());
                            holder.voteCountTV.setText(agreedVote + " people agreed with you and " + disagreedVote + " people disagreed with you!");


                            //collecting comment and necessary data for comment
                            String currentUserId = firebaseAuth.getCurrentUser().getUid();
                            String commentFor = "For No";
                            String comment = holder.commentET.getText().toString().trim();
                            String creationTime = "Time Unknown";


                            Comment commentObj = new Comment(commentWriterName, creatorId, commentFor, comment, creationTime);


                            //pushing comment into database
                            DatabaseReference commentRef = databaseReference.child("Comment").child(Integer.toString(uniqueCode)).child(currentUserId);
                            commentRef.setValue(commentObj);



                        }
                    });
                }


                if (votes != null && votes.size() > 0) {
                    votes.clear();
                }


                //hiding buttons and comment box
                holder.radioGroup.setVisibility(View.GONE);
                holder.commentET.setVisibility(View.GONE);
                holder.submitBTN.setVisibility(View.GONE);

            }


        });


    }

    private void collectCommentWriterName() {
        final DatabaseReference userInfoRef = databaseReference.child("UserInfo").child(currentUserId);
        userInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentWriterName = dataSnapshot.child("userName").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return votes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTV, titleTV, topicTV, voteCountTV, lifetimeTV;
        private EditText commentET;
        private Button submitBTN;
        private RadioGroup radioGroup;
        private ImageView commentIV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTV = itemView.findViewById(R.id.userNameTVHAUI);
            titleTV = itemView.findViewById(R.id.titleTVHAUI);
            topicTV = itemView.findViewById(R.id.topicTVHAUI);
            lifetimeTV = itemView.findViewById(R.id.lifetimeTVHAUI);
            commentET = itemView.findViewById(R.id.commentETHAUI);
            submitBTN = itemView.findViewById(R.id.submitVoteBTNHAUI);
            radioGroup = itemView.findViewById(R.id.radioGRPHAUI);
            voteCountTV = itemView.findViewById(R.id.voteCountTVHAUI);
            commentIV = itemView.findViewById(R.id.commentIVHAUI);
        }
    }


}
