package com.andlee90.piha.piha_androidclient.Database;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

public class ServerItemLoader extends AsyncTaskLoader<ServerItem>
{
    private Context mContext;
    private int mServerId;

    public ServerItemLoader(Context context, int id)
    {
        super(context);
        mContext = context;
        this.mServerId = id;
    }

    @Override
    public ServerItem loadInBackground()
    {
        Helper dbHelper = new Helper(mContext);
        Cursor cursor = dbHelper.getServer(mServerId);
        cursor.moveToFirst();

        return new ServerItem(cursor.getInt(cursor.getColumnIndex("_id")),
                cursor.getString(cursor.getColumnIndex("server_name")),
                cursor.getString(cursor.getColumnIndex("server_address")),
                cursor.getInt(cursor.getColumnIndex("server_port")),
                cursor.getString(cursor.getColumnIndex("server_username")),
                cursor.getString(cursor.getColumnIndex("server_password")));
    }
}