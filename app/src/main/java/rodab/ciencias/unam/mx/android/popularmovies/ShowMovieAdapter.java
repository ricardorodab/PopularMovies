package rodab.ciencias.unam.mx.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Jose Ricardo Rodriguez-Abreu
 * @version 1.0
 * @since Jun 26 2017.
 * <p>
 *     A contract class.
 * </p>
 *
 * <p>
 *     For constant variables like url's and names for the DB.
 * </p>
 */
public class ShowMovieAdapter extends RecyclerView.Adapter<ShowMovieAdapter.ShowMovieAdapterViewHolder> {

    public interface ShowMovieAdapterOnClickHandler {
        public void onClick(String str);
    }

    public class ShowMovieAdapterViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {

        public final TextView videoLabel;
        public final LinearLayout link;

        public ShowMovieAdapterViewHolder(View itemView) {
            super(itemView);
            videoLabel = (TextView) itemView.findViewById(R.id.tv_trailer);
            link = (LinearLayout) itemView.findViewById(R.id.ll_link);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String video = videoLinks[adapterPosition];
            showMovieAdapterOnClickHandler.onClick(video);
        }
    }

    private String[] videoLinks;
    private String[] reviews;
    private final ShowMovieAdapterOnClickHandler showMovieAdapterOnClickHandler;

    public void setMovieData(String[] videos) {
        this.videoLinks = videos;
        notifyDataSetChanged();
    }

    public ShowMovieAdapter(ShowMovieAdapterOnClickHandler mClickHandler) {
        this.showMovieAdapterOnClickHandler = mClickHandler;
    }

    @Override
    public ShowMovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context conext = parent.getContext();
        int layoutid = R.layout.show_movie_item;
        View view = LayoutInflater.from(conext).inflate(layoutid, parent, false);
        return new ShowMovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowMovieAdapterViewHolder holder, int position) {
        holder.videoLabel.setText("Trailer " + (position+1));
    }

    @Override
    public int getItemCount() {
        if (videoLinks == null)
            return 0;
        return videoLinks.length;
    }

}
