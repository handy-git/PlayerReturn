package cn.handyplus.pln.hook;

import cn.handyplus.lib.core.CollUtil;
import cn.handyplus.pln.PlayerReturn;
import cn.handyplus.pln.enter.ReturnPlayerTime;
import cn.handyplus.pln.enter.ReturnTask;
import cn.handyplus.pln.enter.ReturnTaskDemand;
import cn.handyplus.pln.service.ReturnTaskDemandService;
import cn.handyplus.pln.service.ReturnTaskService;
import cn.handyplus.pln.util.ConfigUtil;
import com.handy.playertask.api.PlayerTaskApi;
import com.handy.playertask.entity.TaskDemand;
import com.handy.playertask.entity.TaskList;

import java.util.List;

/**
 * 任务util
 *
 * @author handy
 */
public class PlayerTaskUtil {
    private PlayerTaskUtil() {
    }

    private static class SingletonHolder {
        private static final PlayerTaskUtil INSTANCE = new PlayerTaskUtil();
    }

    public static PlayerTaskUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 创建回归任务
     *
     * @param returnPlayerTime 玩家信息
     */
    public void createTask(ReturnPlayerTime returnPlayerTime) {
        if (!PlayerReturn.USE_TASK || !ConfigUtil.TASK_CONFIG.getBoolean("enable", true)) {
            return;
        }
        List<Integer> taskIdList = ConfigUtil.TASK_CONFIG.getIntegerList("taskId");
        if (CollUtil.isEmpty(taskIdList)) {
            return;
        }
        for (Integer taskId : taskIdList) {
            if (taskId == 0) {
                continue;
            }
            TaskList taskList = PlayerTaskApi.getInstance().findDetailByTaskId(taskId);
            if (taskList == null || CollUtil.isEmpty(taskList.getTaskDemands())) {
                continue;
            }
            // 任务
            ReturnTask returnTask = new ReturnTask();
            returnTask.setReturnPlayerTimeId(returnPlayerTime.getId());
            returnTask.setTaskId(taskId);
            returnTask.setTaskName(taskList.getTaskName());
            returnTask.setStatus(false);
            returnTask.setDemandSuccess(false);
            int returnTaskId = ReturnTaskService.getInstance().add(returnTask);
            // 任务目标
            for (TaskDemand taskDemand : taskList.getTaskDemands()) {
                ReturnTaskDemand returnTaskDemand = new ReturnTaskDemand();
                returnTaskDemand.setReturnPlayerTimeId(returnPlayerTime.getId());
                returnTaskDemand.setTaskId(taskId);
                returnTaskDemand.setReturnTaskId(returnTaskId);
                returnTaskDemand.setType(taskDemand.getType());
                returnTaskDemand.setCompletionAmount(0);
                returnTaskDemand.setAmount(taskDemand.getAmount());
                returnTaskDemand.setItemStack(taskDemand.getItemStack());
                ReturnTaskDemandService.getInstance().add(returnTaskDemand);
            }
        }
    }

}