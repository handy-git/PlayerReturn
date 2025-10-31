package cn.handyplus.pln.inventory;

import cn.handyplus.lib.constants.BaseConstants;
import cn.handyplus.lib.inventory.HandyInventory;
import cn.handyplus.lib.inventory.HandyInventoryUtil;
import cn.handyplus.pln.api.PlayerReturnApi;
import cn.handyplus.pln.constants.GuiTypeEnum;
import cn.handyplus.pln.enter.ReturnPlayerSignIn;
import cn.handyplus.pln.enter.ReturnPlayerTime;
import cn.handyplus.pln.service.ReturnPlayerSignInService;
import cn.handyplus.pln.service.ReturnPlayerTimeService;
import cn.handyplus.pln.util.ConfigUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 签到
 *
 * @author handy
 */
public class SignInGui {
    private SignInGui() {
    }

    private final static SignInGui INSTANCE = new SignInGui();

    public static SignInGui getInstance() {
        return INSTANCE;
    }

    /**
     * 创建gui
     *
     * @param player 玩家
     * @return gui
     */
    public Inventory createGui(Player player) {
        String title = ConfigUtil.SIGN_IN_CONFIG.getString("title");
        int size = ConfigUtil.SIGN_IN_CONFIG.getInt("size", BaseConstants.GUI_SIZE_54);
        String sound = ConfigUtil.SIGN_IN_CONFIG.getString("sound");
        HandyInventory handyInventory = new HandyInventory(GuiTypeEnum.SIGN_IN.getType(), title, size, sound);
        // 设置数据
        handyInventory.setPageNum(1);
        handyInventory.setPlayer(player);
        this.setInventoryDate(handyInventory);
        return handyInventory.getInventory();
    }

    /**
     * 设置数据
     *
     * @param handyInventory gui
     */
    public void setInventoryDate(HandyInventory handyInventory) {
        // 基础设置
        handyInventory.setGuiType(GuiTypeEnum.SIGN_IN.getType());
        // 1. 刷新
        HandyInventoryUtil.refreshInventory(handyInventory.getInventory());
        // 2.设置功能性菜单
        this.setFunctionMenu(handyInventory);
    }

    /**
     * 设置功能性菜单
     *
     * @param handyInventory GUI
     */
    private void setFunctionMenu(HandyInventory handyInventory) {
        Player player = handyInventory.getPlayer();
        // 判断是否回归
        Optional<ReturnPlayerTime> returnPlayerTimeOptional = ReturnPlayerTimeService.getInstance().findByPlayerName(player.getName(), true);
        if (!returnPlayerTimeOptional.isPresent()) {
            return;
        }
        ReturnPlayerTime returnPlayerTime = returnPlayerTimeOptional.get();
        int returnDay = PlayerReturnApi.returnDay(returnPlayerTime, ChronoUnit.DAYS);
        Map<Integer, ReturnPlayerSignIn> signInMap = ReturnPlayerSignInService.getInstance().findMapByPlayerName(returnPlayerTime.getId(), player.getName());
        // 1
        HandyInventoryUtil.setButton(ConfigUtil.SIGN_IN_CONFIG, handyInventory, "one", this.replaceMap(signInMap.get(1), returnDay == 1));
        // 2
        HandyInventoryUtil.setButton(ConfigUtil.SIGN_IN_CONFIG, handyInventory, "two", this.replaceMap(signInMap.get(2), returnDay == 2));
        // 3
        HandyInventoryUtil.setButton(ConfigUtil.SIGN_IN_CONFIG, handyInventory, "three", this.replaceMap(signInMap.get(3), returnDay == 3));
        // 4
        HandyInventoryUtil.setButton(ConfigUtil.SIGN_IN_CONFIG, handyInventory, "four", this.replaceMap(signInMap.get(4), returnDay == 4));
        // 5
        HandyInventoryUtil.setButton(ConfigUtil.SIGN_IN_CONFIG, handyInventory, "five", this.replaceMap(signInMap.get(5), returnDay == 5));
        // 6
        HandyInventoryUtil.setButton(ConfigUtil.SIGN_IN_CONFIG, handyInventory, "six", this.replaceMap(signInMap.get(6), returnDay == 6));
        // 7
        HandyInventoryUtil.setButton(ConfigUtil.SIGN_IN_CONFIG, handyInventory, "seven", this.replaceMap(signInMap.get(7), returnDay == 7));
        // 返回按钮
        HandyInventoryUtil.setButton(ConfigUtil.SIGN_IN_CONFIG, handyInventory, "back");
        // 回归商店
        HandyInventoryUtil.setButton(ConfigUtil.SIGN_IN_CONFIG, handyInventory, "shop");
        // 回归任务
        HandyInventoryUtil.setButton(ConfigUtil.SIGN_IN_CONFIG, handyInventory, "task");
        // 分隔板
        HandyInventoryUtil.setButton(ConfigUtil.SIGN_IN_CONFIG, handyInventory, "pane");
        // 自定义按钮
        HandyInventoryUtil.setCustomButton(ConfigUtil.SIGN_IN_CONFIG, handyInventory, "custom");
    }

    /**
     * 替换map
     *
     * @param signIn 签到记录
     * @return map
     */
    private Map<String, String> replaceMap(ReturnPlayerSignIn signIn, boolean signRst) {
        HashMap<String, String> replaceMap = new HashMap<>();
        String button = ConfigUtil.SIGN_IN_CONFIG.getString("noButton");
        String yesButton = ConfigUtil.SIGN_IN_CONFIG.getString("yesButton");
        String signedIn = ConfigUtil.SIGN_IN_CONFIG.getString("signedInButton");
        // 可以签到
        if (signRst) {
            button = yesButton;
        }
        // 判断是否签到过
        if (signIn != null) {
            button = signedIn;
        }
        replaceMap.put("button", button);
        return replaceMap;
    }

}