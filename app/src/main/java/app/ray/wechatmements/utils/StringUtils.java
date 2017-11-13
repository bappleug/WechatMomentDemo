package app.ray.wechatmements.utils;

/**
 * Created by Ray on 2016/3/19.
 */

public class StringUtils {

    public static boolean isEmpty(CharSequence input) {
        if(input != null && !"".equals(input) && !"null".equalsIgnoreCase(input.toString())) {
            for(int i = 0; i < input.length(); ++i) {
                char c = input.charAt(i);
                if(c != 32 && c != 9 && c != 13 && c != 10) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
