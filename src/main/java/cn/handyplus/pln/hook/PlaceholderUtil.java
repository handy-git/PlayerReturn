package cn.handyplus.pln.hook;

import cn.handyplus.pln.PlayerReturn;
import cn.handyplus.pln.api.PlayerReturnApi;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

/**
 * 变量扩展
 *
 * @author handy
 */
public class PlaceholderUtil extends PlaceholderExpansion {
    private final PlayerReturn plugin;

    public PlaceholderUtil(PlayerReturn plugin) {
        this.plugin = plugin;
    }

    /**
     * 变量前缀
     *
     * @return 结果
     */
    @Override
    public String getIdentifier() {
        return "playerReturn";
    }

    /**
     * 注册变量
     *
     * @param player      玩家
     * @param placeholder 变量字符串
     * @return 变量
     */
    @Override
    public String onRequest(OfflinePlayer player, String placeholder) {
        if (player == null) {
            return null;
        }
        // %playerReturn_leave_hour%
        if ("leave_hour".equals(placeholder)) {
            return plugin.getConfig().getString(placeholder, String.valueOf(PlayerReturnApi.leaveHour(player.getName())));
        }
        // %playerReturn_leave_day%
        if ("leave_day".equals(placeholder)) {
            return plugin.getConfig().getString(placeholder, String.valueOf(PlayerReturnApi.leaveDay(player.getName())));
        }
        // %playerReturn_hour%
        if ("hour".equals(placeholder)) {
            return plugin.getConfig().getString(placeholder, String.valueOf(PlayerReturnApi.returnHour(player.getName())));
        }
        // %playerReturn_day%
        if ("day".equals(placeholder)) {
            return plugin.getConfig().getString(placeholder, String.valueOf(PlayerReturnApi.returnDay(player.getName())));
        }
        return null;
    }

    /**
     * 因为这是一个内部类，
     * 你必须重写这个方法，让PlaceholderAPI知道不要注销你的扩展类
     *
     * @return 结果
     */
    @Override
    public boolean persist() {
        return true;
    }

    /**
     * 因为这是一个内部类，所以不需要进行这种检查
     * 我们可以简单地返回{@code true}
     *
     * @return 结果
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * 作者
     *
     * @return 结果
     */
    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * 版本
     *
     * @return 结果
     */
    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }
}
