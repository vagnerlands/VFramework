package lobster.heroquestproj.GameMenu;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import lobster.heroquestproj.VFramework.CSharedUtils;

/**
 * Created by Vagner on 9/10/2016.
 */
public class CMenuClassSelect {
    private int mNumOfCols;
    private int mNumOfRows;

    // some imaginary margin from the top to bottom
    final private int sTopMargin = 150;
    // some imaginary margin from the left to right
    final private int sLeftMargin = 50;

    final private int sHeightSpacing = 10;
    final private int sWidthSpacing = 10;

    List<CMenuClassSelectItem> mClassItemsDB = new ArrayList<>();

    public CMenuClassSelect() {
        mNumOfCols = 1;
        mNumOfRows = 1;
    }

    public CMenuClassSelect(int numOfCols, int numOfRows) {
        mNumOfCols = numOfCols;
        mNumOfRows = numOfRows;
    }

    public void addItem(CMenuClassSelectItem newClass) {
        if (mClassItemsDB.size() < (mNumOfCols * mNumOfRows)) {
            mClassItemsDB.add(newClass);
        }
    }

    public void draw(Canvas canvas) {

        int currentWidth = CSharedUtils.instance().getmSurfaceView().getWidth() - sLeftMargin;
        int currentHeight = CSharedUtils.instance().getmSurfaceView().getHeight() - sTopMargin;

        float boxWidth = (currentWidth + sWidthSpacing) / mNumOfCols;
        float boxHeigth = (currentHeight + sHeightSpacing) / mNumOfRows;

        float coordX = 0;
        float coordY = 0;

        for (int i = 0; i < mClassItemsDB.size(); i++) {

            coordX = sLeftMargin + ((i % mNumOfCols) * boxWidth);
            coordY = sTopMargin + ((int)(i / mNumOfCols) * boxHeigth);
            mClassItemsDB.get(i).draw(canvas, coordX, coordY);
        }
    }
}
