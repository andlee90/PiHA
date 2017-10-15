package com.andlee90.piha.piha_androidclient.UI;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.andlee90.piha.piha_androidclient.Networking.ServerConnectionService;
import com.andlee90.piha.piha_androidclient.R;
import com.andlee90.piha.piha_androidclient.UI.ServerConfiguration.ServerListActivity;

public class MainActivity extends AppCompatActivity
{
    ServerConnectionService mService;
    boolean mBound = false;
    LoadingFragment mLoadingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingFragment = LoadingFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_loading_container, mLoadingFragment, null).commit();

        startService(new Intent(this, ServerConnectionService.class));
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

    private ServiceConnection mConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service)
        {
            ServerConnectionService.ServerConnectionBinder binder = (ServerConnectionService.ServerConnectionBinder) service;
            mService = binder.getService();
            mBound = true;

            mLoadingFragment.updateTextView(" Done");
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
}