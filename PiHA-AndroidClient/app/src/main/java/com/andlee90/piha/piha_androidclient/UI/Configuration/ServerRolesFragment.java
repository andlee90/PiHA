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

import RoleObjects.Role;

public class ServerRolesFragment extends ListFragment
{
    private Context mContext;
    private RoleListArrayAdapter mAdapter;
    private ArrayList<Role> mRoles = new ArrayList<>();

    private boolean isAttached = false;

    public static ServerRolesFragment newInstance()
    {
        return new ServerRolesFragment();
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

        mAdapter = new RoleListArrayAdapter(mContext,
                android.R.layout.simple_list_item_1, mRoles);
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
    }

    public void setListView(ArrayList<Role> roles)
    {
        mRoles.addAll(roles);
        if (isAttached)
        {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class RoleListArrayAdapter extends ArrayAdapter<Role>
    {
        private LayoutInflater mInflater;
        private List<Role> roles = null;
        private Map<Integer, View> views = new HashMap<Integer, View>();


        RoleListArrayAdapter(Context context, int resourceId, List<Role> roles)
        {
            super(context, resourceId, roles);

            this.roles = roles;
            mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent)
        {
            Role role = roles.get(position);

            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.list_role_items, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.roleName.setText(role.getRoleName());
            holder.rolePriority.setText("" + role.getRolePriority());

            return convertView;
        }

        private class ViewHolder
        {
            private final TextView roleName;
            private final TextView rolePriority;

            ViewHolder(View view)
            {
                roleName = view.findViewById(R.id.role_name);
                rolePriority = view.findViewById(R.id.role_priority);
            }
        }
    }
}
