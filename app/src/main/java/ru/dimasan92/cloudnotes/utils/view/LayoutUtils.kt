package ru.dimasan92.cloudnotes.utils.view

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.dimasan92.cloudnotes.utils.view.LayoutUtils.Type

private const val APPROXIMATELY_WIDTH_IN_DP = 200
private const val MIN_SPAN_COUNT = 2

interface LayoutUtils {

    enum class Type {
        LINEAR,
        GRID,
        STAGGERED
    }

    fun getAdjustedLayoutManager(
        type: Type,
        orientation: Int = RecyclerView.VERTICAL
    ): LayoutManager
}

class LayoutUtilsImpl(private val context: Context) : LayoutUtils {

    override fun getAdjustedLayoutManager(type: Type, orientation: Int): LayoutManager =
        when (type) {
            Type.LINEAR -> LinearLayoutManager(context)
                .apply { this.orientation = orientation }
            Type.GRID -> GridLayoutManager(context, getSpanGridCount())
                .apply { this.orientation = orientation }
            Type.STAGGERED -> StaggeredGridLayoutManager(getSpanGridCount(), orientation)
        }

    private fun getSpanGridCount(): Int {
        val displayMetrics = context.resources.displayMetrics
        val widthInDp = displayMetrics.widthPixels / displayMetrics.density
        val spanCount = (widthInDp / APPROXIMATELY_WIDTH_IN_DP).toInt()
        return if (spanCount < MIN_SPAN_COUNT) {
            MIN_SPAN_COUNT
        } else {
            spanCount
        }
    }
}