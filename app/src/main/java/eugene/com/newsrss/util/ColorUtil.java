package eugene.com.newsrss.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;

public class ColorUtil {

    public static int[] getColors(String colorPrimary) {
        int primary = Color.parseColor(colorPrimary);
        int[] colors = new int[4];
        colors[0] = primary;
        colors[1] = colorPrimary.equals("#FFFFFF") ? Color.parseColor("#CCCCCC") : colorPrimaryDark(primary);
        colors[2] = getContrastColor(primary);
        colors[3] = colorPrimary.equals("#FFFFFF") ? Color.parseColor("#000000") : colors[1];
        return colors;
    }

    public static int colorPrimaryDark(int colorPrimary) {
        float[] hsv = new float[3];
        Color.colorToHSV(colorPrimary, hsv);
        float[] hsl = hsv2hsl(hsv);
        hsl[2] -= 12 / 100f;
        if (hsl[2] < 0)
            hsl[2] = 0f;
        hsv = hsl2hsv(hsl);
        return Color.HSVToColor(hsv);
    }

    @ColorInt
    private static int getContrastColor(@ColorInt int color) {
        // Counting the perceptive luminance - human eye favors green color...
        double a = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return a < 0.5 ? Color.BLACK : Color.WHITE;
    }

    /**
     * Converts HSV (Hue, Saturation, Value) color to HSL (Hue, Saturation, Lightness)
     * Credit goes to xpansive
     * https://gist.github.com/xpansive/1337890
     *
     * @param hsv HSV color array
     * @return hsl
     */
    private static float[] hsv2hsl(float[] hsv) {
        float hue = hsv[0];
        float sat = hsv[1];
        float val = hsv[2];

        //Saturation is very different between the two color spaces
        //If (2-sat)*val < 1 set it to sat*val/((2-sat)*val)
        //Otherwise sat*val/(2-(2-sat)*val)
        //Conditional is not operating with hue, it is reassigned!
        // sat*val/((hue=(2-sat)*val)<1?hue:2-hue)
        float nhue = (2f - sat) * val;
        float nsat = sat * val / (nhue < 1f ? nhue : 2f - nhue);
        if (nsat > 1f)
            nsat = 1f;

        return new float[]{
                //[hue, saturation, lightness]
                //Range should be between 0 - 1
                hue, //Hue stays the same

                // check nhue and nsat logic
                nsat,

                nhue / 2f //Lightness is (2-sat)*val/2
                //See reassignment of hue above
        };
    }

    /**
     * Reverses hsv2hsl
     * Credit goes to xpansive
     * https://gist.github.com/xpansive/1337890
     *
     * @param hsl HSL color array
     * @return hsv color array
     */
    private static float[] hsl2hsv(float[] hsl) {
        float hue = hsl[0];
        float sat = hsl[1];
        float light = hsl[2];

        sat *= light < .5 ? light : 1 - light;

        return new float[]{
                //[hue, saturation, value]
                //Range should be between 0 - 1

                hue, //Hue stays the same
                2f * sat / (light + sat), //Saturation
                light + sat //Value
        };
    }
}
