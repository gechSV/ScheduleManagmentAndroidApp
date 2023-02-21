package ScheduleManagement.AndroidApp;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Класс-адаптер для компонента ViewPager2
class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder>{

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

//        switch (holder.getItemViewType()) {
//            case 0:
//                ((ViewHolder)holder);
//                break;
//            case 1:
//                ((ViewHolder)holder).textView.setText(_weekDay[1]);
//                break;
//            case 2:
//                ((ViewHolder)holder).textView.setText(_weekDay[2]);
//                break;
//            case 3:
//                ((ViewHolder)holder).textView.setText(_weekDay[3]);
//                break;
//            case 4:
//                ((ViewHolder)holder).textView.setText(_weekDay[4]);
//                break;
//            case 5:
//                ((ViewHolder)holder).textView.setText(_weekDay[5]);
//                break;
//            case 6:
//                ((ViewHolder)holder).textView.setText(_weekDay[6]);
//                break;
//        }
    }

    // Метод возвращает длину массива компонентов
    @Override
    public int getItemCount() {
        return _weekDay.length;
    }

    // Класс для создания и заполнения макета для отображения на каждой странице ViewPager2
    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        private int index;
        private int id;
        /**
         * Конструктор холдера
         **/
        public ViewHolder(@NonNull View itemView, ArrayList<EventSchedule> dayEvent, int n) {
            super(itemView);

            int arrayCounter = 0; //счётчик для массива
            linearLayout = itemView.findViewById((R.id.linear_layout_for_card));

            // для хранения карточек в памяти
            LinearLayout[] buffView = new LinearLayout[dayEvent.size()];

            Intent _IntentAddEvent = new Intent(itemView.getContext(), ActivityAddScheduleItem.class);

            // перебор списка событий и вывод на экран карточек
            for(index = 0; index < dayEvent.size(); index++){
//                id = dayEvent.get(index).GetId();
                // копируем макет
                buffView[arrayCounter] = (LinearLayout)LayoutInflater.from(itemView.getContext())
                        .inflate(R.layout.card_pattern_for_page, null);

                // Получаем объект карточки
                CardView cardView = buffView[arrayCounter].findViewById(R.id.event_card);
                CardView cardTime = cardView.findViewById(R.id.card_time);
                CardView editCard = cardView.findViewById(R.id.edit_event);
                CardView deleteCard = cardView.findViewById(R.id.delete_event);
                LinearLayout LinerLayoutActionForCard = buffView[arrayCounter].findViewById(R.id.EditButton);
                TextView idEvent = cardView.findViewById(R.id.idEvent);
                idEvent.setText(Integer.toString(dayEvent.get(index).GetId()));

                // ------- Долгое нажатие на карточку -------
                cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    boolean flag = false;
                    @Override
                    public boolean onLongClick(View view) {
                        final int timeForAnimation = 300;
                        // Логика работы выезжающих кнопок карточек
                        if(flag){
                            // Убрать кнопки

                            // Задаём параметры анимации
                            // Анимация для Карточки времени
                            TranslateAnimation animation = new TranslateAnimation
                                    (DpInPxDisplay.ConvertDpToPixels(
                                            view.getContext(), -64), DpInPxDisplay.ConvertDpToPixels(
                                                    view.getContext(), 0), 0, 0);

                            animation.setDuration(200);
                            animation.setFillAfter(true);

                            // Анимация для кнопок: edit и delete
                            TranslateAnimation animation2 = new TranslateAnimation(
                                    DpInPxDisplay.ConvertDpToPixels(view.getContext(), 0),
                                    DpInPxDisplay.ConvertDpToPixels(view.getContext(), 64),
                                    0, 0);

                            animation2.setDuration(200);
                            animation2.setFillAfter(true);

                            // Запуск анимации
                            cardTime.startAnimation(animation);
                            LinerLayoutActionForCard.startAnimation(animation2);

                            // Скрываем компонент
                            LinerLayoutActionForCard.setVisibility(View.GONE);
                        }
                        else{
                            // ____ Показать кнопки
                            // Включаем отображение компонентов
                            LinerLayoutActionForCard.setVisibility(View.VISIBLE);
                            editCard.setVisibility(View.VISIBLE);
                            deleteCard.setVisibility(View.VISIBLE);

                            // Задаём параметры анимации
                            TranslateAnimation animation = new TranslateAnimation(
                                    DpInPxDisplay.ConvertDpToPixels(view.getContext(), 64),
                                    DpInPxDisplay.ConvertDpToPixels(view.getContext(), 0),
                                    0, 0);

                            animation.setDuration(200);
                            animation.setFillAfter(true);

                            // Запуск анимации
                            LinerLayoutActionForCard.startAnimation(animation);
                            cardTime.startAnimation(animation);
                        }

                        flag = !flag;
                        return false;
                    }
                });

                editCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    MainActivity.getInstance().OnClickEditEvent(Integer.parseInt(String.valueOf(idEvent.getText())));
                    }
                });

                // Нажатие на кнопку удаления эвента
                deleteCard.setOnClickListener(new CardView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Настройка диалогового окна, предназначенного для подтверждения удаления
                        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext(),
                                R.style.AlertDialogCustom);
                        builder.setMessage(R.string.confirmation_of_deletion);
                        builder.setCancelable(false);

                        // Кнопка ОТМЕНИТЬ
                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        // Кнопка УДАЛИТЬ
                        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(itemView.getContext(), idEvent.getText(),
                                        Toast.LENGTH_LONG).show();

                                // Удаление события из списка и перезапись списка в файл
                                // В данном методе присутствует товарищ костыль
                                // id события мы берём из невидемого TextView карточке:))
                               MainActivity.getInstance().OnClickDeleteEvent(Integer.parseInt(String.valueOf(idEvent.getText())));

                               // Вызываем метод MainActivity, который обновляет ViewPager
                                MainActivity.getInstance().ReloadViewPager_1();
                                MainActivity.getInstance().ReloadViewPager_2();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();

                        // Настройка внешнего вида кнопок
                        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                        Button pButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);

                        pButton.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.deep_orange_300));
                    }
                });

                // Установка цвета карточки. По умолчанию (если не выбран цвет) - серый
                switch (dayEvent.get(index).GetColorForEvent()){
                    case 1:
                        SetTextData(dayEvent.get(index), cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_lime);
                        break;
                    case 2:
                        SetTextData(dayEvent.get(index), cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_green);
                        break;
                    case 3:
                        SetTextData(dayEvent.get(index), cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_blue);
                        break;
                    case 4:
                        SetTextData(dayEvent.get(index), cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_purple);
                        break;
                    case 5:
                        SetTextData(dayEvent.get(index), cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_pink);
                        break;
                    case 6:
                        SetTextData(dayEvent.get(index), cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_red);
                        break;
                    case 7:
                        SetTextData(dayEvent.get(index), cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_orange);
                        break;
                    case 8:
                        SetTextData(dayEvent.get(index), cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_gray);
                        break;
                    case 9:
                        SetTextData(dayEvent.get(index), cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_teal);
                        break;
                    case 10:
                        SetTextData(dayEvent.get(index), cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_brown);
                        break;
                    default:
                        SetTextData(dayEvent.get(index), cardView);
                        cardView.setBackgroundResource(R.drawable.style_for_card_gray);
                        cardTime.setBackgroundResource(R.drawable.style_for_card_time_gray);
                        break;
                }

                // Фон кнопок редактирования и удаления у всех карточек одинаковы
                editCard.setBackgroundResource(R.drawable.style_for_edit_card);
                deleteCard.setBackgroundResource(R.drawable.style_for_delete_card);

                linearLayout.addView(buffView[arrayCounter]);
                arrayCounter++;
            }


        }

        void SetTextData(EventSchedule event, CardView cardView){
            CardView cardTime = cardView.findViewById(R.id.card_time);
            TextView textViewStartTime = cardView.findViewById(R.id.textViewCardTimeStart);
            TextView textViewEndTime = cardView.findViewById(R.id.textViewCardTimeEnd);
            TextView textViewName = cardView.findViewById(R.id.event_name);
            TextView textViewType = cardView.findViewById(R.id.event_type);
            TextView textViewHost = cardView.findViewById(R.id.event_host);
            TextView textViewLocation = cardView.findViewById(R.id.event_location);

            textViewStartTime.setText(event.GetStartTimeEvent().replace(':', '꞉'));
            textViewEndTime.setText(event.GetEndTimeEvent().replace(':', '꞉'));
            textViewName.setText(event.GetNameEvent());
            textViewType.setText(event.GetTypeEvent());
            textViewHost.setText(event.GetEventHost());
            textViewLocation.setText(event.GetLocationEvent());
        }
    }

}


//new AlertDialog.Builder(itemView.getContext(), R.style.AlertDialogCustom)
//                                // Specifying a listener allows you to take an action before dismissing the dialog.
//                                // The dialog is automatically dismissed when a dialog button is clicked.
//                                .setItems(catNamesArray, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        Toasty.success(itemView.getContext(), "Num" + i,
//                                                Toast.LENGTH_SHORT, true).show();
//                                    }
//                                })
//                                // A null listener allows the button to dismiss the dialog and take no further action.
//                                .show();
