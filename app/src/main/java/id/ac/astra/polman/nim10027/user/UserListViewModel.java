package id.ac.astra.polman.nim10027.user;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserListViewModel extends ViewModel {
    private static final String TAG = "UserListViewModel";

    // private List<User> mUsers;
    private MutableLiveData<List<User>> mUserListMutableLiveData;
    private UserRepository mUserRepository;

    public UserListViewModel() {
        Log.d(TAG, "UserListViewModel constructor called");
        mUserRepository = UserRepository.get();
    }

//    public List<User> getUsers() {
//        if (mUsers == null) {
//            mUsers = new ArrayList<>();
//            loadUser();
//        }
//
//        return mUsers;
//    }
//
//    private void loadUser() {
//        for (int i = 1; i <= 100; i++) {
//            User user = new User(i, "User #" + i);
//            mUsers.add(user);
//        }
//    }

    public MutableLiveData<List<User>> getUsers() {
        mUserListMutableLiveData = mUserRepository.getUsers();
        Log.d(TAG, "UserListViewModel.getUsers() called = " + mUserListMutableLiveData.toString());

        return mUserListMutableLiveData;
    }

    public void addUser(User user) {
        mUserRepository.addUser(user);
    }
}
