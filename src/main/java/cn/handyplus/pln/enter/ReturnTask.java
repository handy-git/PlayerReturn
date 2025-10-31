package cn.handyplus.pln.enter;

import cn.handyplus.lib.annotation.TableField;
import cn.handyplus.lib.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 回归任务
 *
 * @author handy
 */
@Data
@TableName(value = "return_task", comment = "回归任务")
public class ReturnTask {

    @TableField(value = "id", comment = "ID")
    private Integer id;

    @TableField(value = "return_player_time_id", comment = "回归ID")
    private Integer returnPlayerTimeId;

    @TableField(value = "task_id", comment = "任务id", notNull = true)
    private Integer taskId;

    @TableField(value = "task_name", comment = "任务名称", notNull = true)
    private String taskName;

    @TableField(value = "status", comment = "任务是否完成 false 未完成，true 已完成", notNull = true)
    private Boolean status;

    @TableField(value = "demand_success", comment = "目标是否完成 false 未完成，true 已完成", notNull = true)
    private Boolean demandSuccess;

    /**
     * 任务目标
     */
    private List<ReturnTaskDemand> returnTaskDemandList;

}