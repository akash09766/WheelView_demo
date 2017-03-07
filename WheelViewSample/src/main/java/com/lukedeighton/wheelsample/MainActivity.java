package com.lukedeighton.wheelsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class MainActivity extends Activity {

    private static final int ITEM_COUNT = 8;
    private ArrayList<String> mAdapterData;
    private ArrayList<String> mAdapterDataval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final WheelView wheelView = (WheelView) findViewById(R.id.wheelview);

        //create data for the adapter
        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(ITEM_COUNT);
        for (int i = 0; i < ITEM_COUNT; i++) {
            Map.Entry<String, Integer> entry = MaterialColor.random(this, "\\D*_500$");
            entries.add(entry);
        }

        createData();
//        float density = getResources().getDisplayMetrics().density;

        //populate the adapter, that knows how to draw each item
        // (as you would do with a ListAdapter)
        wheelView.setAdapter(new MaterialColorAdapter(entries, this, mAdapterData, mAdapterDataval));

        //a listener for receiving a callback for when the item
        // closest to the selection angle changes
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectListener() {
            @Override
            public void onWheelItemSelected(WheelView parent, Drawable itemDrawable, int position) {
                //get the item at this position
                Map.Entry<String, Integer> selectedEntry = ((MaterialColorAdapter) parent.getAdapter()).getItem(position);
                parent.setSelectionColor(getContrastColor(selectedEntry));
            }
        });

        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
                String msg = String.valueOf(position) + " " + isSelected;
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                moveToCirleActivity();
            }
        });

        addView();
    }

    private void moveToCirleActivity() {
        startActivity(new Intent(this, CircleActivity.class));
    }

    private void createData() {
        mAdapterData = new ArrayList<>();
        mAdapterData.add("BP");
        mAdapterData.add("Temp");
        mAdapterData.add("HR");
        mAdapterData.add("BS");
        mAdapterData.add("BP");
        mAdapterData.add("Temp");
        mAdapterData.add("HR");
        mAdapterData.add("BS");

        mAdapterDataval = new ArrayList<>();

        mAdapterDataval.add("120/80");
        mAdapterDataval.add("97.6");
        mAdapterDataval.add("72");
        mAdapterDataval.add("14");
        mAdapterDataval.add("120/80");
        mAdapterDataval.add("97.6");
        mAdapterDataval.add("72");
        mAdapterDataval.add("14");


    }

    private void addView() {

        LinearLayout mVitalView = (LinearLayout) findViewById(R.id.vital_title_id);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.title_view, null);
        mVitalView.addView(view);
    }

    //get the materials darker contrast
    private int getContrastColor(Map.Entry<String, Integer> entry) {
        String colorName = MaterialColor.getColorName(entry);
        return MaterialColor.getContrastColor(colorName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class MaterialColorAdapter extends WheelArrayAdapter<Map.Entry<String, Integer>> {
        private Context context;
        private ArrayList<String> data;
        private ArrayList<String> data2;

        MaterialColorAdapter(List<Map.Entry<String, Integer>> entries, Context context
                , ArrayList<String> data, ArrayList<String> data2) {
            super(entries);
            this.context = context;
            this.data = data;
            this.data2 = data2;
        }

        @Override
        public Drawable getDrawable(int position) {
            Drawable[] drawable = new Drawable[]{createOvalDrawable(position,
                    getItem(position).getValue()),
                    new TextDrawable(context, data.get(position), data2.get(position))};
            return new LayerDrawable(drawable);
        }

        private Drawable createOvalDrawable(int pos, int color) {
            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.getPaint().setColor(ContextCompat.getColor(context, R.color.white));
            return shapeDrawable;
        }
    }
}