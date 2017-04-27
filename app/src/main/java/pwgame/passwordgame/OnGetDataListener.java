package pwgame.passwordgame;

/**
 * Created by AbhishekTumuluru on 4/9/2017.
 */

import com.google.firebase.database.DataSnapshot;

@SuppressWarnings("EmptyMethod")
interface OnGetDataListener {

    /**
     * define what happens on success
     * @param dataSnapshot snapshot of the data
     */
    void onSuccess(DataSnapshot dataSnapshot);

    /**
     * define what happens when start listening to data
     */
    void onStart();

    /**
     * define what happens of failure of retrieving data
     */
    void onFailure();
}