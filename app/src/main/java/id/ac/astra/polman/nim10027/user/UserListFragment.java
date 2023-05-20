package id.ac.astra.polman.nim10027.user;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class UserListFragment extends Fragment {
    private static final String TAG = "UserListFragment";

    private UserListViewModel mUserListViewModel;
    private RecyclerView mUserRecyclerView;
    private UserAdapter mAdapter;
    private List<User> mUsers;

    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        public void onUserSelected(String userId);
    }

    private Callbacks mCallbacks = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach called");
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach called");
        mCallbacks = null;
    }

    //private void updateUI() {
    private void updateUI(List<User> users) {
//        List<User> users = mUserListViewModel.getUsers();
        Log.i(TAG, "updateUI called");
        mAdapter = new UserAdapter(users);
        mUserRecyclerView.setAdapter(mAdapter);
        mUsers = users;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "UserListFragment.onCreate() called");
        setHasOptionsMenu(true);
        mUserListViewModel = new ViewModelProvider(this)
                .get(UserListViewModel.class);
        mAdapter = new UserAdapter(Collections.<User>emptyList());
//        Log.d(TAG, "Total User: " + mUserListViewModel.getUsers().size());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_user_list, menu);
    }

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "UserListFragment.onCreateView() called");
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        mUserRecyclerView = (RecyclerView) view.findViewById(R.id.user_recycler_view);
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        updateUI();
        mUserRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        Log.i(TAG, "UserListFragment.onViewCreated() called");
        mUserListViewModel.getUsers().observe(
                getViewLifecycleOwner(),
                new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {
                        // update the cached copy of
                        updateUI(users);
                        Log.i(TAG, "Got Users: " + users.size());
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_user:
                User user = new User();
                int maxId = 0;

                for (User aUser: mUsers) {
                    if (aUser.getId() >= maxId) {
                        maxId = aUser.getId();
                    }
                }

                user.setId(maxId + 1);
                mUserListViewModel.addUser(user);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }

                mCallbacks.onUserSelected(String.valueOf(user.getId()));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class UserHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mIdTextView;
        private TextView mUsernameTextView;
        private User mUser;

        public UserHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_user, parent, false));
            this.itemView.setOnClickListener(this);

            mIdTextView = this.itemView.findViewById(R.id.user_id);
            mUsernameTextView = this.itemView.findViewById(R.id.user_name);
        }

        public void bind(User user) {
            mUser = user;
            mIdTextView.setText(String.valueOf(mUser.getId()));
            mUsernameTextView.setText(mUser.getUsername());
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(getActivity(),
//                    mUser.getUsername() + " clicked !",
//                    Toast.LENGTH_SHORT)
//                    .show();

            mCallbacks.onUserSelected(String.valueOf(mUser.getId()));
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        private List<User> mUserList;

        public UserAdapter(List<User> users) {
            mUserList = users;
        }

        @Override
        public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new UserHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(UserHolder holder, int position) {
            User user = mUserList.get(position);
            holder.bind(user);
        }

        @Override
        public int getItemCount() {
            return mUserList.size();
        }
    }
}
