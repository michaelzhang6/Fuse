package us.mzhang.fuse.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import us.mzhang.fuse.R
import kotlinx.android.synthetic.main.profile_media_row_layout.view.*
import us.mzhang.fuse.ProfileActivity
import us.mzhang.fuse.QRActivity
import us.mzhang.fuse.SocialIntent
import us.mzhang.fuse.data.User

class MediaAdapter : RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon = itemView.ivIcon
        val tvUsername = itemView.tvUserName
        val btnAddEdit = itemView.btnAddEdit
    }

    var mediaList = mutableListOf("snapchat", "twitter", "instagram")

    val context: Context
    val user: User

    constructor(context: Context, user: User) {
        this.context = context
        this.user = user
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mediaRow = LayoutInflater.from(context).inflate(
            R.layout.profile_media_row_layout, parent, false
        )

        return ViewHolder(mediaRow)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var media = mediaList.get(holder.adapterPosition)

        if (media == "instagram") {
            holder.ivIcon.setImageResource(R.drawable.ig_glyph_fill)
        } else if (media == "twitter") {
            holder.ivIcon.setImageResource(R.drawable.ic_action_twitter)
        } else if (media == "snapchat") {
            holder.ivIcon.setImageResource(R.drawable.snap_ghost_yellow)
        }

        if (media in user.socialSet) {
            holder.tvUsername.text = user.socialSet.get(media)
        } else {
            holder.tvUsername.text = context.getString(R.string.na)
        }

        if (context is ProfileActivity) {
            if (holder.tvUsername.text != context.getString(R.string.na)) {
                holder.btnAddEdit.setImageResource(R.drawable.ic_edit)
            }
            
            holder.btnAddEdit.setOnClickListener {
                context.showMediaDialog(
                    holder.tvUsername.text.toString(), media
                )
            }
        } else if (context is QRActivity) {
            holder.btnAddEdit.setOnClickListener {
                var social = SocialIntent(context, user)
                social.launchIntent(media)
            }
        }

    }
}