package com.example.watering;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class MoistureLevelGraphFragment extends Fragment {

    public void onStart() {
        super.onStart();

        final Bundle data = getArguments();
        ArrayList<String> dateList = data.getStringArrayList("date");
        ArrayList<String> moistureList = data.getStringArrayList("moisture");
        DataPoint[]dataPointArray = new DataPoint[dateList.size()];
        for (int i = 0; i < dateList.size(); i++){
            dataPointArray[i] = new DataPoint( Double.parseDouble(dateList.get(i)), Integer.parseInt(moistureList.get(i)));
        }

        drawGraph(dataPointArray);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.moisture_level_graph_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void drawGraph(DataPoint[] dataPointArray){
        GraphView graph = getActivity().findViewById(R.id.moisture_level_graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointArray);
        series.setTitle("Moisture");
        series.setColor(R.color.colorAccent);
        graph.addSeries(series);
        graph.getLegendRenderer().setVisible(true);
        graph.setTitle("Moisture level");
    }
}
