package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rodab.ciencias.unam.mx.android.popularmovies.utilities.Review;

/**
 * Created by ricardo_rodab on 27/06/17.
 */

public class ShowReviewsAdapter extends RecyclerView.Adapter<ShowReviewsAdapter.ShowReviewsAdapterViewHolder> {

    public interface ShowReviewsAdapterOnClickHandler {
        public void onClick(Review review);
    }

    public class ShowReviewsAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final TextView author;
        public final TextView review;

        public ShowReviewsAdapterViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.tv_author);
            review = (TextView) itemView.findViewById(R.id.tv_review);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Review video = reviews[adapterPosition];
            showReviewsAdapterOnClickHandler.onClick(video);
        }
    }

    private Review[] reviews;
    private final ShowReviewsAdapterOnClickHandler showReviewsAdapterOnClickHandler;

    public void setReviewsData(Review[] videos) {
        this.reviews = videos;
        notifyDataSetChanged();
    }

    public ShowReviewsAdapter(ShowReviewsAdapterOnClickHandler mClickHandler) {
        this.showReviewsAdapterOnClickHandler = mClickHandler;
    }

    @Override
    public ShowReviewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context conext = parent.getContext();
        int layoutid = R.layout.show_movies_item_reviews;
        View view = LayoutInflater.from(conext).inflate(layoutid, parent, false);
        return new ShowReviewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowReviewsAdapterViewHolder holder, int position) {
        holder.author.setText("Author " + reviews[position].getAuthor());
        holder.review.setText(reviews[position].getContent());
    }

    @Override
    public int getItemCount() {
        if (reviews == null)
            return 0;
        return reviews.length;
    }

}