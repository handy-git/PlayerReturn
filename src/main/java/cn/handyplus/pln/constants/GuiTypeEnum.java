package cn.handyplus.pln.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * gui类型
 *
 * @author handy
 */
@Getter
@AllArgsConstructor
public enum GuiTypeEnum {
    /**
     * gui类型
     */
    SIGN_IN("signIn", "签到"),
    TASK("task", "任务"),
    SHOP("shop", "商店"),
    VIEW_SHOP("view_shop", "商店管理"),
    VIEW_RETURN("view_return", "回归管理"),
    ;

    private final String type;
    private final String desc;

}