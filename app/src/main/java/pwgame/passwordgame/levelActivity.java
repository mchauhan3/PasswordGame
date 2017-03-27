package pwgame.passwordgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class levelActivity extends AppCompatActivity {

    private TextView challenge, lastChallenge, lastAnswer;
    private EditText guess;
    private LinearLayout history;
    private PasswordData pwd;
    private int levelNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra("PasswordData")) {
            Log.e("!!!!!", "has PasswordData");
            pwd = getIntent().getExtras().getParcelable("PasswordData");
            levelNum = getIntent().getExtras().getInt("level")+1;
        } else {
            Log.e("!!!!!", "has pwd");
            levelNum = getIntent().getExtras().getInt("levelNum")+1;
            pwd = (getIntent().<PasswordData>getParcelableArrayListExtra("pwd")).get(levelNum-1);
        }
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setWeightSum(2.0f);
        TextView fill1 = new TextView(this);
        fill1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.2f));
        ll.addView(fill1);
        TextView head = new TextView(this);
        head.setTextColor(Color.BLUE);
        head.setTextSize(18);
        head.setText("Level " + levelNum + "     Par score = " + pwd.getQ());
        head.setGravity(Gravity.CENTER);
        head.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.2f));
        ll.addView(head);
        TextView fill2 = new TextView(this);
        //fill2.setText("Q = " + pwd.getQ());
        fill2.setGravity(Gravity.CENTER);
        fill2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.3f));
        ll.addView(fill2);
        TextView hint = new TextView(this);
        hint.setTextColor(Color.RED);
        hint.setTextSize(24);
        hint.setText("SAMPLE --> " + getPassword("SAMPLE"));
        hint.setGravity(Gravity.CENTER);
        hint.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f));
        ll.addView(hint);
        TextView fill3 = new TextView(this);
        fill3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f));
        ll.addView(fill3);
        Button GO = new Button(this);
        GO.setText("START");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 0.2f);
        lp.gravity = Gravity.CENTER;
        GO.setLayoutParams(lp);
        GO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createScreen();
            }
        });
        GO.setGravity(Gravity.CENTER);
        ll.addView(GO);
        TextView fill4 = new TextView(this);
        fill4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.2f));
        ll.addView(fill4);
        setContentView(ll);

        //setContentView(R.gradient.activity_level);
    }
    private ArrayList<String> challenges, guesses, answers;
    private TextView score;
    private void createScreen() {
        challenges = new ArrayList<String>();
        guesses = new ArrayList<String>();
        answers = new ArrayList<String>();
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setWeightSum(2.0f);
        //Upper Half: Challenge, Response Area, Button
        LinearLayout uh = new LinearLayout(this);
        uh.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f));
        uh.setWeightSum(2.0f);
        uh.setOrientation(LinearLayout.VERTICAL);
        LinearLayout header = new LinearLayout(this);
        header.setWeightSum(1.0f);
        header.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f));
        score = new TextView(this);
        score.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f));
        score.setText("Score: 0       Par score: " + pwd.getQ());
        score.setTextSize(20);
        score.setTextColor(Color.BLUE);
        score.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        header.addView(score);
        Button hint = new Button(this);
        hint.setText("Hint");
        hint.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f));
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = pwd.getHint();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        header.addView(hint);
        uh.addView(header);

        LinearLayout lastGuess = new LinearLayout(this);
        lastGuess.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.3f));
        lastGuess.setWeightSum(1.0f);
        lastChallenge = (TextView) getLayoutInflater().inflate(R.layout.text_view_layout, null);
        lastChallenge.setText("SAMPLE");
        lastChallenge.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));
        lastChallenge.setTransformationMethod(null);
        lastChallenge.setGravity(Gravity.CENTER);
        lastAnswer = (TextView) getLayoutInflater().inflate(R.layout.text_view_layout, null);
        lastAnswer.setText(getPassword("SAMPLE"));
        lastAnswer.setTransformationMethod(null);
        lastAnswer.setGravity(Gravity.CENTER);
        lastAnswer.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));
        lastGuess.addView(lastChallenge);
        lastGuess.addView(lastAnswer);
        uh.addView(lastGuess);

        challenge = new TextView(this);
        Collections.shuffle(Arrays.asList(pw));
        challenge.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f));
        challenge.setText(pw[index++]);
        challenge.setTextSize(30);
        challenge.setTypeface(null, Typeface.BOLD);
        challenge.setTextColor(Color.BLACK);
        challenge.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        challenge.setGravity(Gravity.CENTER_VERTICAL);
        uh.addView(challenge);

        guess = new EditText(this);
        guess.setHint("Guess here");
        guess.setGravity(Gravity.CENTER_VERTICAL);
        guess.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f));
        uh.addView(guess);

        LinearLayout buttons = new LinearLayout(this);
        buttons.setWeightSum(1.0f);
        buttons.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f));
        Button tryButton = new Button(this);
        tryButton.setText("Guess");
        tryButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        buttons.addView(tryButton);
        /**
        Button traceButton = new Button(this);
        traceButton.setText("View Trace");
        traceButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));
        traceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), traceDisplayActivity.class);
                intent.putExtra("trace", pwd.getFullTrace());
                //intent.putStringArrayListExtra("trace", pwd.getTrace());
                startActivity(intent);
            }
        });
        buttons.addView(traceButton);
        **/
         uh.addView(buttons);

        ll.addView(uh);
        //Lower Half: History of Challenges, Attempts, and Responses
        LinearLayout lh = new LinearLayout(this);
        lh.setOrientation(LinearLayout.VERTICAL);
        lh.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f));
        lh.setWeightSum(5.0f);
        LinearLayout labelbar = new LinearLayout(this);
        labelbar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f));
        labelbar.setWeightSum(3.0f);
        float size = 18f;
        TextView text1 = (TextView) getLayoutInflater().inflate(R.layout.text_view_layout, null);
        text1.setText("Challenge");
        text1.setGravity(Gravity.CENTER);
        text1.setTypeface(null, Typeface.BOLD);
        text1.setTextSize(size);
        text1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        TextView text2 = (TextView) getLayoutInflater().inflate(R.layout.text_view_layout, null);
        text2.setText("Guess");
        text2.setTextSize(size);
        text2.setGravity(Gravity.CENTER);
        text2.setTypeface(null, Typeface.BOLD);
        text2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        TextView text3 = (TextView) getLayoutInflater().inflate(R.layout.text_view_layout, null);
        text3.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        text3.setText("Response");
        text3.setTextSize(size);
        text3.setGravity(Gravity.CENTER);
        text3.setTypeface(null, Typeface.BOLD);
        labelbar.addView(text1);
        labelbar.addView(text2);
        labelbar.addView(text3);
        lh.addView(labelbar);
        ScrollView sc = new ScrollView(this);
        history = new LinearLayout(this);
        history.setOrientation(LinearLayout.VERTICAL);
        sc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 4.0f));
        sc.addView(history);
        lh.addView(sc);
        ll.addView(lh);
        setContentView(ll);
        addSample();
    }
    private void addSample() {
        LinearLayout add = new LinearLayout(this);
        add.setWeightSum(3.0f);
        //Button add1 = new Button(this);
        //Button add1 = new Button(this, null, R.style.TextViewMod);
        TextView add1 = (TextView) getLayoutInflater().inflate(R.layout.text_view_layout, null);
        add1.setText("SAMPLE");
        add1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        add1.setGravity(Gravity.CENTER);
        TextView add2 = (TextView) getLayoutInflater().inflate(R.layout.text_view_layout, null);
        add2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        add2.setText("");
        add2.setTransformationMethod(null);
        add2.setGravity(Gravity.CENTER);
        TextView add3 = (TextView) getLayoutInflater().inflate(R.layout.text_view_layout, null);
        add3.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        add3.setTransformationMethod(null);
        add3.setText(getPassword("SAMPLE"));
        add3.setGravity(Gravity.CENTER);
        add.addView(add1);
        add.addView(add2);
        add.addView(add3);
        history.addView(add);
    }
    private int index = 0;
    private String[] pw = new String[]{"AMAZON", "FACEBOOK", "DROPBOX", "MSN", "INSTAGRAM", "ALIEXPRESS",
            "GOOGLE", "YOUTUBE", "BAIDU", "YAHOO", "WIKIPEDIA", "TENCENT", "TWITTER","TAOBAO", "LIVE",
            "SINA", "LINKEDIN", "EBAY", "YANDEX", "HAO", "BING", "APPLE", "BLOGSPOT", "ASK", "PINTEREST",
            "TMALL", "REDDIT", "MAILRU", "SOHU", "TUMBLR", "IMGUR", "MICROSOFT", "NETFLIX", "STACK", "CRAIGSLIST"};
    private void reset() {
        String curChal = challenge.getText().toString();
        String response = getPassword(curChal);
        String theirGuess = guess.getText().toString();
        challenges.add(curChal);
        guesses.add(theirGuess);
        answers.add(response);
        if (response.equals(theirGuess)) {
            doFinish();
        }
        challenge.setText(pw[index++]);
        score.setText("Score: "+ challenges.size() + "       Par score: " + pwd.getQ());
        index %= pw.length;
        guess.setText("");
        LinearLayout add = new LinearLayout(this);
        add.setWeightSum(3.0f);
        TextView add1 = (TextView) getLayoutInflater().inflate(R.layout.text_view_layout, null);
        add1.setText(curChal);
        add1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        add1.setGravity(Gravity.CENTER);
        TextView add2 = (TextView) getLayoutInflater().inflate(R.layout.text_view_layout, null);
        add2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        add2.setText(theirGuess);
        add2.setTransformationMethod(null);
        add2.setGravity(Gravity.CENTER);
        TextView add3 = (TextView) getLayoutInflater().inflate(R.layout.text_view_layout, null);
        add3.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        add3.setTransformationMethod(null);
        add3.setGravity(Gravity.CENTER);
        add3.setText(response);
        add.addView(add1);
        add.addView(add2);
        add.addView(add3);
        history.addView(add);
    }
    private void doFinish() {
        Intent intent = new Intent(this, postLevelActivity.class);
        if (getIntent().hasExtra("pwd")) {
            Log.e("!", "contains pwd");
            intent.putParcelableArrayListExtra("pwd", this.getIntent().<PasswordData>getParcelableArrayListExtra("pwd"));
            intent.putExtra("levelNumber", levelNum - 1);
        } else {
            Log.e("!", "does not contain pwd");
            intent.putExtra("PasswordData", this.getIntent().getParcelableExtra("PasswordData"));
            intent.putExtra("level", this.getIntent().getExtras().getInt("level",0));
        }
        intent.putStringArrayListExtra("guesses", guesses);
        intent.putStringArrayListExtra("challenges", challenges);
        intent.putStringArrayListExtra("answers", answers);
        startActivity(intent);
        finish();
    }
    private String getPassword(String chal) {
        return pwd.getPassword(chal, false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.gameMenu) {
            Intent intent = new Intent(this, introduction.class);
            intent.putParcelableArrayListExtra("pwd", getIntent().<PasswordData>getParcelableArrayListExtra("pwd"));
            startActivity(intent);
            finish();
        } else if (id==R.id.viewTrace) {
            Intent intent = new Intent(this, traceDisplayActivity.class);
            intent.putExtra("trace", pwd.getFullTrace());
            //intent.putStringArrayListExtra("trace", pwd.getTrace());
            startActivity(intent);
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
