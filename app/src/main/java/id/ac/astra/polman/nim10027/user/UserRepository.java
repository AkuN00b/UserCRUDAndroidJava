package id.ac.astra.polman.nim10027.user;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.astra.polman.nim10027.user.api.ApiUtils;
import id.ac.astra.polman.nim10027.user.api.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private static UserRepository INSTANCE;
    private UserService mUserService;

    private UserRepository(Context context) {
        mUserService = ApiUtils.getUserService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(context);
        }
    }

    public static UserRepository get() {
        return INSTANCE;
    }

    public MutableLiveData<List<User>> getUsers() {
        MutableLiveData<List<User>> users = new MutableLiveData<>();

        Call<List<User>> call = mUserService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    users.setValue(response.body());
                    Log.d(TAG, "getUsers.onResponse() called");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("Error API call: ", t.getMessage());
            }
        });

        return users;
    }

    public MutableLiveData<User> getUser(String userId) {
        MutableLiveData<User> user = new MutableLiveData<>();

        Call<User> call = mUserService.getUserById(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user.setValue(response.body());
                    Log.d(TAG, "getUserById.onResponse() called");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error API Call: ", t.getMessage());
            }
        });

        return user;
    }
    
    public void updateUser(User user) {
        Log.i(TAG, "updateUser() called");
        Call<User> call = mUserService.updateUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "User updated " + user.getUsername());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error API Call: ", t.getMessage());
            }
        });
    }

    public void addUser(User user) {
        Log.i(TAG, "addUser() called");
        Call<User> call = mUserService.addUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "User added " + user.getUsername());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error API call : ", t.getMessage());
            }
        });
    }
}
