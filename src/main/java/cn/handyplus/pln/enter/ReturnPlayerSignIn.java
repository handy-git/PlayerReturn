package cn.handyplus.pln.enter;

import cn.handyplus.lib.annotation.TableField;
import cn.handyplus.lib.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 回归玩家签到
 *
 * @author handy
 */
@Getter
@Setter
@TableName(value = "return_player_sign_in", comment = "回归玩家签到")
public class ReturnPlayerSignIn {

    @TableField(value = "id", comment = "ID")
    private Integer id;

    @TableField(value = "return_player_time_id", comment = "回归ID")
    private Integer returnPlayerTimeId;

    @TableField(value = "player_name", comment = "玩家名称", notNull = true)
    private String playerName;

    @TableField(value = "player_uuid", comment = "玩家uuid", notNull = true)
    private String playerUuid;

    @TableField(value = "sign_in_time", comment = "本次签到时间")
    private Date signInTime;

    @TableField(value = "day", comment = "签到天数")
    private Integer day;

}