package ScheduleManagement.AndroidApp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

    // Дни недели - массив ресурсов для отображения
    private int[] _weekDay = {R.string.Monday, R.string.Tuesday, R.string.Wednesday,
            R.string.Thursday, R.string.Friday, R.string.Saturday, R.string.Sunday};

    // Список событий
    EventScheduleList _eventScheduleList;

    private Context _context;

    // Конструктор ViewPager2Adapter класса
    ViewPager2Adapter(Context ctx, EventScheduleList eventScheduleList) {
        this._eventScheduleList = eventScheduleList;
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
            case 0: return new ViewHolder(view, _eventScheduleList.GetEventByDayWeek(0) ,0);
            case 1: return new ViewHolder(view, _eventScheduleList.GetEventByDayWeek(1) ,1);
            case 2: return new ViewHolder(view, _eventScheduleList.GetEventByDayWeek(2) ,2);
            case 3: return new ViewHolder(view, _eventScheduleList.GetEventByDayWeek(3) ,3);
            case 4: return new ViewHolder(view, _eventScheduleList.GetEventByDayWeek(4) ,4);
            case 5: return new ViewHolder(view, _eventScheduleList.GetEventByDayWeek(5) ,5);
            case 6: return new ViewHolder(view, _eventScheduleList.GetEventByDayWeek(6) ,6);
            default: return new ViewHolder(view, _eventScheduleList.GetEventByDayWeek(0) ,-1);
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
                viewHolder0.textView.setText(_weekDay[0]);
                break;
            case 1:
                ViewHolder viewHolder1 = (ViewHolder)holder;
                viewHolder1.textView.setText(_weekDay[1]);
                break;
            case 2:
                ViewHolder viewHolder2 = (ViewHolder)holder;
                viewHolder2.textView.setText(_weekDay[2]);
                break;
            case 3:
                ViewHolder viewHolder3 = (ViewHolder)holder;
                viewHolder3.textView.setText(_weekDay[3]);
                break;
            case 4:
                ViewHolder viewHolder4 = (ViewHolder)holder;
                viewHolder4.textView.setText(_weekDay[4]);
                break;
            case 5:
                ViewHolder viewHolder5 = (ViewHolder)holder;
                viewHolder5.textView.setText(_weekDay[5]);
                break;
            case 6:
                ViewHolder viewHolder6 = (ViewHolder)holder;
                viewHolder6.textView.setText(_weekDay[6]);
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

        public ViewHolder(@NonNull View itemView, ArrayList<EventSchedule> dayEvent, int n) {
            super(itemView);

            textView = itemView.findViewById(R.id.textViewPage);
            linearLayout = itemView.findViewById((R.id.linear_layout_for_card));

            LinearLayout[] buffView = new LinearLayout[dayEvent.size()];

            for(int i = 0; i < dayEvent.size(); i++){
                buffView[i] = (LinearLayout)LayoutInflater.from(itemView.getContext())
                        .inflate(R.layout.card_pattern_for_page, null);

                linearLayout.addView(buffView[i]);
            }
        }
    }
}
