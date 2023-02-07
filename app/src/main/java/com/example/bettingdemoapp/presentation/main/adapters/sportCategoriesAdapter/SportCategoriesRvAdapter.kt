package com.example.bettingdemoapp.presentation.main.adapters.sportCategoriesAdapter

import android.os.CountDownTimer
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.bettingdemoapp.R
import com.example.bettingdemoapp.databinding.LayoutSportsCategoryBinding
import com.example.bettingdemoapp.domain.model.sportsEvent.CustomSportCategory
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class SportCategoriesRvAdapter(
    data: MutableList<CustomSportCategory>,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var sportCategoriesList: MutableList<CustomSportCategory> = data
    private lateinit var sportsCategoryBinding: LayoutSportsCategoryBinding
    private val scrollStates: MutableMap<String, Parcelable?> = mutableMapOf()
    val listen: MutableLiveData<String> = MutableLiveData()
    private var timer: CountDownTimer

    companion object {
        const val ZERO_TIME = "00:00:00"
    }

    init {
        //set the timer which will refresh/decrease the time data by 1 every second.
        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(p0: Long) {
                sportCategoriesList = sportCategoriesList.map {
                    it.copy(sportEvents = it.sportEvents.map { ev ->
                        ev.copy(time = decrementTime(ev.time))
                    }.toMutableList())

                }.toMutableList()
                listen.value = "time changed"
            }

            override fun onFinish() {
                // do nothing
            }
        }
        timer.start()
    }


    //maybe call timer here instead of init
//    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(recyclerView)
//    }


    private fun getSectionID(position: Int): String {
        return sportCategoriesList[position].sportName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        sportsCategoryBinding =
            LayoutSportsCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderSportsCategoryItem(sportsCategoryBinding)

    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)

        //save horizontal scroll state
        val key = getSectionID(holder.layoutPosition)
        scrollStates[key] =
            (holder as ViewHolderSportsCategoryItem).itemView.findViewById<RecyclerView>(R.id.sportEventsRecyclerView).layoutManager?.onSaveInstanceState()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = sportCategoriesList[position]
        when (holder) {
            is ViewHolderSportsCategoryItem -> {
                holder.bind(item, position)
                Timber.d("ViewHolderSportsCategoryItem bind fun called")
            }
            else -> throw IllegalStateException("Invalid item type")
        }
    }

    override fun getItemCount(): Int {
        return sportCategoriesList.size
    }

    inner class ViewHolderSportsCategoryItem(private val itemBinding: LayoutSportsCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: CustomSportCategory, position: Int) {
            var shouldConsiderBackground = false

            var sportEventsList = item
            itemBinding.sportsView.initView(sportEventsList) {
                val changed = sportEventsList.sportEvents[it].copy(isFavourite = true)
                val changedParent =
                    sportCategoriesList[adapterPosition].sportEvents[it].copy(isFavourite = true)
                sportEventsList.sportEvents.removeAt(it)
                sportEventsList.sportEvents.add(0, changed)
                sportCategoriesList[position].sportEvents.removeAt(it)
                sportCategoriesList[position].sportEvents.add(0, changedParent)
                itemBinding.sportsView.updateListData(sportEventsList.sportEvents)
            }

            listen.observe(lifecycleOwner) {
                sportEventsList =
                    sportEventsList.copy(sportEvents = sportEventsList.sportEvents.map {
                        it.copy(time = decrementTime(it.time))
                    }.toMutableList())
                itemBinding.sportsView.updateListData(sportEventsList.sportEvents)

                //handle timer when in background // THERE is definitely a more efficient way to achieve this
                if (lifecycleOwner.lifecycle.currentState == Lifecycle.State.STARTED) {
                    shouldConsiderBackground = true
                }
                if (lifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED && shouldConsiderBackground) {
                        sportEventsList = sportCategoriesList[position]
                        shouldConsiderBackground = false
                }
            }

            val key = getSectionID(this.layoutPosition)
            val state = scrollStates[key]
            itemBinding.sportsView.handleState(state)

        }

    }

    private fun decrementTime(time: String): String {
        // if time reaches "00:00:00" then we stop countdown
        if (time == ZERO_TIME) return time

        val cal = Calendar.getInstance()
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return try {
            val date = formatter.parse(time)
            cal.time = date
            cal.add(Calendar.SECOND, -1)
            formatter.format(
                formatter?.parse(
                    cal.get(Calendar.HOUR).toString() + ":" + cal.get(
                        Calendar.MINUTE
                    ).toString() + ":" + cal.get(Calendar.SECOND).toString()
                )
            )
        } catch (e: Exception) {
            Timber.d("Exception thrown -> $e")
            ZERO_TIME
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        timer.cancel()
        super.onDetachedFromRecyclerView(recyclerView)
    }

}