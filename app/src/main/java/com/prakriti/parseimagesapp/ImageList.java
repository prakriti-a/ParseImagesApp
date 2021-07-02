package com.prakriti.parseimagesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ImageList extends AppCompatActivity {
    // retrieve images & display in descending order via createdAt attribute

    private ArrayList<Bitmap> imageBitmaps = new ArrayList();
    private ArrayList<String> imageTexts = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        final RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        final RCAdapter rcAdapter = new RCAdapter(imageTexts, imageBitmaps, this);
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Photos"); // class name
        query.orderByAscending("createdAt"); // old to new
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size() > 0 && e == null) {
                    for(ParseObject object : objects) {
                        ParseFile parseFile = object.getParseFile("picture"); // column name
                        parseFile.getDataInBackground(new GetDataCallback() { // download in bg
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data.length >0 && e == null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    imageBitmaps.add(bitmap);
                                    rcAdapter.notifyDataSetChanged();
                                }
                                else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(ImageList.this, R.string.no_images, Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
                }
            }
        });
    }
}