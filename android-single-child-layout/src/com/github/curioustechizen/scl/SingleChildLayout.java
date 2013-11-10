
package com.github.curioustechizen.scl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * A very simple layout that shows only one of its children at a time. The
 * contract for this class is as follows:
 * 
 * <ol>
 * <li>Add this layout either using an XML resource file, or programmatically.
 * <li>Ensure that the very first time, only one of the children has
 * {@code android:visibility} set to {@code visible}. If this condition is
 * violated, this class will throw an {@code IllegalStateException}.
 * <li>When you want to show a child other than the one currently showing, use
 * {@code showChildAt}.
 * <li>You can use {@code android:animateLayoutChanges="true"} on the layout (or
 * set it programmatically) since this layout actually extends from
 * {@code FrameLayout}.
 * </ol>
 * 
 * <p/>
 * It is important to note that this class only works with direct children.
 * <p/>
 * 
 * 
 * @author Kiran Rao
 */
public class SingleChildLayout extends FrameLayout {

    private int mCurrentlyShowingIndex;
    private int mVisibleChildCount;

    public SingleChildLayout(Context context) {
        super(context);
    }

    public SingleChildLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleChildLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        /*
         * When a child is being added, detect whether there is already another child that is visible. If so, throw an exception.
         */
        if(child.getVisibility() == View.VISIBLE){
            mVisibleChildCount++;
            if(mVisibleChildCount > 1){
                throw new IllegalStateException(
                        "Only one child view in SingleChildLayout can be visible at a time");
            }
            mCurrentlyShowingIndex = (index < 0) ? getChildCount() : index;
        }
        super.addView(child, index, params);
        
    }

    /**
     * Show the child at the specified index
     * 
     * @param childIndex Index of the child to show.
     */
    public void showChildAt(int childIndex) {
        if(childIndex == mCurrentlyShowingIndex){
            /*
             * The desired child is already being shown. Nothing to do.
             */
            return;
        }
        hide(getChildAt(mCurrentlyShowingIndex));
        show(getChildAt(childIndex));
        mCurrentlyShowingIndex = childIndex;
    }
    
    /**
     * 
     * @return The index of the child being shown currently
     */
    public int getShownChildIndex(){
        return mCurrentlyShowingIndex;
    }

    private static void hide(View v) {
        if (v != null && v.getVisibility() != View.GONE) {
            v.setVisibility(View.GONE);
        }
    }

    private static void show(View v) {
        if (v != null && v.getVisibility() != View.VISIBLE) {
            v.setVisibility(View.VISIBLE);
        }
    }
}
