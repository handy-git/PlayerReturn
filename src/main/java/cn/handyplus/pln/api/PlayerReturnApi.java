package cn.handyplus.pln.api;

import cn.handyplus.lib.core.DateUtil;
import cn.handyplus.pln.enter.ReturnPlayerInfo;
import cn.handyplus.pln.enter.ReturnPlayerTime;
import cn.handyplus.pln.service.ReturnPlayerInfoService;
import cn.handyplus.pln.service.ReturnPlayerTimeService;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

/**
 * API
 *
 * @author handy
 */
public class PlayerReturnApi {

    /**
     * 回归天数
     *
     * @param playerName 玩家
     * @return 天数
     */
    public static int returnDay(String playerName) {
        Optional<ReturnPlayerTime> returnPlayerTimeOptional = ReturnPlayerTimeService.getInstance().findByPlayerName(playerName, true);
        return returnPlayerTimeOptional.map(returnPlayerTime -> returnDay(returnPlayerTime, ChronoUnit.DAYS)).orElse(0);
    }

    /**
     * 回归小时数
     *
     * @param playerName 玩家
     * @return 小时数
     */
    public static int returnHour(String playerName) {
        Optional<ReturnPlayerTime> returnPlayerTimeOptional = ReturnPlayerTimeService.getInstance().findByPlayerName(playerName, true);
        return returnPlayerTimeOptional.map(returnPlayerTime -> returnDay(returnPlayerTime, ChronoUnit.HOURS)).orElse(0);
    }

    /**
     * 回归天数
     *
     * @param returnPlayerTime 玩家回归
     * @param unit             类型
     * @return 时间
     */
    public static int returnDay(ReturnPlayerTime returnPlayerTime, ChronoUnit unit) {
        if (returnPlayerTime == null) {
            return 0;
        }
        long time = DateUtil.between(returnPlayerTime.getReturnTime(), new Date(), unit);
        return Math.toIntExact(time + 1);
    }

    /**
     * 是否回归玩家
     *
     * @param playerName 玩家
     * @return true是
     */
    public static boolean isReturnPlayer(String playerName) {
        return ReturnPlayerTimeService.getInstance().findByPlayerName(playerName, true).isPresent();
    }

    /**
     * 离开天数
     *
     * @param playerName 玩家
     * @return 天数
     */
    public static int leaveDay(String playerName) {
        Optional<ReturnPlayerInfo> returnPlayerInfoOptional = ReturnPlayerInfoService.getInstance().findByPlayerName(playerName);
        return returnPlayerInfoOptional.map(returnPlayerInfo -> leaveDay(returnPlayerInfo, ChronoUnit.DAYS)).orElse(0);
    }

    /**
     * 离开小时数
     *
     * @param playerName 玩家
     * @return 小时数
     */
    public static int leaveHour(String playerName) {
        Optional<ReturnPlayerInfo> returnPlayerInfoOptional = ReturnPlayerInfoService.getInstance().findByPlayerName(playerName);
        return returnPlayerInfoOptional.map(returnPlayerInfo -> leaveDay(returnPlayerInfo, ChronoUnit.HOURS)).orElse(0);
    }

    /**
     * 离开天数
     *
     * @param returnPlayerInfo 玩家信息
     * @param unit             类型
     * @return 时间
     */
    public static int leaveDay(ReturnPlayerInfo returnPlayerInfo, ChronoUnit unit) {
        if (returnPlayerInfo == null) {
            return 0;
        }
        long time = DateUtil.between(returnPlayerInfo.getLastQuitTime(), returnPlayerInfo.getLastJoinTime(), unit);
        return Math.toIntExact(time + 1);
    }

}
