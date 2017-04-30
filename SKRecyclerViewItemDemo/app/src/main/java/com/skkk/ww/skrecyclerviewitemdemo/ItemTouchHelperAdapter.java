package com.skkk.ww.skrecyclerviewitemdemo;

/**
 * Created by admin on 2017/4/22.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2017/4/22$ 23:34$.
*/
public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPos,int toPos);
    void onitemSwipe(int pos);
}
