package cn.handyplus.pln.enter;

import cn.handyplus.lib.annotation.TableField;
import cn.handyplus.lib.annotation.TableName;
import lombok.Data;

/**
 * 回归任务目标进度
 *
 * @author handy
 */
@Data
@TableName(value = "return_task_demand", comment = "回归任务目标进度")
public class ReturnTaskDemand {

    @TableField(value = "id", comment = "ID")
    private Integer id;

    @TableField(value = "return_player_time_id", comment = "回归ID")
    private Integer returnPlayerTimeId;

    @TableField(value = "task_id", comment = "任务id", notNull = true)
    private Integer taskId;

    @TableField(value = "return_task_id", comment = "回归任务id", notNull = true)
    private Integer returnTaskId;

    @TableField(value = "type", comment = "类型", length = 32, notNull = true)
    private String type;

    @TableField(value = "completion_amount", comment = "完成过的数量", notNull = true)
    private Integer completionAmount;

    @TableField(value = "amount", comment = "数量", notNull = true)
    private Integer amount;

    @TableField(value = "item_stack", comment = "自定义物品", length = 10000, notNull = true)
    private String itemStack;

}