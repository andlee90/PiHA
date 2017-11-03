package com.andlee90.piha.piha_androidclient.UI.Configuration;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.andlee90.piha.piha_androidclient.Database.Helper;
import com.andlee90.piha.piha_androidclient.Database.ServerItem;
import com.andlee90.piha.piha_androidclient.R;

public class EditorFragment extends Fragment
{
    private ServerItem mServerItem;

    public static EditorFragment newInstance()
    {
        return new EditorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ServerConfigActivity parentActivity = (ServerConfigActivity) getActivity();
        mServerItem = parentActivity.mServerItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_editor, container, false);
        EditText serverNameEdit = view.findViewById(R.id.editor_server_name);
        EditText serverAddressEdit = view.findViewById(R.id.editor_server_address);
        EditText serverPortEdit = view.findViewById(R.id.editor_server_port);
        EditText usernameEdit = view.findViewById(R.id.editor_server_username);
        EditText passwordEdit = view.findViewById(R.id.editor_server_password);

        if(mServerItem != null)
        {
            serverNameEdit.setText(mServerItem.getName());
            serverAddressEdit.setText(mServerItem.getAddress());
            serverPortEdit.setText("" + mServerItem.getPort());

            if(mServerItem.getUsername() != null)
            {
                usernameEdit.setText(mServerItem.getUsername());
            }

            if(mServerItem.getPassword() != null)
            {
                passwordEdit.setText(mServerItem.getPassword());
            }
        }

        Button updateButton = view.findViewById(R.id.button_editor_update);
        updateButton.setOnClickListener(view1 -> {
            String name = serverNameEdit.getText().toString();
            String address = serverAddressEdit.getText().toString();
            String port = serverPortEdit.getText().toString();
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();

            if(!name.equals("") && !address.equals("") && !port.equals(""))
            {
                ServerConfigActivity parentActivity = (ServerConfigActivity) getActivity();
                mServerItem = parentActivity.mServerItem;
                Helper helper = new Helper(getActivity());
                helper.updateServer(mServerItem.getId(), name, address, Integer.parseInt(port), username, password);

                Snackbar.make(view, mServerItem.getName() + "updated successfully" , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                getActivity().recreate();
            }
            else
            {
                Snackbar.make(view, "Please fill out all fields" , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }
}
