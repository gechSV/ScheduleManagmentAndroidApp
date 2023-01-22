package ScheduleManagement.AndroidApp;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

    // Массив ресурсов для отображения
    private int[] _weekDay = {R.string.Monday, R.string.Tuesday, R.string.Wednesday,
            R.string.Thursday, R.string.Friday, R.string.Saturday, R.string.Sunday};

    private Context _context;

    // Конструктор ViewPager2Adapter класса
    ViewPager2Adapter(Context ctx) {
        this._context = ctx;
    }

    // Этот метод возвращает наш макет
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.page_holder_for_main_activity,
                parent, false);
//
//        return new ViewHolder(view);

        switch (viewType) {
            case 0: return new ViewHolder(view, 1);
            case 2: return new ViewHolder(view, 2);
            default: return new ViewHolder(view, 0);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    // Этот метод связывает экран с представлением
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
//        holder.textView.setText(_weekDay[position]);

        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder viewHolder0 = (ViewHolder)holder;
                viewHolder0.textView.setText("1");
                break;

            case 2:
                ViewHolder viewHolder2 = (ViewHolder)holder;
                viewHolder2.textView.setText("2");
                break;
        }
    }

    // Метод возвращает длину массива компонентов
    @Override
    public int getItemCount() {
        return _weekDay.length;
    }



    // The ViewHolder class holds the view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView, int n) {
            super(itemView);

//            int n = 10;
            textView = itemView.findViewById(R.id.textViewPage);
            linearLayout = itemView.findViewById((R.id.linear_layout_for_card));
//            linearLayout = new LinearLayout[n];

            LinearLayout[] buffView = new LinearLayout[n];

            for(int i = 0; i<n; i++){
                buffView[i] = (LinearLayout)LayoutInflater.from(itemView.getContext())
                        .inflate(R.layout.card_pattern_for_page, null);

                linearLayout.addView(buffView[i]);
            }
        }
    }
}
