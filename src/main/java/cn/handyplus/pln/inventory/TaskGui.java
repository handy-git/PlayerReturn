package cn.handyplus.pln.inventory;

import cn.handyplus.lib.constants.BaseConstants;
import cn.handyplus.lib.db.Page;
import cn.handyplus.lib.inventory.HandyInventory;
import cn.handyplus.lib.inventory.HandyInventoryUtil;
import cn.handyplus.lib.util.ItemStackUtil;
import cn.handyplus.pln.constants.GuiTypeEnum;
import cn.handyplus.pln.constants.ReturnConstants;
import cn.handyplus.pln.enter.ReturnPlayerTime;
import cn.handyplus.pln.enter.ReturnTask;
import cn.handyplus.pln.enter.ReturnTaskDemand;
import cn.handyplus.pln.service.ReturnPlayerTimeService;
import cn.handyplus.pln.service.ReturnTaskDemandService;
import cn.handyplus.pln.service.ReturnTaskService;
import cn.handyplus.pln.util.ConfigUtil;
import com.handy.playertask.api.PlayerTaskApi;
import com.handy.playertask.entity.TaskPlayerDemand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 回归任务gui
 *
 * @author handy
 */
public class TaskGui {

    private TaskGui() {
    }

    private final static TaskGui INSTANCE = new TaskGui();

    public static TaskGui getInstance() {
        return INSTANCE;
    }

    /**
     * 创建gui
     *
     * @param player 玩家
     * @return gui
     */
    public Inventory createGui(Player player) {
        String title = ConfigUtil.TASK_CONFIG.getString("title");
        int size = ConfigUtil.TASK_CONFIG.getInt("size", BaseConstants.GUI_SIZE_54);
        String sound = ConfigUtil.TASK_CONFIG.getString("sound");
        HandyInventory handyInventory = new HandyInventory(GuiTypeEnum.TASK.getType(), title, size, sound);
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
        handyInventory.setGuiType(GuiTypeEnum.TASK.getType());
        // 1.刷新
        HandyInventoryUtil.refreshInventory(handyInventory.getInventory());
        // 1.设置数据
        this.setDate(handyInventory);
        // 2.设置功能性菜单
        this.setFunctionMenu(handyInventory);
    }

    /**
     * 设置数据
     *
     * @param handyInventory gui
     */
    private void setDate(HandyInventory handyInventory) {
        Inventory inventory = handyInventory.getInventory();
        Map<Integer, Integer> map = handyInventory.getIntMap();
        Player player = handyInventory.getPlayer();
        // 判断是否回归
        Optional<ReturnPlayerTime> returnPlayerTimeOptional = ReturnPlayerTimeService.getInstance().findByPlayerName(player.getName(), true);
        if (!returnPlayerTimeOptional.isPresent()) {
            return;
        }
        ReturnPlayerTime returnPlayerTime = returnPlayerTimeOptional.get();
        Page<ReturnTask> page = ReturnTaskService.getInstance().page(returnPlayerTime.getId(), handyInventory.getPageNum(), 10);
        if (page.getTotal() < 1) {
            return;
        }
        List<ReturnTask> records = page.getRecords();
        List<Integer> returnTaskIdList = records.stream().map(ReturnTask::getId).collect(Collectors.toList());
        List<ReturnTaskDemand> returnTaskDemandList = ReturnTaskDemandService.getInstance().findByReturnTaskIds(returnTaskIdList);
        Map<Integer, List<ReturnTaskDemand>> returnTaskDemandMap = returnTaskDemandList.stream().collect(Collectors.groupingBy(ReturnTaskDemand::getReturnTaskId));
        for (ReturnTask record : records) {
            if (returnTaskDemandMap.get(record.getId()) != null) {
                record.setReturnTaskDemandList(returnTaskDemandMap.get(record.getId()));
            }
        }
        int i = 0;
        List<Integer> guiIndexList = ReturnConstants.GUI_INDEX;
        for (ReturnTask record : records) {
            Integer index = guiIndexList.get(i++);
            // 获取任务目标变量
            List<TaskPlayerDemand> taskDemands = this.getTaskPlayerDemands(record);
            // 基础参数
            String material = ConfigUtil.TASK_CONFIG.getString("returnTask." + record.getTaskId() + ".material");
            String name = ConfigUtil.TASK_CONFIG.getString("returnTask." + record.getTaskId() + ".name", "${taskName}").replace("${taskName}", record.getTaskName());
            int customModelData = ConfigUtil.TASK_CONFIG.getInt("returnTask." + record.getTaskId() + ".custom-model-data");
            List<String> loreList = ConfigUtil.TASK_CONFIG.getStringList("returnTask." + record.getTaskId() + ".lore");
            // 批量进度lore替换
            Map<String, List<String>> replaceMap = new HashMap<>();
            replaceMap.put("taskSchedule", PlayerTaskApi.taskScheduleReplaceMap(taskDemands));
            loreList = ItemStackUtil.loreBatchReplaceMap(loreList, replaceMap, "");
            // button替换
            loreList = ItemStackUtil.loreReplaceMap(loreList, this.getLoreReplaceMap(record));
            ItemStack itemStack = ItemStackUtil.getItemStack(material, name, loreList, false, customModelData);
            inventory.setItem(index, itemStack);
            map.put(index, record.getId());
        }
    }

    /**
     * 获取任务目标变量
     *
     * @param record 记录
     * @return 变量
     */
    private List<TaskPlayerDemand> getTaskPlayerDemands(ReturnTask record) {
        List<TaskPlayerDemand> taskDemands = new ArrayList<>();
        for (ReturnTaskDemand returnTaskDemand : record.getReturnTaskDemandList()) {
            TaskPlayerDemand taskPlayerDemand = new TaskPlayerDemand();
            taskPlayerDemand.setType(returnTaskDemand.getType());
            taskPlayerDemand.setCompletionAmount(returnTaskDemand.getCompletionAmount());
            taskPlayerDemand.setAmount(returnTaskDemand.getAmount());
            taskPlayerDemand.setItemStack(returnTaskDemand.getItemStack());
            taskDemands.add(taskPlayerDemand);
        }
        return taskDemands;
    }

    /**
     * 设置功能性菜单
     *
     * @param handyInventory GUI
     */
    private void setFunctionMenu(HandyInventory handyInventory) {
        // 任务介绍
        HandyInventoryUtil.setButton(ConfigUtil.TASK_CONFIG, handyInventory, "info");
        // 设置翻页按钮
        Map<String, String> replacePageMap = this.replacePageMap(handyInventory);
        HandyInventoryUtil.setButton(ConfigUtil.TASK_CONFIG, handyInventory, "nextPage", replacePageMap);
        HandyInventoryUtil.setButton(ConfigUtil.TASK_CONFIG, handyInventory, "previousPage", replacePageMap);
        // 返回按钮
        HandyInventoryUtil.setButton(ConfigUtil.TASK_CONFIG, handyInventory, "back");
        // 分隔板
        HandyInventoryUtil.setButton(ConfigUtil.TASK_CONFIG, handyInventory, "pane");
        // 回归商店
        HandyInventoryUtil.setButton(ConfigUtil.TASK_CONFIG, handyInventory, "shop");
        // 回归签到
        HandyInventoryUtil.setButton(ConfigUtil.TASK_CONFIG, handyInventory, "signIn");
        // 自定义按钮
        HandyInventoryUtil.setCustomButton(ConfigUtil.TASK_CONFIG, handyInventory, "custom");
    }

    /**
     * 分页map
     *
     * @param handyInventory 当前页
     * @return 分页map
     */
    private Map<String, String> replacePageMap(HandyInventory handyInventory) {
        Integer pageNum = handyInventory.getPageNum();
        Map<String, String> map = new HashMap<>();
        int count = (int) Math.ceil(ReturnTaskService.getInstance().findCount(handyInventory.getId()) / 10.0);
        handyInventory.setPageCount(count);
        map.put("count", count == 0 ? "1" : count + "");
        map.put("pageNum", pageNum + "");
        map.put("nextPage", (pageNum + 1) + "");
        map.put("previousPage", (pageNum - 1) < 1 ? "1" : (pageNum - 1) + "");
        return map;
    }

    /**
     * 获取替换lore
     *
     * @param returnTask 任务
     * @return loreList
     */
    private Map<String, String> getLoreReplaceMap(ReturnTask returnTask) {
        Map<String, String> map = new HashMap<>();
        // 未完成
        String button = ConfigUtil.TASK_CONFIG.getString("taskNoFinish");
        String taskFinish = ConfigUtil.TASK_CONFIG.getString("taskFinish");
        String taskHaveFinish = ConfigUtil.TASK_CONFIG.getString("taskHaveFinish");
        // 已完成
        if (returnTask.getStatus()) {
            button = taskHaveFinish;
        } else {
            // 未完成- 进度全了
            if (returnTask.getDemandSuccess()) {
                button = taskFinish;
            }
        }
        map.put("button", button);
        return map;
    }

}