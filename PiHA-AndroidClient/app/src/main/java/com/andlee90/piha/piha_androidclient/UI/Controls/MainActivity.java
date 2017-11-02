package com.andlee90.piha.piha_androidclient.UI.Controls;

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
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.andlee90.piha.piha_androidclient.Database.ServerItem;
import com.andlee90.piha.piha_androidclient.Database.ServerListLoader;
import com.andlee90.piha.piha_androidclient.Networking.ServerConnectionService;
import com.andlee90.piha.piha_androidclient.R;
import com.andlee90.piha.piha_androidclient.UI.Configuration.ServerListActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import DeviceObjects.DeviceList;
import UserObjects.User;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ServerItem>>
{
    private static final int LOADER_ID = 0;
    public static final String RECEIVE_USER = "com.andlee90.piha.RECEIVE_USER";
    public static final String RECEIVE_DEVICE_LIST = "com.andlee90.piha.RECEIVE_DEVICE_LIST";
    public static final String RECEIVE_DEVICE = "com.andlee90.piha.RECEIVE_DEVICE";

    ServerConnectionService mService;
    boolean mBound = false;

    private DeviceListFragment mDeviceListFragment;
    private ArrayList<Fragment> mFragments;
    private List<ServerItem> mServers;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(5);

        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();

        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVE_USER);
        intentFilter.addAction(RECEIVE_DEVICE_LIST);
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
            mService.stopSelf();
        }
    }

    @Override
    protected void onDestroy()
    {
        broadcastManager.unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public Loader<List<ServerItem>> onCreateLoader(int id, Bundle args)
    {
        return new ServerListLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<ServerItem>> loader, List<ServerItem> data)
    {
        mServers = data;
        mFragments = new ArrayList<>();

        for(ServerItem serverItem: mServers)
        {
            mFragments.add(WrapperFragment.newInstance());
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new DeviceListPagerAdapter(getSupportFragmentManager(), mServers, mFragments);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onLoaderReset(Loader<List<ServerItem>> loader)
    {
        //TODO: Implement method.
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
                connectToServers();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.configure_servers)
        {
            Intent intent = new Intent(getApplicationContext(), ServerListActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.help)
        {

        }

        return super.onOptionsItemSelected(item);
    }

    public void connectToServers() throws ExecutionException, InterruptedException
    {
        for(ServerItem server : mServers)
        {
            mService.establishConnection(server);
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(intent.getAction().equals(RECEIVE_USER))
            {
                User user = (User)intent.getSerializableExtra("user");
                mDeviceListFragment = DeviceListFragment.newInstance();
                WrapperFragment wf = (WrapperFragment)mFragments.get((int)intent.getSerializableExtra("serverId")-1);
                wf.swapFragment(mDeviceListFragment);
            }

            else if(intent.getAction().equals(RECEIVE_DEVICE_LIST))
            {
                DeviceList devices = (DeviceList)intent.getSerializableExtra("devices");
                mDeviceListFragment.setListView(devices.getDevices());
            }
        }
    };
    LocalBroadcastManager broadcastManager;
}
