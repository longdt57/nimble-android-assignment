package co.nimble.lee.assignment.ui.screens.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.model.SurveyUIModel
import co.nimble.lee.assignment.ui.screens.ext.loadSurveyCoverImage
import co.nimble.lee.assignment.ui.screens.ext.setOnSingleClickListener

class SurveyPagerAdapter(
    private val items: List<SurveyUIModel>,
    private val callback: ((SurveyUIModel, Int) -> Unit)
) : PagerAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun isViewFromObject(view: View, container: Any): Boolean {
        return view == container
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(container.context)
        val itemView = layoutInflater.inflate(R.layout.item_survey_slider, container, false)
        container.addView(itemView)

        val viewHolder = SurveyViewHolder(itemView)
        viewHolder.bindSurveyData(items[position], position, callback)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        (view as? View)?.let {
            container.removeView(view)
        }
    }

    open class SurveyViewHolder(private val itemView: View) {
        protected open val tvTitle: TextView
            get() = itemView.findViewById(R.id.tvTitle)
        protected open val tvDescription: TextView
            get() = itemView.findViewById(R.id.tvDescription)
        protected open val actionButton: View
            get() = itemView.findViewById(R.id.btnNext)
        protected open val ivCover: ImageView
            get() = itemView.findViewById(R.id.ivCover)

        open fun bindSurveyData(item: SurveyUIModel, position: Int, callback: (SurveyUIModel, Int) -> Unit) {
            tvTitle.text = item.title
            tvDescription.text = item.description
            actionButton.setOnSingleClickListener {
                callback.invoke(item, position)
            }

            ivCover.loadSurveyCoverImage(item.coverImageUrl.orEmpty())
        }
    }
}