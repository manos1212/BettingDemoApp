package com.example.bettingdemoapp.ui.expandableSportsView

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bettingdemoapp.databinding.LayoutCustomExpandableSportsCategoryBinding
import com.example.bettingdemoapp.domain.model.sportsEvent.CustomSportCategory
import com.example.bettingdemoapp.domain.model.sportsEvent.CustomSportsEvent
import com.example.bettingdemoapp.presentation.main.adapters.sportEventsAdapter.SportEventsAdapter

/**
 * A Expandable/Collapsible custom view that consists of an horizontal recyclerView.
 */
class ExpandableSportsView : ConstraintLayout {
    private lateinit var binding: LayoutCustomExpandableSportsCategoryBinding
    private var adapter: SportEventsAdapter? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }


    private fun init(attrs: AttributeSet?) {
        binding = LayoutCustomExpandableSportsCategoryBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }


    fun initView(
        item: CustomSportCategory,
        newItemPosition: (result: Int) -> Unit
    ) {
        setRecyclerViewAdapter(item.sportEvents, newItemPosition)
        setHeader(item.sportName)
        binding.categoryHeader.setOnClickListener { expandOrCollapseView() }


    }

    private fun expandOrCollapseView() {
        val visibility =
            if (binding.sportEventsRecyclerView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        binding.sportEventsRecyclerView.visibility = visibility
        rotateArrow(visibility)
    }

    private fun rotateArrow(visibility: Int) {
        if (visibility == View.VISIBLE) {
            binding.arrowIcon.rotation = binding.arrowIcon.rotation.plus(180)
        } else {
            binding.arrowIcon.rotation = binding.arrowIcon.rotation.minus(180)
        }
    }


    /**
     * Sets the title of the sport category.
     * @param title Title to set as a String value.
     */
    private fun setHeader(title: String?) {
        binding.categoryTitle.text = title
    }

    /**
     * Sets the recyclerView .
     */
    private fun setRecyclerViewAdapter(
        sportEvents: MutableList<CustomSportsEvent>,
        newItemPosition: (result: Int) -> Unit
    ) {
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.sportEventsRecyclerView.layoutManager = layoutManager
        adapter = SportEventsAdapter {
            newItemPosition(it)
        }
        binding.sportEventsRecyclerView.adapter = adapter
        adapter?.submitList(sportEvents.toList())
        binding.sportEventsRecyclerView.setHasFixedSize(false)
    }

    /**
     * Function used to update the list
     */

    fun updateListData(sportEvents: MutableList<CustomSportsEvent>) {
        adapter?.submitList(sportEvents.toList())
    }


    /**
     * Function used to handle the state of the recyclerView
     * @param state the saved State
     */
    fun handleState(state: Parcelable?) {
        if (state != null) {
            binding.sportEventsRecyclerView.layoutManager?.onRestoreInstanceState(state)
        } else {
            binding.sportEventsRecyclerView.layoutManager?.scrollToPosition(0)
        }
    }


}