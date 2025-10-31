package cn.handyplus.pln.service;

import cn.handyplus.lib.core.CollUtil;
import cn.handyplus.lib.db.Db;
import cn.handyplus.pln.enter.ReturnPlayerSignIn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 回归玩家签到
 *
 * @author handy
 */
public class ReturnPlayerSignInService {

    private ReturnPlayerSignInService() {
    }

    private static class SingletonHolder {
        private static final ReturnPlayerSignInService INSTANCE = new ReturnPlayerSignInService();
    }

    public static ReturnPlayerSignInService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 新增
     *
     * @param returnPlayerSignIn 入参
     * @return id
     */
    public long add(ReturnPlayerSignIn returnPlayerSignIn) {
        return Db.use(ReturnPlayerSignIn.class).execution().insert(returnPlayerSignIn);
    }

    /**
     * 根据 playerName 查询
     *
     * @param returnPlayerTimeId 回归id
     * @param playerName         玩家名
     * @return ReturnPlayerSignIn
     */
    public List<ReturnPlayerSignIn> findByPlayerName(Integer returnPlayerTimeId, String playerName) {
        Db<ReturnPlayerSignIn> use = Db.use(ReturnPlayerSignIn.class);
        use.where().eq(ReturnPlayerSignIn::getPlayerName, playerName)
                .eq(ReturnPlayerSignIn::getReturnPlayerTimeId, returnPlayerTimeId);
        return use.execution().list();
    }

    /**
     * 查询map
     *
     * @param returnPlayerTimeId 回归id
     * @param playerName         玩家
     * @return map key day
     */
    public Map<Integer, ReturnPlayerSignIn> findMapByPlayerName(Integer returnPlayerTimeId, String playerName) {
        List<ReturnPlayerSignIn> signInList = this.findByPlayerName(returnPlayerTimeId, playerName);
        if (CollUtil.isEmpty(signInList)) {
            return new HashMap<>();
        }
        return signInList.stream().collect(Collectors.toMap(ReturnPlayerSignIn::getDay, x -> x));
    }

    /**
     * 根据 playerName 查询
     *
     * @param returnPlayerTimeId 回归id
     * @param playerName         玩家名
     * @param day                天
     * @return ReturnPlayerSignIn
     */
    public Optional<ReturnPlayerSignIn> findByPlayerName(Integer returnPlayerTimeId, String playerName, Integer day) {
        Db<ReturnPlayerSignIn> use = Db.use(ReturnPlayerSignIn.class);
        use.where().eq(ReturnPlayerSignIn::getPlayerName, playerName)
                .eq(ReturnPlayerSignIn::getReturnPlayerTimeId, returnPlayerTimeId)
                .eq(ReturnPlayerSignIn::getDay, day);
        return use.execution().selectOne();
    }

    /**
     * 查询全部
     *
     * @return list
     */
    public List<ReturnPlayerSignIn> findAll() {
        return Db.use(ReturnPlayerSignIn.class).execution().list();
    }


}