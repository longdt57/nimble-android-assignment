package co.nimble.lee.assignment.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.nimble.lee.assignment.databinding.ItemSurveySliderBinding
import co.nimble.lee.assignment.model.SurveyUIModel
import co.nimble.lee.assignment.ui.screens.ext.loadSurveyCoverImage
import co.nimble.lee.assignment.ui.screens.ext.setOnSingleClickListener

class SurveyPagerAdapter(
    private val callback: ((SurveyUIModel, Int) -> Unit)
) : RecyclerView.Adapter<SurveyPagerAdapter.SurveyViewHolder>() {
    private val items: MutableList<SurveyUIModel> = mutableListOf()

    fun submitList(list: List<SurveyUIModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSurveySliderBinding.inflate(layoutInflater, parent, false)
        return SurveyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SurveyViewHolder, position: Int) {
        holder.bindSurveyData(items[position], position, callback)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    open class SurveyViewHolder(private val binding: ItemSurveySliderBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun bindSurveyData(item: SurveyUIModel, position: Int, callback: (SurveyUIModel, Int) -> Unit) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
            binding.btnTakeSurvey.setOnSingleClickListener {
                callback.invoke(item, position)
            }

            binding.ivCover.loadSurveyCoverImage(item.coverImageUrl.orEmpty())
        }
    }
}