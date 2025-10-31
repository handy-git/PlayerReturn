package cn.handyplus.pln.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 玩家回归事件
 *
 * @author handy
 */
public class PlayerReturnEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    @Getter
    private final Player player;
    @Getter
    private final Integer day;

    /**
     * 构造器
     *
     * @param player 玩家
     * @param day    回归天数
     */
    public PlayerReturnEvent(Player player, Integer day) {
        this.player = player;
        this.day = day;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}