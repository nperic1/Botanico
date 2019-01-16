package com.example.watering;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MoistureLevelGraphFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.moisture_level_graph_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         drawGraph();
    }

    private void drawGraph(){
        GraphView graph = getActivity().findViewById(R.id.moisture_level_graph);

        //getData()

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        series.setTitle("Moisture");
        series.setColor(R.color.colorAccent);
        graph.addSeries(series);
        graph.getLegendRenderer().setVisible(true);
        graph.setTitle("Moisture level");
    }

    private void getData(){

    }
}
