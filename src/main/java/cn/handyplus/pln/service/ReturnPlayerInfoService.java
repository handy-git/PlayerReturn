package cn.handyplus.pln.service;

import cn.handyplus.lib.db.Db;
import cn.handyplus.pln.enter.ReturnPlayerInfo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 玩家信息
 *
 * @author handy
 */
public class ReturnPlayerInfoService {

    private ReturnPlayerInfoService() {
    }

    private static class SingletonHolder {
        private static final ReturnPlayerInfoService INSTANCE = new ReturnPlayerInfoService();
    }

    public static ReturnPlayerInfoService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 新增
     *
     * @param returnPlayer 入参
     */
    public void putIfAbsent(ReturnPlayerInfo returnPlayer) {
        Optional<ReturnPlayerInfo> returnPlayerInfoOptional = this.findByPlayerName(returnPlayer.getPlayerName());
        if (returnPlayerInfoOptional.isPresent()) {
            return;
        }
        Db.use(ReturnPlayerInfo.class).execution().insert(returnPlayer);
    }

    /**
     * 根据 playerName 查询
     *
     * @param playerName 玩家名
     * @return 成功
     */
    public Optional<ReturnPlayerInfo> findByPlayerName(String playerName) {
        Db<ReturnPlayerInfo> use = Db.use(ReturnPlayerInfo.class);
        use.where().eq(ReturnPlayerInfo::getPlayerName, playerName);
        return use.execution().selectOne();
    }

    /**
     * 根据 playerName 更改登录时间
     *
     * @param playerName   ID
     * @param lastJoinTime 加入时间
     */
    public void updateLastJoinTimeByPlayerName(String playerName, Date lastJoinTime) {
        Db<ReturnPlayerInfo> db = Db.use(ReturnPlayerInfo.class);
        db.update().set(ReturnPlayerInfo::getLastJoinTime, lastJoinTime);
        db.where().eq(ReturnPlayerInfo::getPlayerName, playerName);
        db.execution().update();
    }

    /**
     * 根据 playerName 更改离线时间
     *
     * @param playerName   ID
     * @param lastQuitTime 离线时间
     */
    public void updateLastQuitTimeByPlayerName(String playerName, Date lastQuitTime) {
        Db<ReturnPlayerInfo> db = Db.use(ReturnPlayerInfo.class);
        db.update().set(ReturnPlayerInfo::getLastQuitTime, lastQuitTime);
        db.where().eq(ReturnPlayerInfo::getPlayerName, playerName);
        db.execution().update();
    }

    /**
     * 查询全部
     *
     * @return list
     */
    public List<ReturnPlayerInfo> findAll() {
        return Db.use(ReturnPlayerInfo.class).execution().list();
    }

}