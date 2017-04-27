package pwgame.passwordgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import model.Model;
import model.User;

import static android.R.attr.duration;
import static android.R.attr.phoneNumber;
import static android.R.attr.type;

public class MainMenu extends AppCompatActivity {
    FirebaseAuth auth;
    public static final int RC_SIGN_IN = 0;
    DatabaseReference mUserDatabase;

    private static String uniqueId;
    private static String name;
    private static String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mUserDatabase = Splashscreen.getUserDatabase();
    }

    public void openIntroduction(View view) {
        Intent intent = new Intent(this, introduction.class);
        startActivity(intent);
    }

    public void openInstruction(View view) {
        Intent intent = new Intent(this, Instruction.class);
        startActivity(intent);
    }
    public void openAbout(View view) {
        Intent intent = new Intent(this, AboutPage.class);
        startActivity(intent);
    }

    public void openSchemaBuilder(View view) {
        Intent intent = new Intent(this, schemaBuilder.class);
        startActivity(intent);
    }
    public void resetScore(View view) {
        SharedPreferences sp = getSharedPreferences("scores", 0);
        SharedPreferences.Editor ed = sp.edit();
        for (int x = 0; x < 11; x++) {
            if (sp.contains("level" + x)) {
                ed.remove("level" + x);
            }
        }
        if (sp.contains("lastLevel")) {
            ed.remove("lastLevel");
        }
        ed.commit();
        Toast toast = Toast.makeText(this, "Scores Reset!", Toast.LENGTH_SHORT);
        toast.show();
    }
    public void openLogin(View view) {
        auth = Splashscreen.getAuth();
        if (auth.getCurrentUser() != null) {
            Log.d("LOGGED", auth.getCurrentUser().getEmail());
            //finish();
            String uid = auth.getCurrentUser().getUid();
            DatabaseReference ref = mUserDatabase.child(uid);
            setUser(ref);
        } else {
            //open fire base login if current user is null i.e. not signed in
            startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                            .build(),
                    RC_SIGN_IN);
        };
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                String uniqueId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                final DatabaseReference UserRef = mUserDatabase.child(uniqueId);
                UserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Object userData = dataSnapshot.getValue();
                        Intent intent;
                        if (userData == null) {
                            //mUserDatabase.child(uniqueId).child("email").setValue(email);
                            //mUserDatabase.child(uniqueId).child("name").setValue(displayName);
                            intent = new Intent(MainMenu.this, CreateProfile.class);
                            startActivity(intent);
                        } else {
                            //mUserDatabase.setValue(uniqueId);
                            //mUserDatabase.child("email").setValue(email);
                            setUser(UserRef);
                        }
                        //startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Log.d("AUTH", auth.getCurrentUser().getEmail());


                //finish();
            }
        }
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
    private void setUser(DatabaseReference ref) {
        readData(ref, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                uniqueId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                name = dataSnapshot.child("name").getValue(String.class);
                Model.getInstance().setCurrentUser(new User(uniqueId, name, email));
                Intent intent = new Intent(MainMenu.this, introduction.class);
                startActivity(intent);
            }

            @Override
            public void onStart() {
                Log.d("On Start", "Started");
            }

            @Override
            public void onFailure() {

            }
        });
    }


}
