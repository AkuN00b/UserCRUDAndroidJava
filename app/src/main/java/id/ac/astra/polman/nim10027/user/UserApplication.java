package id.ac.astra.polman.nim10027.user;

import android.app.Application;
import android.util.Log;

public class UserApplication extends Application {
    private static final String TAG = "UserApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "UserApplication.onCreate() called");
        UserRepository.initialize(this);
    }
}
