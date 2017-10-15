package com.andlee90.piha.piha_androidclient.UI.ServerConfiguration;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.andlee90.piha.piha_androidclient.Networking.ServerConnectionService;
import com.andlee90.piha.piha_androidclient.Database.Helper;
import com.andlee90.piha.piha_androidclient.Database.ServerItem;
import com.andlee90.piha.piha_androidclient.Database.ServerListLoader;
import com.andlee90.piha.piha_androidclient.R;

import java.util.List;

public class ServerListActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<List<ServerItem>>
{
    private static final int LOADER_ID = 1;

    private ListView mListView;
    private String mServerName;
    private String mServerAddress;
    private String mServerPort;

    ServerConnectionService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_list);
        setTitle("PiHA");

        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        mListView = (ListView)findViewById(R.id.listview);
        TextView emptyText = (TextView)findViewById(R.id.empty_listview);
        mListView.setEmptyView(emptyText);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.fab:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add New Server");

                LayoutInflater inflater= this.getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_add_server,null);
                builder.setView(layout);

                EditText serverName = layout.findViewById(R.id.server_name);
                EditText serverAddress = layout.findViewById(R.id.server_address);
                EditText serverPort = layout.findViewById(R.id.server_port);

                builder.setPositiveButton("OK", (dialog, which) ->
                {

                    mServerName = serverName.getText().toString();
                    mServerAddress = serverAddress.getText().toString();
                    mServerPort = serverPort.getText().toString();

                    if(!mServerName.equals("") && !mServerAddress.equals("") && !mServerPort.equals(""))
                    {
                        Helper helper = new Helper(getApplicationContext());
                        helper.insertServer(mServerName, mServerAddress, Integer.parseInt(mServerPort), null, null);
                        recreate();
                    }
                    else
                    {
                        Snackbar.make(view, "Please fill out all fields" , Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.show();
        }
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
    public Loader<List<ServerItem>> onCreateLoader(int id, Bundle args)
    {
        return new ServerListLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<ServerItem>> loader, List<ServerItem> data)
    {
        ServerItemArrayAdapter adapter = new ServerItemArrayAdapter(getApplicationContext(),
                android.R.layout.simple_list_item_1, data);
        mListView.setAdapter(adapter);
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
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0)
        {
            unbindService(mConnection);
            mBound = false;
        }
    };

    private class ServerItemArrayAdapter extends ArrayAdapter<ServerItem>
    {
        private LayoutInflater mInflater;
        private List<ServerItem> servers = null;
        private Context mContext;

        ServerItemArrayAdapter(Context context, int resourceId, List<ServerItem> servers)
        {
            super(context, resourceId, servers);

            this.servers = servers;
            mInflater = LayoutInflater.from(context);
            mContext = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent)
        {
            ServerItem server = servers.get(position);

            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.list_server_items, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.serverName.setText(server.getName());

            holder.serverName.setOnClickListener(view ->
            {
                Intent intent = new Intent(mContext, ServerEditorActivity.class);
                intent.putExtra("server_id", server.getId());
                mContext.startActivity(intent);
            });

            return convertView;
        }

        private class ViewHolder
        {
            private final TextView serverName;

            ViewHolder(View view)
            {
                serverName = view.findViewById(R.id.serverName);
            }
        }
    }
}