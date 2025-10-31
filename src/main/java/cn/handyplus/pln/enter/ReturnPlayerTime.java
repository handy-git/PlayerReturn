package cn.handyplus.pln.enter;

import cn.handyplus.lib.annotation.TableField;
import cn.handyplus.lib.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 回归玩家
 *
 * @author handy
 */
@Getter
@Setter
@TableName(value = "return_player_time", comment = "回归玩家")
public class ReturnPlayerTime {

    @TableField(value = "id", comment = "ID")
    private Integer id;

    @TableField(value = "player_name", comment = "玩家名称", notNull = true)
    private String playerName;

    @TableField(value = "player_uuid", comment = "玩家uuid")
    private String playerUuid;

    @TableField(value = "return_time", comment = "本次回归时间")
    private Date returnTime;

    @TableField(value = "day", comment = "回归天数")
    private Integer day;

    @TableField(value = "invitee_player_name", comment = "邀请玩家名称")
    private String inviteePlayerName;

    @TableField(value = "invitee_player_uuid", comment = "邀请玩家uuid")
    private String inviteePlayerUuid;

    @TableField(value = "number", comment = "积分")
    private Integer number;

    @TableField(value = "status", comment = "状态 true开启，false过期")
    private Boolean status;

}