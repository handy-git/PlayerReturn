package cn.handyplus.pln.enter;

import cn.handyplus.lib.annotation.TableField;
import cn.handyplus.lib.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 玩家基本信息
 *
 * @author handy
 */
@Getter
@Setter
@TableName(value = "return_player_info", comment = "玩家基本信息")
public class ReturnPlayerInfo {

    @TableField(value = "id", comment = "ID")
    private Integer id;

    @TableField(value = "player_name", comment = "玩家名称", notNull = true)
    private String playerName;

    @TableField(value = "player_uuid", comment = "玩家uuid", notNull = true)
    private String playerUuid;

    @TableField(value = "last_join_time", comment = "上次登录时间")
    private Date lastJoinTime;

    @TableField(value = "last_quit_time", comment = "最后离线时间")
    private Date lastQuitTime;
}
