package id.ac.astra.polman.nim10027.user;

import android.os.Trace;
import android.util.Log;
import android.view.animation.Transformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class UserDetailViewModel extends ViewModel {
    private static final String TAG = "UserDetailViewModel";

    private LiveData<User> mUserLiveData;
    private UserRepository mUserRepository;
    private MutableLiveData<Integer> mIdMutableLiveData;

    public UserDetailViewModel() {
        mUserRepository = UserRepository.get();
        mIdMutableLiveData = new MutableLiveData<Integer>();
        mUserLiveData = Transformations.switchMap(mIdMutableLiveData,
                userId -> mUserRepository.getUser(String.valueOf(userId)));
    }

    public void loadUser(int userId) {
        Log.i(TAG, "loadUser() called");
        mIdMutableLiveData.setValue(userId);
    }

    public LiveData<User> getUserLiveData() {
        Log.i(TAG, "getUserLiveData() called");
        return mUserLiveData;
    }

    public void saveUser(User user) {
        mUserRepository.updateUser(user);
    }
}
