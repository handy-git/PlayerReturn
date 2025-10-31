package cn.handyplus.pln.enter;

import cn.handyplus.lib.annotation.TableField;
import cn.handyplus.lib.annotation.TableName;
import lombok.Data;

/**
 * 回归商店玩家购买记录
 *
 * @author handy
 */
@Data
@TableName(value = "return_shop_log", comment = "回归商店玩家购买记录")
public class ReturnShopLog {

    @TableField(value = "id", comment = "ID")
    private Integer id;

    @TableField(value = "return_player_time_id", comment = "回归ID")
    private Integer returnPlayerTimeId;

    @TableField(value = "player_name", comment = "玩家名称", notNull = true)
    private String playerName;

    @TableField(value = "player_uuid", comment = "玩家uuid", notNull = true)
    private String playerUuid;

    @TableField(value = "return_shop_id", comment = "回归商店物品id", notNull = true)
    private Integer returnShopId;

    @TableField(value = "number", comment = "购买次数", notNull = true, fieldDefault = "0")
    private Integer number;

}
