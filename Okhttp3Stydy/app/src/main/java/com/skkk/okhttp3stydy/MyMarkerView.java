package com.skkk.okhttp3stydy;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.NumberFormat;

/**
 * Created by admin on 2017/3/12.
 */
/*
* 
* 描    述：自定义标签
* 作    者：ksheng
* 时    间：2017/3/12$ 20:43$.
*/
public class MyMarkerView extends MarkerView {
    private final NumberFormat numberFormat;
    private TextView tvMarker;
    private MPPointF offset2=new MPPointF();


    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvMarker= (TextView) findViewById(R.id.tvMarker);
        numberFormat= java.text.NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);

    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvMarker.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
        tvMarker.setText(numberFormat.format(e.getY()));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        offset2.x=getOffset().getX();
        offset2.y=-posY+getChartView().getViewPortHandler().offsetTop();
        return offset2;
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(0,0);
    }
}
