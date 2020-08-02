package blog.util;

/**
 * 模糊查询：判断字符串是否有值的工具类
 */
public class StringUtil {
    //给字符串加百分号的方法，方便数据库模糊查询
    public static String formatLike(String str) {

        if (isNotEmpty(str)){
            str =  "%"+str+"%" ;

        }
        return str;
    }


    //判断字符串是否为空的方法
    public static boolean isNotEmpty(String str){

        if (str != null && "".equals(str.trim())){
            return true;
        }

        return false;
    }



}
