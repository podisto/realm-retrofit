package android.courses.realmretrofit.adapters;

import android.courses.realmretrofit.R;
import android.courses.realmretrofit.models.Content;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Podisto on 15/05/2016.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.CharacterViewHolder> {

    private List<Content> contentList;

    public ContentAdapter(List<Content> contentList) {
        this.contentList = contentList;
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        Content content = contentList.get(position);
        holder.draweeView.setImageURI(Uri.parse(content.getImage()));
        holder.title.setText(content.getTitle());
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView draweeView;
        private TextView title;

        public CharacterViewHolder(View view) {
            super(view);
            draweeView = (SimpleDraweeView) view.findViewById(R.id.sw_image);
            title = (TextView) view.findViewById(R.id.sw_title);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
