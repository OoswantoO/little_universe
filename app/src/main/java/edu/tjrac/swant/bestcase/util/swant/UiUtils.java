package edu.tjrac.swant.bestcase.util.swant;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

import edu.tjrac.swant.bestcase.R;

/**
 * Created by wpc on 2016/9/10.
 */
public class UiUtils {

    //切换输入法显示状态
    public static void hiddenSoftInput(Context context) {
        //得到InputMethodManager的实例
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果开启
        if (imm.isActive()) {
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    private int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);

       return wm.getDefaultDisplay().getWidth();
    }
    public static int getScreenHeight(Context context){

        WindowManager wm = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    public static int getTabsHeight(Context context) {
        return (int) context.getResources().getDimension(R.dimen.tabsHeight);
    }

    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static void showPopmenu(Context context, View v,
                                   boolean withIcon,
                                   int menuId, PopupMenu.OnMenuItemClickListener onMenuItemClickListener) {
        PopupMenu popup = new PopupMenu(context, v);

        if (withIcon) {
            try {
                Field field = popup.getClass().getDeclaredField("mPopup");
                field.setAccessible(true);
                MenuPopupHelper mHelper = (MenuPopupHelper) field.get(popup);
                mHelper.setForceShowIcon(true);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(menuId, popup.getMenu());
//                if (vp.getCurrentItem() == 0) {
//                    popup.getMenu().findItem(R.id.light_mod).setVisible(true);
//                } else {
//                    popup.getMenu().findItem(R.id.light_mod).setVisible(false);
//                }
        popup.setOnMenuItemClickListener(onMenuItemClickListener);
        popup.show();
    }
}
