package com.andlee90.piha.piha_androidclient.UI.Configuration;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.andlee90.piha.piha_androidclient.Database.ServerDbHelper;
import com.andlee90.piha.piha_androidclient.Database.ServerItem;
import com.andlee90.piha.piha_androidclient.Database.ServerItemLoader;
import com.andlee90.piha.piha_androidclient.R;

public class ServerConfigActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ServerItem>
{
    private static final int LOADER_ID = 2;
    public int mServerId;
    public ServerItem mServerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_editor);

        Intent intent = getIntent();
        mServerId = intent.getIntExtra("server_id", 0);

        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        EditorPagerAdapter pagerAdapter =
                new EditorPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
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
}
