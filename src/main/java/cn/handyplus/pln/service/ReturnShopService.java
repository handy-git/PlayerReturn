package cn.handyplus.pln.service;

import cn.handyplus.lib.db.Db;
import cn.handyplus.lib.db.Page;
import cn.handyplus.pln.enter.ReturnShop;

import java.util.List;
import java.util.Optional;

/**
 * 回归商城
 *
 * @author handy
 */
public class ReturnShopService {

    private ReturnShopService() {
    }

    private static class SingletonHolder {
        private static final ReturnShopService INSTANCE = new ReturnShopService();
    }

    public static ReturnShopService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 新增
     *
     * @param returnShop 入参
     * @return 成功
     */
    public int add(ReturnShop returnShop) {
        return Db.use(ReturnShop.class).execution().insert(returnShop);
    }

    /**
     * 根据id删除
     *
     * @param id ID
     */
    public void delById(Integer id) {
        Db.use(ReturnShop.class).execution().deleteById(id);
        // 删除关联信息
        ReturnShopLogService.getInstance().delByReturnShopId(id);
    }

    /**
     * 根据id查询
     *
     * @param id ID
     * @return 条数
     */
    public Optional<ReturnShop> findById(Integer id) {
        return Db.use(ReturnShop.class).execution().selectById(id);
    }

    /**
     * 分页查询
     *
     * @param pageNum  页数
     * @param pageSize 条数
     * @return GuildShop
     */
    public Page<ReturnShop> page(Integer pageNum, Integer pageSize) {
        Db<ReturnShop> db = Db.use(ReturnShop.class);
        db.where().limit(pageNum, pageSize).orderByDesc(ReturnShop::getId);
        return db.execution().page();
    }

    /**
     * 查询总数
     *
     * @return 条数
     */
    public Integer findCount() {
        return Db.use(ReturnShop.class).execution().count();
    }

    /**
     * 根据id 减少 数量
     *
     * @param id     ID
     * @param number 数量
     * @since 1.3.0
     */
    public void subtractNumberById(Integer id, int number) {
        Db<ReturnShop> db = Db.use(ReturnShop.class);
        db.update().subtract(ReturnShop::getNumber, ReturnShop::getNumber, number);
        db.execution().updateById(id);
    }

    /**
     * 查询全部
     *
     * @return list
     */
    public List<ReturnShop> findAll() {
        return Db.use(ReturnShop.class).execution().list();
    }

}