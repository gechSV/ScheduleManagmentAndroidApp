package ScheduleManagement.AndroidApp;

import java.lang.annotation.ElementType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorForEvent {
    private String colorHex;

    // Для проверки входной строки в методе setColorHex
    private static final String HEX_COLOR_PATTERN = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$";
    private static final Pattern pattern = Pattern.compile(HEX_COLOR_PATTERN);

    public ColorForEvent(){ }

    public ColorForEvent(String colorHex){
        setColorHex(colorHex);
    }

    /**
     *  Метод для проверки строки на соответствие формату шеснадцатиричного предстывления цвета
     * @param colorCode Строка длф проверки
     * @return true - ok; false - ne ok
     */
    public static boolean isValid(final String colorCode) {
        Matcher matcher = pattern.matcher(colorCode);
        return matcher.matches();
    }


    public void setColorHex(String colorHex) {

        if (colorHex == null){
            throw new Error("the colorHex attribute cannot be null");
        }

        if (!isValid(colorHex)){
            throw new Error("invalid color format:" + colorHex);
        } else {
            this.colorHex = colorHex;
        }
    }

    public String getColorHex() {
        return this.colorHex;
    }
}
