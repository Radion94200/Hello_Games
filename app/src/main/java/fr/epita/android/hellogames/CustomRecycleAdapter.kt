import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.epita.android.hellogames.GameObject
import fr.epita.android.hellogames.R

class CustomRecyclerAdapter(val context: Context, val data: MutableList<GameObject>, private val onItemClickListener: View.OnClickListener) : RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView = LayoutInflater
            .from(context) .inflate(R.layout.list_item, parent, false)
        // attach the onclicklistener
        rowView.setOnClickListener(onItemClickListener) // create a ViewHolder using this rowview
        val viewHolder = ViewHolder(rowView)
        // return this ViewHolder. The system will make sure view holders // are used and recycled
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder!!.nameTextView.text = currentItem.name
        holder!!.itemView.tag = currentItem.id
        Glide.with(context).load(currentItem.picture).into(holder.imageView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name_game)
        val imageView: ImageView = itemView.findViewById(R.id.image_game)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}