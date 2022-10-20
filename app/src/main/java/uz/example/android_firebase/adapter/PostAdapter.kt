package uz.example.android_firebase.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import uz.example.android_firebase.R
import uz.example.android_firebase.activity.EditActivity
import uz.example.android_firebase.activity.MainActivity
import uz.example.android_firebase.model.Post
import java.lang.String
import kotlin.Int

class PostAdapter(var activity: MainActivity, var items:ArrayList<Post>):RecyclerView.Adapter<PostAdapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_posts,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item:Post = items[position]
        val sl_swipe: SwipeLayout = holder.sl_swipe
        sl_swipe.showMode = SwipeLayout.ShowMode.PullOut
        sl_swipe.addDrag(SwipeLayout.DragEdge.Left, holder.linear_left)
        sl_swipe.addDrag(SwipeLayout.DragEdge.Right, holder.linear_right)


        holder.tv_title.text = item.title
        holder.tv_body.text = item.body

        holder.tv_delete.setOnClickListener{ activity.dialogPoster(item) }

        holder.tv_edit.setOnClickListener{
            val intent = Intent(activity.baseContext, EditActivity::class.java)
            intent.putExtra("id",item.id)
            intent.putExtra("title", item.title)
            intent.putExtra("body", item.body)
            //activity.setResult(88,intent);
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tv_title:TextView
        lateinit var tv_body:TextView
        var linear_left: LinearLayout
        var linear_right: LinearLayout
        var sl_swipe: SwipeLayout
        var tv_delete: TextView
        var tv_edit: TextView
        init {
            tv_title = itemView.findViewById(R.id.tv_title_item)
            tv_body = itemView.findViewById(R.id.tv_body_item)
            linear_right = itemView.findViewById(R.id.ll_linear_right)
            linear_left = itemView.findViewById(R.id.ll_linear_left)
            sl_swipe = itemView.findViewById(R.id.sl_swipe)
            tv_delete = itemView.findViewById(R.id.tv_delete)
            tv_edit = itemView.findViewById(R.id.tv_edit)
        }
    }
}