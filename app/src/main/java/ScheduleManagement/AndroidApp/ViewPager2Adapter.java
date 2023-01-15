package ScheduleManagement.AndroidApp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

    // Массив ресурсов для отображения
    private int[] images = {R.drawable.style_for_choice_button_black, R.drawable.style_for_choice_button_brown_click, R.drawable.style_for_choice_button_cactus,
            R.drawable.style_for_choice_button_blue, R.drawable.style_for_choice_button_lime_click};

    private Context _context;

    // Конструктор ViewPager2Adapter класса
    ViewPager2Adapter(Context ctx) {
        this._context = ctx;
    }

    // Этот метод возвращает наш макет
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.page_holder_for_main_activity, parent, false);
        return new ViewHolder(view);
    }

    // Этот метод связывает экран с представлением
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
        holder.images.setImageResource(images[position]);
    }

    // Метод возвращает длину массива компонентов
    @Override
    public int getItemCount() {
        return images.length;
    }

    // The ViewHolder class holds the view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.images);
        }
    }
}
