package com.andlee90.piha.piha_androidclient.UI.Configuration;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import RuleObjects.Rule;

public class ServerRulesFragment extends ListFragment
{
    private Context mContext;
    private RuleListArrayAdapter mAdapter;
    private ArrayList<Rule> mRules = new ArrayList<>();

    private boolean isAttached = false;

    public static ServerRulesFragment newInstance()
    {
        return new ServerRulesFragment();
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

        mAdapter = new RuleListArrayAdapter(mContext,
                android.R.layout.simple_list_item_1, mRules);
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
    }

    public void setListView(ArrayList<Rule> rules)
    {
        mRules.addAll(rules);
        if (isAttached)
        {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class RuleListArrayAdapter extends ArrayAdapter<Rule>
    {
        private LayoutInflater mInflater;
        private List<Rule> rules = null;
        private Map<Integer, View> views = new HashMap<Integer, View>();


        RuleListArrayAdapter(Context context, int resourceId, List<Rule> rules)
        {
            super(context, resourceId, rules);

            this.rules = rules;
            mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent)
        {
            Rule rule = rules.get(position);

            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.list_rule_items, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.ruleId.setText("" + rule.getRuleId());
            holder.roleName.setText(rule.getRoleName());
            holder.permissionName.setText(rule.getPermissionName());
            holder.deviceName.setText(rule.getDeviceName());

            return convertView;
        }

        private class ViewHolder
        {
            private final TextView ruleId;
            private final TextView roleName;
            private final TextView permissionName;
            private final TextView deviceName;

            ViewHolder(View view)
            {
                ruleId = view.findViewById(R.id.rule_id);
                roleName = view.findViewById(R.id.rule_role_name);
                permissionName = view.findViewById(R.id.rule_permission_name);
                deviceName = view.findViewById(R.id.rule_device_name);
            }
        }
    }
}
