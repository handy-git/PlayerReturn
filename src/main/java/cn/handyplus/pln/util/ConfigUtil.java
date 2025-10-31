package cn.handyplus.pln.util;

import cn.handyplus.lib.constants.BaseConstants;
import cn.handyplus.lib.util.HandyConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * 配置类
 *
 * @author handy
 */
public class ConfigUtil {
    public static FileConfiguration SIGN_IN_CONFIG, TASK_CONFIG, SHOP_CONFIG, RETURN_CONFIG;

    /**
     * 加载全部配置
     */
    public static void init() {
        // 加载config
        HandyConfigUtil.loadConfig();
        // 加载语言文件
        HandyConfigUtil.loadLangConfig(false);
        // 加载gui配置
        SIGN_IN_CONFIG = HandyConfigUtil.load("gui/signIn.yml");
        TASK_CONFIG = HandyConfigUtil.load("gui/task.yml");
        SHOP_CONFIG = HandyConfigUtil.load("gui/shop.yml");
        RETURN_CONFIG = HandyConfigUtil.load("gui/return.yml");
        upConfig();
    }

    /**
     * 升级节点处理
     *
     * @since 1.0.1
     */
    public static void upConfig() {
        // 1.0.1
        String language = "languages/" + BaseConstants.CONFIG.getString("language") + ".yml";
        HandyConfigUtil.setPathIsNotContains(BaseConstants.LANG_CONFIG, "amountFailureMsg", "&4数量只能为大于0的数字", null, language);
        HandyConfigUtil.setPathIsNotContains(BaseConstants.LANG_CONFIG, "tabHelp.coinAmount", "请输入金额,必须正整数", null, language);
        HandyConfigUtil.loadLangConfig(false);
    }

}