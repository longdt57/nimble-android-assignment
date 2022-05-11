package co.nimble.lee.assignment.ui.screens.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.model.SurveyUIModel
import com.bumptech.glide.Glide

class SurveyPagerAdapter(
    private val callback: ((SurveyUIModel) -> Unit)
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

        viewHolder.bindSurveyData(itemView, items[position])

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        (view as? View)?.let {
            container.removeView(view)
        }
    }

    open class SurveyViewHolder(private val itemView: View) {
        protected val tvTitle: TextView
            get() = itemView.findViewById(R.id.tvTitle)
        protected val tvDescription: TextView
            get() = itemView.findViewById(R.id.tvDescription)
        protected val ivCover: ImageView
            get() = itemView.findViewById(R.id.ivCover)

        open fun bindSurveyData(itemView: View, item: SurveyUIModel) {
            tvTitle.text = item.title
            tvDescription.text = item.description

            Glide.with(itemView.context)
                .load(item.coverImageUrl)
                .placeholder(R.drawable.nb_background)
                .into(ivCover)
        }
    }

    class LastSurveyViewHolder(itemView: View) : SurveyViewHolder(itemView)

}