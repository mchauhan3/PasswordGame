package model;

/**
 * Created by AbhishekTumuluru on 4/9/2017.
 */


public class Model {
    /** Singleton instance */
    private static Model _instance = new Model();

    /**
     * @return the singleton instance of the model.
     * If it is null, initialize it.
     */
    public static Model getInstance() {
        if(_instance == null)
        {
            _instance = new Model();
        }
        return _instance;
    }


    //getters and setters
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private User currentUser;
}

