package model;

import com.google.firebase.database.DatabaseReference;

import pwgame.passwordgame.Splashscreen;

/**
 * Created by AbhishekTumuluru on 4/9/2017.
 */

public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private int highScore;
    private String email;

    public String getUid() {
        return uid;
    }

    private String uid;

    public User(String uid, String name, String email) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public void writeToDatabase() {
        DatabaseReference mUserDatabase = Splashscreen.getUserDatabase();
        mUserDatabase.child(uid).child("name").setValue(name);
        mUserDatabase.child(uid).child("email").setValue(email);
    }

}
