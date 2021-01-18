package com.teste.digioapplication.util.custom

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

class CustomLinearSnapHelper : LinearSnapHelper() {

    private var verticalHelper: OrientationHelper? = null
    private var horizontalHelper: OrientationHelper? = null

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val out = IntArray(2)

        out[0] = if (layoutManager.canScrollHorizontally()) {
            distanceToStart(targetView, getHorizontalHelper(layoutManager))
        } else 0

        out[1] = if (layoutManager.canScrollVertically()) {
            distanceToStart(targetView, getVerticalHelper(layoutManager))
        } else 0

        return out
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        return if (layoutManager is LinearLayoutManager) {

            if (layoutManager.canScrollHorizontally()) {
                getStartView(layoutManager, getHorizontalHelper(layoutManager))
            } else getStartView(layoutManager, getVerticalHelper(layoutManager))

        } else super.findSnapView(layoutManager)
    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper?): Int {
        return helper?.run { getDecoratedStart(targetView) - startAfterPadding } ?: 0
    }

    private fun getStartView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper?
    ): View? {

        if (layoutManager is LinearLayoutManager) {

            val firstChild = layoutManager.findFirstVisibleItemPosition()
            val isLastItem = (layoutManager.findLastCompletelyVisibleItemPosition()
                    == layoutManager.getItemCount() - 1)

            if (firstChild == RecyclerView.NO_POSITION || isLastItem) return null

            val child = layoutManager.findViewByPosition(firstChild)

            return helper?.run {
                if (getDecoratedEnd(child) >= getDecoratedMeasurement(child) / 2
                    && getDecoratedEnd(child) > 0) {
                    child
                } else {
                    if (layoutManager.findLastCompletelyVisibleItemPosition()
                        == layoutManager.getItemCount() - 1) {
                        null
                    } else layoutManager.findViewByPosition(firstChild + 1)
                }
            }
        }
        return super.findSnapView(layoutManager)
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        return verticalHelper ?: OrientationHelper.createVerticalHelper(layoutManager).apply {
            verticalHelper = this
        }
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        return horizontalHelper ?: OrientationHelper.createHorizontalHelper(layoutManager).apply {
            horizontalHelper = this
        }
    }
}