package com.andlee90.piha.piha_androidclient.UI.Configuration;

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
                builder.setTitle("Add a new server");

                LayoutInflater inflater = this.getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_add_server,null);
                builder.setView(layout);

                EditText serverNameEditText = layout.findViewById(R.id.server_name);
                EditText serverAddressEditText = layout.findViewById(R.id.server_address);
                EditText serverPortEditText = layout.findViewById(R.id.server_port);

                builder.setPositiveButton("OK", (dialog, which) ->
                {
                    mServerName = serverNameEditText.getText().toString();
                    mServerAddress = serverAddressEditText.getText().toString();
                    mServerPort = serverPortEditText.getText().toString();

                    if(mServerName.equals("") || mServerAddress.equals("") || mServerPort.equals(""))
                    {
                        Snackbar.make(view, "Please fill out all server fields" , Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                    {
                        AlertDialog.Builder authBuilder = new AlertDialog.Builder(this);
                        authBuilder.setTitle("This server requires authentication");

                        LayoutInflater authInflater = this.getLayoutInflater();
                        View authLayout = authInflater.inflate(R.layout.dialog_login,null);
                        authBuilder.setView(authLayout);

                        EditText usernameEditText = authLayout.findViewById(R.id.username);
                        EditText passwordEditText = authLayout.findViewById(R.id.password);

                        authBuilder.setPositiveButton("OK", (authDialog, authWhich) ->
                        {
                            String username = usernameEditText.getText().toString();
                            String password = passwordEditText.getText().toString();

                            if(!username.equals("") && !password.equals(""))
                            {
                                Helper helper = new Helper(getApplicationContext());
                                helper.insertServer(mServerName, mServerAddress, Integer.parseInt(mServerPort), username, password);
                                recreate();
                            }
                            else
                            {
                                Snackbar.make(view, "Please fill out username and password" , Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
                        authBuilder.setNegativeButton("Cancel", (authDialog, authWhich) -> authDialog.dismiss());
                        authBuilder.show();
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

        mListView.setOnItemClickListener((adapterView, view, i, l) -> {
            ServerItem server = (ServerItem) mListView.getItemAtPosition(i);
            Intent intent = new Intent(getApplicationContext(), ServerConfigActivity.class);
            intent.putExtra("server_id", server.getId());
            getApplicationContext().startActivity(intent);
        });
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

        ServerItemArrayAdapter(Context context, int resourceId, List<ServerItem> servers)
        {
            super(context, resourceId, servers);

            this.servers = servers;
            mInflater = LayoutInflater.from(context);
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
            holder.serverLocation.setText(server.getAddress() + ":" + server.getPort());

            return convertView;
        }

        private class ViewHolder
        {
            private final TextView serverName;
            private final TextView serverLocation;

            ViewHolder(View view)
            {
                serverName = view.findViewById(R.id.server_name);
                serverLocation = view.findViewById(R.id.server_location);
            }
        }
    }
}
