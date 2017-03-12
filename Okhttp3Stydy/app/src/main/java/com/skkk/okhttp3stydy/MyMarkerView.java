package com.skkk.okhttp3stydy;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

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
    private TextView tvMarker;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvMarker= (TextView) findViewById(R.id.tvMarker);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvMarker.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tvMarker.setText(e.getY()+"°");

        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {
        if (mOffset == null) {
            mOffset=new MPPointF(-(getWidth()/2),-getHeight());
        }
        return super.getOffset();
    }
}
