package cat.itb.m08_uf1_p6_retrofit.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cat.itb.m08_uf1_p6_retrofit.R;
import cat.itb.m08_uf1_p6_retrofit.models.People;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {
    private List<People> peopleList;

    public PeopleAdapter(List<People> peopleList) {
        this.peopleList = peopleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        People p = peopleList.get(position);
        holder.getTextViewName().setText(p.getName());
        holder.getTextViewBirthYear().setText(p.getBirthYear());
        holder.getTextViewEyeColor().setText(p.getEyeColor());
        holder.getTextViewHeight().setText(String.valueOf(p.getHeight()));

    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName, textViewHeight, textViewEyeColor, textViewBirthYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewHeight = itemView.findViewById(R.id.textViewHeight);
            textViewEyeColor = itemView.findViewById(R.id.textViewEyeColor);
            textViewBirthYear = itemView.findViewById(R.id.textViewBirthYear);
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewHeight() {
            return textViewHeight;
        }

        public TextView getTextViewEyeColor() {
            return textViewEyeColor;
        }

        public TextView getTextViewBirthYear() {
            return textViewBirthYear;
        }
    }
}
