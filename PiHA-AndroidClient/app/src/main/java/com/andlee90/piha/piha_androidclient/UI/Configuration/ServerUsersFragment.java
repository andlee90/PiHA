package com.andlee90.piha.piha_androidclient.UI.Configuration;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andlee90.piha.piha_androidclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import UserObjects.User;

public class ServerUsersFragment extends ListFragment
{
    private Context mContext;
    private UsersListArrayAdapter mAdapter;
    private ArrayList<User> mUsers = new ArrayList<>();

    private boolean isAttached = false;

    public static ServerUsersFragment newInstance()
    {
        return new ServerUsersFragment();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        isAttached = true;
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new UsersListArrayAdapter(mContext,
                android.R.layout.simple_list_item_1, mUsers);
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
    }

    public void setListView(ArrayList<User> users)
    {
        mUsers.addAll(users);
        if (isAttached)
        {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class UsersListArrayAdapter extends ArrayAdapter<User>
    {
        private LayoutInflater mInflater;
        private List<User> users = null;
        private Map<Integer, View> views = new HashMap<Integer, View>();


        UsersListArrayAdapter(Context context, int resourceId, List<User> users)
        {
            super(context, resourceId, users);

            this.users = users;
            mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent)
        {
            User user = users.get(position);

            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.list_user_items, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.userName.setText(user.getUserName());
            holder.userRole.setText(user.getRole());

            return convertView;
        }

        private class ViewHolder
        {
            private final TextView userName;
            private final TextView userRole;

            ViewHolder(View view)
            {
                userName = view.findViewById(R.id.user_name);
                userRole = view.findViewById(R.id.user_role);
            }
        }
    }
}
