package cn.handyplus.pln.service;

import cn.handyplus.lib.db.Db;
import cn.handyplus.pln.enter.ReturnShopLog;

import java.util.List;
import java.util.Optional;

/**
 * 回归商店玩家购买记录
 *
 * @author handy
 */
public class ReturnShopLogService {

    private ReturnShopLogService() {
    }

    private static class SingletonHolder {
        private static final ReturnShopLogService INSTANCE = new ReturnShopLogService();
    }

    public static ReturnShopLogService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 新增
     *
     * @param returnShopLog 入参
     */
    public void add(ReturnShopLog returnShopLog) {
        Db.use(ReturnShopLog.class).execution().insert(returnShopLog);
    }

    /**
     * 增加购买次数
     *
     * @param id     id
     * @param number 增加数
     */
    public void addNumber(Integer id, Integer number) {
        Db<ReturnShopLog> use = Db.use(ReturnShopLog.class);
        use.update().add(ReturnShopLog::getNumber, ReturnShopLog::getNumber, number);
        use.execution().updateById(id);
    }

    /**
     * 根据 玩家名和回归商城物品id 查询
     *
     * @param returnPlayerTimeId 回归id
     * @param playerName         玩家名
     * @param returnShopId       回归商城物品id
     * @return GuildPlayer
     */
    public Optional<ReturnShopLog> findByPlayerName(Integer returnPlayerTimeId, String playerName, Integer returnShopId) {
        Db<ReturnShopLog> use = Db.use(ReturnShopLog.class);
        use.where().eq(ReturnShopLog::getReturnPlayerTimeId, returnPlayerTimeId)
                .eq(ReturnShopLog::getReturnShopId, returnShopId)
                .eq(ReturnShopLog::getPlayerName, playerName);
        return use.execution().selectOne();
    }

    /**
     * 根据回归商城物品id删除
     *
     * @param returnShopId 回归商城物品id
     */
    public void delByReturnShopId(Integer returnShopId) {
        Db<ReturnShopLog> use = Db.use(ReturnShopLog.class);
        use.where().eq(ReturnShopLog::getReturnShopId, returnShopId);
        use.execution().delete();
    }

    /**
     * 查询全部
     *
     * @return list
     */
    public List<ReturnShopLog> findAll() {
        return Db.use(ReturnShopLog.class).execution().list();
    }

}