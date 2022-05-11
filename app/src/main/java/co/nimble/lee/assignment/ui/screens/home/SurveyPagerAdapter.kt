package co.nimble.lee.assignment.ui.screens.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.model.SurveyUIModel
import co.nimble.lee.assignment.ui.screens.ext.setOnSingleClickListener
import com.bumptech.glide.Glide

class SurveyPagerAdapter(
    private val callback: ((SurveyUIModel, Int) -> Unit)
) : PagerAdapter() {

    private val items = mutableListOf<SurveyUIModel>()

    fun submitList(list: List<SurveyUIModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun isViewFromObject(view: View, container: Any): Boolean {
        return view == container
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(container.context)
        val isLastSurvey = position == count - 1
        val layoutId = when (isLastSurvey) {
            true -> R.layout.nb_item_last_survey_slider
            else -> R.layout.nb_item_survey_slider
        }
        val itemView = layoutInflater.inflate(layoutId, container, false)
        container.addView(itemView)

        val viewHolder = if (isLastSurvey) SurveyViewHolder(itemView)
        else LastSurveyViewHolder(itemView)

        viewHolder.bindSurveyData(items[position], position, callback)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        (view as? View)?.let {
            container.removeView(view)
        }
    }

    open class SurveyViewHolder(val itemView: View) {
        protected open val tvTitle: TextView
            get() = itemView.findViewById(R.id.tvTitle)
        protected open val tvDescription: TextView
            get() = itemView.findViewById(R.id.tvDescription)
        protected open val actionButton: ImageView
            get() = itemView.findViewById(R.id.btnNext)
        protected open val ivCover: ImageView
            get() = itemView.findViewById(R.id.ivCover)

        open fun bindSurveyData(item: SurveyUIModel, position: Int, callback: (SurveyUIModel, Int) -> Unit) {
            tvTitle.text = item.title
            tvDescription.text = item.description
            actionButton.setOnSingleClickListener {
                callback.invoke(item, position)
            }

            Glide.with(itemView.context)
                .load(item.coverImageUrl)
                .placeholder(R.drawable.nb_background)
                .into(ivCover)
        }
    }

    class LastSurveyViewHolder(itemView: View) : SurveyViewHolder(itemView) {
        override val actionButton: ImageView
            get() = itemView.findViewById(R.id.btnSurvey)
    }

}