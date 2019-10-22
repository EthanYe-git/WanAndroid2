package com.ethan.wanandroid2.data.util;

import com.ethan.wanandroid2.data.image.ImageBean;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 * 存储应用首页界面Fragment中的JsonBean类，避免每次Fragment创建时都通过网络获取
 *
 * @quthor Ethan Ye
 */
public class FragmentJsonBeanUtil {
    public static final Integer FRAGMENT_HOME = 1001;
    public static final Integer FRAGMENT_SQUARE = 1002;

    public static final Integer IMAGE_HEADER_HOME = 2001;

    //存储应用首页界面Fragment中的JsonBean的容器
    private static SoftReference<Map<Integer, Object>> jsonDataReference =
            new SoftReference<Map<Integer, Object>>(new HashMap<Integer, Object>());
    private static SoftReference<Map<Integer, List<ImageBean>>> jsonHeaderImages =
            new SoftReference<Map<Integer, List<ImageBean>>>(new HashMap<Integer,List<ImageBean>>());

    private FragmentJsonBeanUtil() {
    }

    public static boolean put(int fragment, Object jsonData) {
        if (checkFragment(fragment) && jsonDataReference.get() != null) {
            jsonDataReference.get().put(fragment, jsonData);
            return true;
        }
        return false;
    }

    /**
     * 存储Fragment文章列表JsonBean类
     *
     * @param fragment fragment标识
     * @return JsonBean类或不存在该fragment时返回空
     */
    public static Object get(int fragment) {
        if (checkFragment(fragment) && jsonDataReference.get() != null
                && jsonDataReference.get().containsKey(fragment)) {
            //TODO 获取JSON
            return jsonDataReference.get().get(fragment);
        }
        return null;
    }

    public static List<ImageBean> getImage(int image) {
        if (checkImage(image) && jsonHeaderImages.get() != null
                && jsonHeaderImages.get().containsKey(image)) {
            //TODO 获取JSON
            return jsonHeaderImages.get().get(image);
        }
        return null;
    }

    public static boolean putImage(int image, List<ImageBean> jsonData) {
        if (checkImage(image) && jsonHeaderImages.get() != null) {
            jsonHeaderImages.get().put(image, jsonData);
            return true;
        }
        return false;
    }

    /**
     * 移除JsonBean容器中的指定Fragment的JsonBean
     *
     * @param fragment Fragment标识
     * @return 移除的JsonBean或不存在fragment标识时返回null
     */
    public static Object remove(int fragment) {
        if (checkFragment(fragment) && jsonDataReference.get()!=null
                && jsonDataReference.get().containsKey(fragment)) {
            return jsonDataReference.get().remove(fragment);
        }
        return null;
    }

    /**
     * 检查是否存在fragment标识
     *
     * @param fragment
     * @return
     */
    public static boolean checkFragment(int fragment) {
        if (fragment == FRAGMENT_HOME ||
                fragment == FRAGMENT_SQUARE) {
            return true;
        }
        return false;
    }

    public static boolean checkImage(int image){
        if (image == IMAGE_HEADER_HOME){
            return true;
        }
        return false;
    }
}
