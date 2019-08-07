package com.pos.mahmoud.pos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import miguelbcr.ui.tableFixHeadesWrapper.TableFixHeaderAdapter;

public class BasicCellViewGroup extends FrameLayout
        implements
        TableFixHeaderAdapter.FirstHeaderBinder<String>,
        TableFixHeaderAdapter.HeaderBinder<String>,
        TableFixHeaderAdapter.FirstBodyBinder<List<String>>,
        TableFixHeaderAdapter.BodyBinder<List<String>>,
        TableFixHeaderAdapter.SectionBinder<List<String>> {

    public TextView textView;

    public BasicCellViewGroup(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(android.R.layout.test_list_item, this, true);
        textView = (TextView) findViewById(android.R.id.text1);
    }


    @Override
    public void bindFirstHeader(String headerName) {
        textView.setText(headerName);
    }

    @Override
    public void bindHeader(String headerName, int column) {
        textView.setText(headerName);
    }

    @Override
    public void bindFirstBody(List<String> items, int row) {
        textView.setText(items.get(0));
    }

    @Override
    public void bindBody(List<String> items, int row, int column) {
        textView.setText(items.get(column + 1));
    }

    @Override
    public void bindSection(List<String> item, int row, int column) {
        textView.setText(column == 0 ? "Section:" + (row + 1) : "");
    }
}