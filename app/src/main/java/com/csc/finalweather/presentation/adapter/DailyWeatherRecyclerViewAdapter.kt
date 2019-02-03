package com.csc.finalweather.presentation.adapter


import android.app.Activity
import android.content.Context
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.support.annotation.NonNull
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.csc.finalweather.R
import com.csc.finalweather.data.model.db.DailyWeatherTableDbModel
import com.csc.finalweather.presentation.presenter.getDrawableByWeatherIconName


class DailyWeatherRecyclerViewAdapter(val context: Context, private val dailyForecastList: ArrayList<DailyWeatherTableDbModel>) : RecyclerView.Adapter<DailyWeatherRecyclerViewAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null
    private val MAX_GRID_NR = 8

    init {
        this.mInflater = LayoutInflater.from(context)
    }

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_daily_weather, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the view and textview in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dailyForecastModel = dailyForecastList[position]

        holder.dayNameTextView.text = dailyForecastModel.formattedTime
        holder.tempMaxTextView.text = dailyForecastModel.temperatureMax
        holder.tempMinTextView.text = dailyForecastModel.temperatureMin
        holder.weatherIconImageView.setImageDrawable(getDrawableByWeatherIconName(this.context as Activity, dailyForecastModel.icon));

    }

    // total number of rows
    override fun getItemCount(): Int {
        return MAX_GRID_NR
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        internal var dayNameTextView: TextView
        internal var tempMaxTextView: TextView
        internal var tempMinTextView: TextView
        internal var weatherIconImageView: ImageView

        init {
            dayNameTextView = itemView.findViewById(R.id.dayNameTextView)
            tempMaxTextView = itemView.findViewById(R.id.tempMaxTextView)
            tempMinTextView = itemView.findViewById(R.id.tempMinTextView)
            weatherIconImageView = itemView.findViewById(R.id.weatherIconImageView)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): DailyWeatherTableDbModel {
        return dailyForecastList[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}