package cn.handyplus.pln.constants;

import cn.handyplus.lib.util.BaseUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * tab提醒
 *
 * @author handy
 */
@Getter
@AllArgsConstructor
public enum TabListEnum {
    /**
     * 第一层提醒
     */
    FIRST(Arrays.asList("reload", "signIn", "addShop", "setReturn", "shop", "task", "view", "coin"), 0, null, 1),

    ADD_SHOP_TWO(Collections.singletonList(BaseUtil.getLangMsg("tabHelp.price")), 1, "addShop", 2),
    ADD_SHOP_FOUR(Collections.singletonList(BaseUtil.getLangMsg("tabHelp.limit")), 1, "addShop", 3),

    SET_RETURN(null, 1, "setReturn", 2),

    VIEW_TWO(Arrays.asList("return", "shop"), 1, "view", 2),

    COIN_TWO(Arrays.asList("give", "set", "take"), 1, "coin", 2),
    COIN_THREE(null, 1, "coin", 3),
    COIN_FOUR(Collections.singletonList(BaseUtil.getLangMsg("tabHelp.coinAmount")), 1, "coin", 4),
    ;

    /**
     * 返回的List
     */
    private final List<String> list;
    /**
     * 识别的上个参数的位置
     */
    private final int befPos;
    /**
     * 上个参数的内容
     */
    private final String bef;
    /**
     * 这个参数可以出现的位置
     */
    private final int num;

    /**
     * 获取提醒
     *
     * @param args       参数
     * @param argsLength 参数长度
     * @return 提醒
     */
    public static List<String> returnList(String[] args, int argsLength) {
        List<String> completions = new ArrayList<>();
        for (TabListEnum tabListEnum : TabListEnum.values()) {
            // 过滤掉参数长度不满足要求的情况
            if (tabListEnum.getBefPos() - 1 >= args.length) {
                continue;
            }
            // 过滤掉前置参数不匹配的情况
            if (tabListEnum.getBef() != null && !tabListEnum.getBef().equalsIgnoreCase(args[tabListEnum.getBefPos() - 1])) {
                continue;
            }
            // 过滤掉参数长度不匹配的情况
            if (tabListEnum.getNum() != argsLength) {
                continue;
            }
            return tabListEnum.getList();
        }
        return completions;
    }

}
