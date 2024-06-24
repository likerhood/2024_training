package com.example.week3test;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.week3test.adapter.PlanetBaseAdapter;
import com.example.week3test.adapter.PlanetListWithButtonAdapter;
import com.example.week3test.enity.Planet;
import com.example.week3test.utils.ToastUtil;

import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv_planet;
    List<Planet> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        lv_planet = findViewById(R.id.lv_planet);
        list = Planet.getDefaultList();
        PlanetListWithButtonAdapter adapter = new PlanetListWithButtonAdapter(this, list);
        lv_planet.setAdapter(adapter);

        lv_planet.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "您选择的是" + list.get(position).name);


    }
}