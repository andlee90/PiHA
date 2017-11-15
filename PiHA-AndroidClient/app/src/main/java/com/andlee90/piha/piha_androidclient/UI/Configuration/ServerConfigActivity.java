package com.andlee90.piha.piha_androidclient.UI.Configuration;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.andlee90.piha.piha_androidclient.Database.ServerDbHelper;
import com.andlee90.piha.piha_androidclient.Database.ServerItem;
import com.andlee90.piha.piha_androidclient.Database.ServerItemLoader;
import com.andlee90.piha.piha_androidclient.Networking.ServerConnectionService;
import com.andlee90.piha.piha_androidclient.R;
import com.andlee90.piha.piha_androidclient.UI.General.WrapperFragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import DeviceObjects.DeviceList;
import RoleObjects.RoleList;
import RuleObjects.RuleList;
import UserObjects.UserList;

public class ServerConfigActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ServerItem>
{
    public static final String RECEIVE_CONFIG_DEVICE_LIST = "com.andlee90.piha.RECEIVE_CONFIG_DEVICE_LIST";
    public static final String RECEIVE_CONFIG_USER_LIST = "com.andlee90.piha.RECEIVE_CONFIG_USER_LIST";
    public static final String RECEIVE_CONFIG_ROLE_LIST = "com.andlee90.piha.RECEIVE_CONFIG_ROLE_LIST";
    public static final String RECEIVE_CONFIG_RULE_LIST = "com.andlee90.piha.RECEIVE_CONFIG_RULE_LIST";

    private static final int LOADER_ID = 2;

    public int mServerId;
    public ServerItem mServerItem;
    private ArrayList<Fragment> mFragments;

    ServerConnectionService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_editor);

        Intent intent = getIntent();
        mServerId = intent.getIntExtra("server_id", 0);

        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();

        mFragments = new ArrayList<>();
        mFragments.add(ServerEditorFragment.newInstance());
        mFragments.add(WrapperFragment.newInstance());
        mFragments.add(WrapperFragment.newInstance());
        mFragments.add(WrapperFragment.newInstance());
        mFragments.add(WrapperFragment.newInstance());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new EditorPagerAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVE_CONFIG_DEVICE_LIST);
        intentFilter.addAction(RECEIVE_CONFIG_USER_LIST);
        intentFilter.addAction(RECEIVE_CONFIG_ROLE_LIST);
        intentFilter.addAction(RECEIVE_CONFIG_RULE_LIST);
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        Intent intent = new Intent(this, ServerConnectionService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (mBound)
        {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public Loader<ServerItem> onCreateLoader(int id, Bundle args)
    {
        return new ServerItemLoader(getApplicationContext(), mServerId);
    }

    @Override
    public void onLoadFinished(Loader<ServerItem> loader, ServerItem data)
    {
        mServerItem = data;
        setTitle(mServerItem.getName());
    }

    @Override
    public void onLoaderReset(Loader<ServerItem> loader)
    {
        //TODO: Implement method.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_server_editor_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete " + mServerItem.getName() + "?");

                builder.setPositiveButton("Delete", (dialog, which) ->
                {
                    ServerDbHelper serverDbHelper = new ServerDbHelper(this);
                    serverDbHelper.deleteServer(mServerId);
                    finish();
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private ServiceConnection mConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service)
        {
            ServerConnectionService.ServerConnectionBinder binder = (ServerConnectionService.ServerConnectionBinder) service;
            mService = binder.getService();
            mBound = true;

            try
            {
                if(mService.isConnected(mServerItem.getId()))
                {
                    mService.getDevices(mServerItem);
                    mService.getUsers(mServerItem);
                    mService.getRoles(mServerItem);
                    mService.getRules(mServerItem);
                }
            }
            catch (ExecutionException | InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0)
        {
            unbindService(mConnection);
            mBound = false;
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(intent.getAction().equals(RECEIVE_CONFIG_DEVICE_LIST))
            {
                DeviceList devices = (DeviceList)intent.getSerializableExtra("devices");
                ServerDevicesFragment serverDevicesFragment = ServerDevicesFragment.newInstance();
                WrapperFragment wf = (WrapperFragment)mFragments.get(1);
                wf.swapFragment(serverDevicesFragment);
                serverDevicesFragment.setListView(devices.getDevices());
            }

            else if(intent.getAction().equals(RECEIVE_CONFIG_USER_LIST))
            {
                UserList users = (UserList)intent.getSerializableExtra("users");
                ServerUsersFragment serverUsersFragment = ServerUsersFragment.newInstance();
                WrapperFragment wf = (WrapperFragment)mFragments.get(2);
                wf.swapFragment(serverUsersFragment);
                serverUsersFragment.setListView(users.getUsers());
            }

            else if(intent.getAction().equals(RECEIVE_CONFIG_ROLE_LIST))
            {
                RoleList roles = (RoleList)intent.getSerializableExtra("roles");
                ServerRolesFragment serverRolesFragment = ServerRolesFragment.newInstance();
                WrapperFragment wf = (WrapperFragment)mFragments.get(3);
                wf.swapFragment(serverRolesFragment);
                serverRolesFragment.setListView(roles.getRoles());
            }

            else if(intent.getAction().equals(RECEIVE_CONFIG_RULE_LIST))
            {
                RuleList rules = (RuleList)intent.getSerializableExtra("rules");
                ServerRulesFragment serverRulesFragment = ServerRulesFragment.newInstance();
                WrapperFragment wf = (WrapperFragment)mFragments.get(4);
                wf.swapFragment(serverRulesFragment);
                serverRulesFragment.setListView(rules.getRules());
            }
        }
    };
    LocalBroadcastManager broadcastManager;
}
