package pwgame.passwordgame;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import model.Model;
import model.User;

import static android.R.attr.y;


@SuppressWarnings("SuspiciousMethodCalls")
public class CreateProfile extends AppCompatActivity {
    //instance variables
    private List<String> modes;
    private Spinner modeSelection;
    private EditText nameText;
    private String email;
    private String uid;
    private EditText phone;
    private EditText zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth auth = Splashscreen.getAuth();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        email = auth.getCurrentUser().getEmail();
        uid = auth.getCurrentUser().getUid();


        nameText = (EditText) findViewById(R.id.name_text);

    }

    /**
     * @param view the current view
     * Adds the user to the database with all relevant details.
     * Sets the current user to the one just added.
     */
    @SuppressWarnings("UnusedParameters")
    public void onSubmit(View view) {
        String name = nameText.getText().toString();
        User newUser = new User(uid, name, email);
        newUser.writeToDatabase();
        Model.getInstance().setCurrentUser(newUser);
        Intent intent = new Intent(CreateProfile.this, introduction.class);
        startActivity(intent);

    }
}
