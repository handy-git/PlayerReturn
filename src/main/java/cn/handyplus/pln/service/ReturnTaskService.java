package cn.handyplus.pln.service;

import cn.handyplus.lib.db.Db;
import cn.handyplus.lib.db.Page;
import cn.handyplus.pln.enter.ReturnTask;

import java.util.List;
import java.util.Optional;

/**
 * 回归任务
 *
 * @author handy
 */
public class ReturnTaskService {
    private ReturnTaskService() {
    }

    private static class SingletonHolder {
        private static final ReturnTaskService INSTANCE = new ReturnTaskService();
    }

    public static ReturnTaskService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 新增
     *
     * @param returnTask 回归任务
     * @return id
     */
    public int add(ReturnTask returnTask) {
        return Db.use(ReturnTask.class).execution().insert(returnTask);
    }

    /**
     * 获取全部任务
     * 目标完成，任务未完成的
     *
     * @return 本周全部任务
     */
    public List<ReturnTask> findSeasonTask() {
        Db<ReturnTask> use = Db.use(ReturnTask.class);
        use.where().eq(ReturnTask::getStatus, false)
                .eq(ReturnTask::getDemandSuccess, true);
        return use.execution().list();
    }

    /**
     * 根据id更新状态
     *
     * @param id     id
     * @param status 状态
     */
    public void updateStatusById(Integer id, boolean status) {
        Db<ReturnTask> use = Db.use(ReturnTask.class);
        use.update().set(ReturnTask::getStatus, status);
        use.execution().updateById(id);
    }

    /**
     * 更新目标阶段是否完成
     *
     * @param id            id
     * @param demandSuccess 目标
     */
    public void updateDemandSuccess(Integer id, boolean demandSuccess) {
        Db<ReturnTask> use = Db.use(ReturnTask.class);
        use.update().set(ReturnTask::getDemandSuccess, demandSuccess);
        use.execution().updateById(id);
    }

    /**
     * 分页查询
     *
     * @param returnPlayerTimeId 回归id
     * @param pageNum            页数
     * @param pageSize           条数
     * @return ReturnTask
     */
    public Page<ReturnTask> page(Integer returnPlayerTimeId, Integer pageNum, Integer pageSize) {
        Db<ReturnTask> db = Db.use(ReturnTask.class);
        db.where().eq(ReturnTask::getReturnPlayerTimeId, returnPlayerTimeId)
                .limit(pageNum, pageSize).orderByDesc(ReturnTask::getId);
        return db.execution().page();
    }

    /**
     * 查询总数
     *
     * @param returnPlayerTimeId 回归id
     * @return 条数
     */
    public Integer findCount(Integer returnPlayerTimeId) {
        Db<ReturnTask> db = Db.use(ReturnTask.class);
        db.where().eq(ReturnTask::getReturnPlayerTimeId, returnPlayerTimeId);
        return db.execution().count();
    }

    /**
     * 根据回归id删除
     *
     * @param returnPlayerTimeId 回归id
     */
    public void delByReturnPlayerTimeId(Integer returnPlayerTimeId) {
        Db<ReturnTask> use = Db.use(ReturnTask.class);
        use.where().eq(ReturnTask::getReturnPlayerTimeId, returnPlayerTimeId);
        use.execution().delete();
    }

    /**
     * 查询全部
     *
     * @return list
     */
    public List<ReturnTask> findAll() {
        return Db.use(ReturnTask.class).execution().list();
    }

    /**
     * 根据id查询
     *
     * @param id Id
     * @return ReturnTask
     */
    public Optional<ReturnTask> findById(Integer id) {
        return Db.use(ReturnTask.class).execution().selectById(id);
    }

}