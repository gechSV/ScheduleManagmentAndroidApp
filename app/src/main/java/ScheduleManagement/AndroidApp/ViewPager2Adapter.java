package ScheduleManagement.AndroidApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

    // Дни недели - массив ресурсов для отображения
    private int[] _weekDay = {R.string.Monday, R.string.Tuesday, R.string.Wednesday,
            R.string.Thursday, R.string.Friday, R.string.Saturday, R.string.Sunday};

    // Список событий
    EventScheduleList _eventScheduleList;

    private final Context _context;

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
                ((ViewHolder)holder).textView.setText(_weekDay[0]);
                break;
            case 1:
                ((ViewHolder)holder).textView.setText(_weekDay[1]);
                break;
            case 2:
                ((ViewHolder)holder).textView.setText(_weekDay[2]);
                break;
            case 3:
                ((ViewHolder)holder).textView.setText(_weekDay[3]);
                break;
            case 4:
                ((ViewHolder)holder).textView.setText(_weekDay[4]);
                break;
            case 5:
                ((ViewHolder)holder).textView.setText(_weekDay[5]);
                break;
            case 6:
                ((ViewHolder)holder).textView.setText(_weekDay[6]);
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

        void SetTextData(EventSchedule event, CardView cardView){
            CardView cardTime = cardView.findViewById(R.id.card_time);
            TextView textViewStartTime = cardView.findViewById(R.id.textViewCardTimeStart);
            TextView textViewEndTime = cardView.findViewById(R.id.textViewCardTimeEnd);
            TextView textViewName = cardView.findViewById(R.id.event_name);
            TextView textViewType = cardView.findViewById(R.id.event_type);
            TextView textViewHost = cardView.findViewById(R.id.event_host);
            TextView textViewLocation = cardView.findViewById(R.id.event_location);

            textViewStartTime.setText(event.GetStartTimeEvent());
            textViewEndTime.setText(event.GetEndTimeEvent());
            textViewName.setText(event.GetNameEvent());
            textViewType.setText(event.GetTypeEvent());
            textViewHost.setText(event.GetHost());
            textViewLocation.setText(event.GetPlaceEvent());
        }

        public ViewHolder(@NonNull View itemView, ArrayList<EventSchedule> dayEvent, int n) {
            super(itemView);

            int i = 0; //счётчик для массива
            textView = itemView.findViewById(R.id.textViewPage);
            linearLayout = itemView.findViewById((R.id.linear_layout_for_card));

            // для хранения карточек в памяти
            LinearLayout[] buffView = new LinearLayout[dayEvent.size()];

            // перебор списка событий и вывод на экран карточек
            for(EventSchedule event: dayEvent){
                // копируем макет
                buffView[i] = (LinearLayout)LayoutInflater.from(itemView.getContext())
                        .inflate(R.layout.card_pattern_for_page, null);

                // Получаем объект карточки
                CardView cardView = buffView[i].findViewById(R.id.event_card);
                CardView cardTime = cardView.findViewById(R.id.card_time);
                CardView iconType = cardView.findViewById(R.id.icon_event_type);
                CardView iconHost = cardView.findViewById(R.id.icon_event_host);
                CardView iconLocation = cardView.findViewById(R.id.icon_event_location);

                // Установка цвета катрочки
                switch (event.GetColorForEvent()){
                    case 1:
                        SetTextData(event, cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_lime);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_lime);
                        iconType.setBackgroundResource(R.drawable.style_for_card_time_lime);
                        iconHost.setBackgroundResource(R.drawable.style_for_card_time_lime);
                        iconLocation.setBackgroundResource(R.drawable.style_for_card_time_lime);
                        break;
                    case 2:
                        SetTextData(event, cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_green);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_green);
                        iconType.setBackgroundResource(R.drawable.style_for_card_time_green);
                        iconHost.setBackgroundResource(R.drawable.style_for_card_time_green);
                        iconLocation.setBackgroundResource(R.drawable.style_for_card_time_green);
                        break;
                    case 3:
                        SetTextData(event, cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_blue);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_blue);
                        iconType.setBackgroundResource(R.drawable.style_for_card_time_blue);
                        iconHost.setBackgroundResource(R.drawable.style_for_card_time_blue);
                        iconLocation.setBackgroundResource(R.drawable.style_for_card_time_blue);
                        break;
                    case 4:
                        SetTextData(event, cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_purple);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_purple);
                        iconType.setBackgroundResource(R.drawable.style_for_card_time_purple);
                        iconHost.setBackgroundResource(R.drawable.style_for_card_time_purple);
                        iconLocation.setBackgroundResource(R.drawable.style_for_card_time_purple);
                        break;
                    case 5:
                        SetTextData(event, cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_pink);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_pink);
                        iconType.setBackgroundResource(R.drawable.style_for_card_time_pink);
                        iconHost.setBackgroundResource(R.drawable.style_for_card_time_pink);
                        iconLocation.setBackgroundResource(R.drawable.style_for_card_time_pink);
                        break;
                    case 6:
                        SetTextData(event, cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_red);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_red);
                        iconType.setBackgroundResource(R.drawable.style_for_card_time_red);
                        iconHost.setBackgroundResource(R.drawable.style_for_card_time_red);
                        iconLocation.setBackgroundResource(R.drawable.style_for_card_time_red);
                        break;
                    case 7:
                        SetTextData(event, cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_orange);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_orange);
                        iconType.setBackgroundResource(R.drawable.style_for_card_time_orange);
                        iconHost.setBackgroundResource(R.drawable.style_for_card_time_orange);
                        iconLocation.setBackgroundResource(R.drawable.style_for_card_time_orange);
                        break;
                    case 8:
                        SetTextData(event, cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_gray);
                        iconType.setBackgroundResource(R.drawable.style_for_card_time_gray);
                        iconHost.setBackgroundResource(R.drawable.style_for_card_time_gray);
                        iconLocation.setBackgroundResource(R.drawable.style_for_card_time_gray);
                        break;
                    case 9:
                        SetTextData(event, cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_teal);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_teal);
                        iconType.setBackgroundResource(R.drawable.style_for_card_time_teal);
                        iconHost.setBackgroundResource(R.drawable.style_for_card_time_teal);
                        iconLocation.setBackgroundResource(R.drawable.style_for_card_time_teal);
                        break;
                    case 10:
                        SetTextData(event, cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_brown);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_brown);
                        iconType.setBackgroundResource(R.drawable.style_for_card_time_brown);
                        iconHost.setBackgroundResource(R.drawable.style_for_card_time_brown);
                        iconLocation.setBackgroundResource(R.drawable.style_for_card_time_brown);
                        break;
                    default:
                        SetTextData(event, cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_gray);
                        iconType.setBackgroundResource(R.drawable.style_for_card_time_gray);
                        iconHost.setBackgroundResource(R.drawable.style_for_card_time_gray);
                        iconLocation.setBackgroundResource(R.drawable.style_for_card_time_gray);
                        break;
                }

                linearLayout.addView(buffView[i]);
                i++;
            }
        }
    }
}
