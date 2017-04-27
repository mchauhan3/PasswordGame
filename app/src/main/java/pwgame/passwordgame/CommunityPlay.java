package pwgame.passwordgame;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class CommunityPlay extends AppCompatActivity {
    private ArrayList<DBPasswordData> all;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_community_play);
        all = new ArrayList<>();
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ref = Splashscreen.getSchemaDatabase();
        createLevels(all, ll);
//        Log.d("ALLSIZE", all.size()+"");
//        for (int x = 0; x < all.size(); x++) {
//            Log.d("ALLLSIZEE", all.get(x).toString());
//            LinearLayout temp = new LinearLayout(this);
//            temp.setWeightSum(3.0f);
//
//
//            TextView t = new TextView(this);
//            t.setText(all.get(x).getName());
//            t.setTextColor(Color.BLACK);
//            t.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
//            t.setGravity(Gravity.CENTER);
//            temp.addView(t);
//
//            Button bt1 = new Button(this);
//            bt1.setText("Play");
//            bt1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.e("!!", "clicked");
//                    LinearLayout first = ((LinearLayout) v.getParent());
//                    int index = ((LinearLayout) first.getParent()).indexOfChild(first);
//                    Log.e("!!", index + "");
//                    Intent intent = new Intent(v.getContext(), levelActivity.class);
//                    Log.e("!", all.get(index).getInstructions());
//                    Log.e("!", all.get(index).getMap());
//                    intent.putExtra("PasswordData", all.get(index).createPasswordData());
//                    intent.putExtra("level", 10);
//                    startActivity(intent);
//                }
//            });
//            bt1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
//            temp.addView(bt1);
//            Button bt2 = new Button(this);
//            bt2.setText("Edit");
//            bt2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
//            bt2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.e("!!", "clicked");
//                    LinearLayout first = ((LinearLayout) v.getParent());
//                    int index = ((LinearLayout) first.getParent()).indexOfChild(first);
//                    Log.e("!!", index + "");
//                    Intent intent = new Intent(v.getContext(), schemaBuilder.class);
//                    Log.e("!", all.get(index).getInstructions());
//                    Log.e("!", all.get(index).getMap());
//                    DBPasswordData cur = all.get(index);
//                    ArrayList<Integer> start = new ArrayList<Integer>();
//                    ArrayList<Integer> end = new ArrayList<Integer>();
//                    ArrayList<String> instr = new ArrayList<String>();
//                    StringTokenizer s1 = new StringTokenizer(cur.getMap(), ",");
//                    while (s1.hasMoreTokens()) {
//                        start.add(Integer.parseInt(s1.nextToken()));
//                        end.add(Integer.parseInt(s1.nextToken()));
//                    }
//                    s1 = new StringTokenizer(cur.getInstructions(), ",");
//                    while (s1.hasMoreElements()) {
//                        instr.add(s1.nextToken());
//                    }
//                    intent.putIntegerArrayListExtra("start", start);
//                    intent.putIntegerArrayListExtra("end", end);
//                    intent.putStringArrayListExtra("instr", instr);
//
//                    //.putExtra("PasswordData", all.get(index).createPasswordData());
//                    //intent.putExtra("level", 10);
//                    startActivity(intent);
//                }
//            });
//            temp.addView(bt2);
//
//            ll.addView(temp);
//        }
//        setContentView(ll);
    }

    private void createLevels(final ArrayList<DBPasswordData> pwd, final LinearLayout ll) {
        readData(ref, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    Log.d("HITHITHIT", "THISONE");
                    pwd.add(d.getValue(DBPasswordData.class));
                }
                for (int x = 0; x < all.size(); x++) {
                    Log.d("ALLLSIZEE", all.get(x).toString());
                    LinearLayout temp = new LinearLayout(CommunityPlay.this);
                    temp.setWeightSum(3.0f);


                    TextView t = new TextView(CommunityPlay.this);
                    t.setText(all.get(x).getName());
                    t.setTextColor(Color.BLACK);
                    t.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                    t.setGravity(Gravity.CENTER);
                    temp.addView(t);

                    Button bt1 = new Button(CommunityPlay.this);
                    bt1.setText("Play");
                    bt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("!!", "clicked");
                            LinearLayout first = ((LinearLayout) v.getParent());
                            int index = ((LinearLayout) first.getParent()).indexOfChild(first);
                            Log.e("!!", index + "");
                            Intent intent = new Intent(v.getContext(), levelActivity.class);
                            Log.e("!", all.get(index).getInstructions());
                            Log.e("!", all.get(index).getMap());
                            intent.putExtra("PasswordData", all.get(index).createPasswordData());
                            intent.putExtra("level", 10);
                            startActivity(intent);
                        }
                    });
                    bt1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                    temp.addView(bt1);
                    Button bt2 = new Button(CommunityPlay.this);
                    bt2.setText("Edit");
                    bt2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("!!", "clicked");
                            LinearLayout first = ((LinearLayout) v.getParent());
                            int index = ((LinearLayout) first.getParent()).indexOfChild(first);
                            Log.e("!!", index + "");
                            Intent intent = new Intent(v.getContext(), schemaBuilder.class);
                            Log.e("!", all.get(index).getInstructions());
                            Log.e("!", all.get(index).getMap());
                            DBPasswordData cur = all.get(index);
                            ArrayList<Integer> start = new ArrayList<Integer>();
                            ArrayList<Integer> end = new ArrayList<Integer>();
                            ArrayList<String> instr = new ArrayList<String>();
                            StringTokenizer s1 = new StringTokenizer(cur.getMap(), ",");
                            while (s1.hasMoreTokens()) {
                                start.add(Integer.parseInt(s1.nextToken()));
                                end.add(Integer.parseInt(s1.nextToken()));
                            }
                            s1 = new StringTokenizer(cur.getInstructions(), ",");
                            while (s1.hasMoreElements()) {
                                instr.add(s1.nextToken());
                            }
                            intent.putIntegerArrayListExtra("start", start);
                            intent.putIntegerArrayListExtra("end", end);
                            intent.putStringArrayListExtra("instr", instr);

                            //.putExtra("PasswordData", all.get(index).createPasswordData());
                            //intent.putExtra("level", 10);
                            startActivity(intent);
                        }
                    });
                    temp.addView(bt2);

                    ll.addView(temp);
                }
                setContentView(ll);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });
    }


    private void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure();
            }
        });

    }

}
