package com.example.soumyaagarwal.quickdeal;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soumyaagarwal.quickdeal.attach_row.attachment_adapter;
import com.example.soumyaagarwal.quickdeal.attach_row.row_items;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Screen1 extends AppCompatActivity {

    private static final int IMAGE_PICKER_SELECT = 234;
    private List<row_items> List = new ArrayList<>();
    public RecyclerView recyclerView;
    private attachment_adapter mAdapter;
    LinearLayoutManager mLayoutManager;
    TextInputLayout input_title,input_des;
    String title,des;
    EditText edittext_title,edittext_des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        addingrows(List);
        input_title= (TextInputLayout)findViewById(R.id.input_title);
        input_des= (TextInputLayout)findViewById(R.id.input_des);
        edittext_des  = (EditText)findViewById(R.id.edittext_desp);
        edittext_title = (EditText)findViewById(R.id.edittext_title);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.attach:
                showFileChooser();
                return true;
            case R.id.submit:
                alert();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICKER_SELECT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK)
        {
            Uri selectedMediaUri = data.getData();

            File file = new File(selectedMediaUri.getPath());

            if (file.getName().contains("image"))
            {
                Cursor returnCursor =
                        getContentResolver().query(selectedMediaUri, null, null, null, null);
                assert returnCursor != null;
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                String name = returnCursor.getString(nameIndex);

                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                int size = returnCursor.getInt(sizeIndex)/ 1024;
                returnCursor.close();

                int image = R.mipmap.ic_image;
                row_items attachedfile = new row_items(name,size,image);
                List.add(attachedfile);

                mAdapter.notifyDataSetChanged();
            }
            else if (file.getName().contains("video"))
            {
                Cursor returnCursor =
                        getContentResolver().query(selectedMediaUri, null, null, null, null);
                assert returnCursor != null;
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                String name = returnCursor.getString(nameIndex);

                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                int size = returnCursor.getInt(sizeIndex)/1024;
                returnCursor.close();

                int image = R.mipmap.ic_video;
                row_items attachedfile = new row_items(name,size,image);
                List.add(attachedfile);

                mAdapter.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(this, "This is not supported", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addingrows(final List<row_items> k)
    {
        mAdapter = new attachment_adapter(getApplicationContext(),k);
        mLayoutManager=new LinearLayoutManager(Screen1.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    public void alert()
    {
        title = edittext_title.getText().toString();
        des = edittext_des.getText().toString();
        int p = mAdapter.getItemCount();

        if (title.isEmpty()|| des.isEmpty()||p==0) {
            if (title.isEmpty()) {
                input_title.setError("Field cannot be empty");
                if (edittext_title.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            } else {
                input_title.setErrorEnabled(false);
            }

            if (des.isEmpty()) {
                input_des.setError("Field cannot be empty");
                if (edittext_des.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }

            } else {
                input_des.setErrorEnabled(false);
            }

            if (p==0)
            {
                Toast.makeText(this, "Please attach a file", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Screen1.this);

            alertDialog.setTitle("Submit Requirements");
            alertDialog.setMessage("Sure to submit this ?");

            alertDialog.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Screen1.this, Screen1.class));
                    finish();
                }
            });
                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
    }
}
