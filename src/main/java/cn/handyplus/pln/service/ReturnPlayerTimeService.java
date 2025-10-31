package cn.handyplus.pln.service;

import cn.handyplus.lib.db.Db;
import cn.handyplus.lib.db.Page;
import cn.handyplus.pln.enter.ReturnPlayerTime;

import java.util.List;
import java.util.Optional;

/**
 * 回归玩家
 *
 * @author handy
 */
public class ReturnPlayerTimeService {

    private ReturnPlayerTimeService() {
    }

    private static class SingletonHolder {
        private static final ReturnPlayerTimeService INSTANCE = new ReturnPlayerTimeService();
    }

    public static ReturnPlayerTimeService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 新增
     *
     * @param returnPlayerTime 入参
     * @return id
     */
    public int add(ReturnPlayerTime returnPlayerTime) {
        return Db.use(ReturnPlayerTime.class).execution().insert(returnPlayerTime);
    }

    /**
     * 根据 playerName 查询
     *
     * @param playerName 玩家名
     * @param status     状态
     * @return 成功
     */
    public Optional<ReturnPlayerTime> findByPlayerName(String playerName, Boolean status) {
        Db<ReturnPlayerTime> use = Db.use(ReturnPlayerTime.class);
        use.where().eq(ReturnPlayerTime::getPlayerName, playerName)
                .eq(ReturnPlayerTime::getStatus, status);
        return use.execution().selectOne();
    }

    /**
     * 增加积分
     *
     * @param id     主键
     * @param number 完成的数量
     */
    public void addNumber(Integer id, Integer number) {
        Db<ReturnPlayerTime> use = Db.use(ReturnPlayerTime.class);
        use.update().add(ReturnPlayerTime::getNumber, ReturnPlayerTime::getNumber, number);
        use.execution().updateById(id);
    }

    /**
     * 减少积分
     *
     * @param id     主键
     * @param number 完成的数量
     */
    public void subtractNumber(Integer id, Integer number) {
        Db<ReturnPlayerTime> use = Db.use(ReturnPlayerTime.class);
        use.update().subtract(ReturnPlayerTime::getNumber, ReturnPlayerTime::getNumber, number);
        use.execution().updateById(id);
    }

    /**
     * 更新状态
     *
     * @param playerName 玩家名
     * @param status     状态
     */
    public void updateStatus(String playerName, Boolean status) {
        Db<ReturnPlayerTime> use = Db.use(ReturnPlayerTime.class);
        use.update().set(ReturnPlayerTime::getStatus, status);
        use.where().eq(ReturnPlayerTime::getPlayerName, playerName);
        use.execution().update();
    }

    /**
     * 查询全部
     *
     * @return list
     */
    public List<ReturnPlayerTime> findAll() {
        return Db.use(ReturnPlayerTime.class).execution().list();
    }

    /**
     * 分页查询
     *
     * @param pageNum  页数
     * @param pageSize 条数
     * @return ReturnPlayerTime
     */
    public Page<ReturnPlayerTime> page(Integer pageNum, Integer pageSize) {
        Db<ReturnPlayerTime> db = Db.use(ReturnPlayerTime.class);
        db.where().limit(pageNum, pageSize).orderByDesc(ReturnPlayerTime::getId)
                .eq(ReturnPlayerTime::getStatus, true);
        return db.execution().page();
    }

    /**
     * 查询总数
     *
     * @return 条数
     */
    public Integer findCount() {
        Db<ReturnPlayerTime> db = Db.use(ReturnPlayerTime.class);
        db.where().eq(ReturnPlayerTime::getStatus, true);
        return db.execution().count();
    }

    /**
     * 增加
     *
     * @param playerName 玩家名
     * @param amount     数量
     * @return true 成功
     * @since 1.0.1
     */
    public Boolean give(String playerName, Integer amount) {
        Db<ReturnPlayerTime> use = Db.use(ReturnPlayerTime.class);
        use.update().add(ReturnPlayerTime::getNumber, ReturnPlayerTime::getNumber, amount);
        use.where().eq(ReturnPlayerTime::getPlayerName, playerName).eq(ReturnPlayerTime::getStatus, true);
        return use.execution().update() > 0;
    }

    /**
     * 设置
     *
     * @param playerName 玩家名
     * @param amount     数量
     * @return true 成功
     * @since 1.0.1
     */
    public Boolean set(String playerName, Integer amount) {
        Db<ReturnPlayerTime> use = Db.use(ReturnPlayerTime.class);
        use.update().set(ReturnPlayerTime::getNumber, amount);
        use.where().eq(ReturnPlayerTime::getPlayerName, playerName).eq(ReturnPlayerTime::getStatus, true);
        return use.execution().update() > 0;
    }

    /**
     * 减少
     *
     * @param playerName 玩家名
     * @param amount     数量
     * @return true 成功
     * @since 1.0.1
     */
    public Boolean take(String playerName, Integer amount) {
        Db<ReturnPlayerTime> use = Db.use(ReturnPlayerTime.class);
        use.update().subtract(ReturnPlayerTime::getNumber, ReturnPlayerTime::getNumber, amount);
        use.where().eq(ReturnPlayerTime::getPlayerName, playerName).eq(ReturnPlayerTime::getStatus, true);
        return use.execution().update() > 0;
    }

}