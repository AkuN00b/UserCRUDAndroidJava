package id.ac.astra.polman.nim10027.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class UserFragment extends Fragment {
    private static final String ARG_USER_ID = "user_id";
    private static final String TAG = "UserFragment";

    private User mUser;
    private EditText mUserIdField;
    private EditText mUsernameField;
    private UserDetailViewModel mUserDetailViewModel;
    private int mUserId;

    private UserDetailViewModel getUserDetailViewModel() {
        if (mUserDetailViewModel == null) {
            mUserDetailViewModel = new ViewModelProvider(this)
                    .get(UserDetailViewModel.class);
        }

        return mUserDetailViewModel;
    }

    public static UserFragment newInstance(String userId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "UserFragment.onCreate() called");
        String userId = (String) getArguments().getSerializable(ARG_USER_ID);
        Log.i(TAG, "args bundle userId= " + userId);

        mUser = new User();
        mUserId = Integer.parseInt(userId);
        mUserDetailViewModel = getUserDetailViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        mUserIdField = v.findViewById(R.id.userid);
        mUserIdField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // intentionally after blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUser.setId(Integer.parseInt(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // intentionally left blank
            }
        });

        mUsernameField = v.findViewById(R.id.username);
        mUsernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // intentionally after blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUser.setUsername(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // intentionally left blank
            }
        });

        return v;
    }

    private void updateUI() {
        Log.i(TAG, "UserFragment.updateUI() called");
        mUserIdField.setText(String.valueOf(mUser.getId()));
        mUsernameField.setText(mUser.getUsername());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "UserFragment.onViewCreated() called");
        mUserDetailViewModel.getUserLiveData().observe(
                getViewLifecycleOwner(),
                new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        // update the cached copy of
                        mUser = user;
                        updateUI();
                    }
                }
        );

        mUserDetailViewModel.loadUser(mUserId);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "UserFragment.onStop() called");
        mUserDetailViewModel.saveUser(mUser);
    }
}
