package com.rogowskibart.priceconverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.rogowskibart.priceconverter.R.layout.row;

/**
 * Created by bartoszrogowski on 24/08/2017.
 */

public class ProductAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Product> mDataSource;

    public ProductAdapter(Context context, ArrayList<Product> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(row, parent, false);

        TextView productNameTextView = (TextView) rowView.findViewById(R.id.row_productname);
        TextView productAmountTextView = (TextView) rowView.findViewById(R.id.row_amount);
        TextView pricePerKiloTextView = (TextView) rowView.findViewById(R.id.row_priceperkilo);

        Product product = (Product) getItem(position);

        productNameTextView.setText(product.getProductName());
        productAmountTextView.setText(product.getProductAmount() + " " + product.getProductType());
        pricePerKiloTextView.setText(product.getPricePerKilo());

        return rowView;
    }
}
