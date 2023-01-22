package ScheduleManagement.AndroidApp;
import android.content.Context;
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

        return new ViewHolder(view);
    }

    // Этот метод связывает экран с представлением
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
        holder.textView.setText(_weekDay[position]);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textViewPage);
            linearLayout = itemView.findViewById((R.id.linear_layout_for_card));

            LinearLayout buffView = (LinearLayout)LayoutInflater.from(itemView.getContext())
                    .inflate(R.layout.card_pattern_for_page, null);

            LinearLayout buffView2 = (LinearLayout)LayoutInflater.from(itemView.getContext())
                    .inflate(R.layout.card_pattern_for_page, null);


            linearLayout.addView(buffView);
            linearLayout.addView(buffView2);
        }
    }
}
