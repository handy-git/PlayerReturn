package cn.handyplus.pln.service;

import cn.handyplus.lib.db.Db;
import cn.handyplus.pln.enter.ReturnTaskDemand;

import java.util.List;

/**
 * 回归任务进度
 *
 * @author handy
 */
public class ReturnTaskDemandService {
    private ReturnTaskDemandService() {
    }

    public static ReturnTaskDemandService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ReturnTaskDemandService INSTANCE = new ReturnTaskDemandService();
    }

    /**
     * 新增
     *
     * @param returnTaskDemand 回归任务
     */
    public void add(ReturnTaskDemand returnTaskDemand) {
        Db.use(ReturnTaskDemand.class).execution().insert(returnTaskDemand);
    }

    /**
     * 根据回归id和任务要求查询未完成的任务目标
     *
     * @param returnPlayerTimeId 回归id
     * @param content            内容
     * @param demandType         类型
     * @return 回归任务要求列表
     */
    public List<ReturnTaskDemand> findByReturnPlayerTimeIdAndContent(Integer returnPlayerTimeId, String content, String demandType) {
        Db<ReturnTaskDemand> use = Db.use(ReturnTaskDemand.class);
        use.where().eq(ReturnTaskDemand::getReturnPlayerTimeId, returnPlayerTimeId)
                .eq(ReturnTaskDemand::getItemStack, content)
                .eq(ReturnTaskDemand::getType, demandType)
                .lt(ReturnTaskDemand::getCompletionAmount, ReturnTaskDemand::getAmount);
        return use.execution().list();
    }

    /**
     * 更新完成数量
     *
     * @param id               主键
     * @param completionAmount 完成的数量
     */
    public void updateCompletionAmount(Integer id, Integer completionAmount) {
        Db<ReturnTaskDemand> use = Db.use(ReturnTaskDemand.class);
        use.update().add(ReturnTaskDemand::getCompletionAmount, ReturnTaskDemand::getCompletionAmount, completionAmount);
        use.execution().updateById(id);
    }

    /**
     * 根据回归任务id查询是否任务全部完成
     *
     * @param returnTaskId 回归任务id
     * @return true 未完成
     */
    public Boolean findCountByReturnTaskId(Integer returnTaskId) {
        Db<ReturnTaskDemand> use = Db.use(ReturnTaskDemand.class);
        use.where().eq(ReturnTaskDemand::getReturnTaskId, returnTaskId)
                .lt(ReturnTaskDemand::getCompletionAmount, ReturnTaskDemand::getAmount);
        return use.execution().count() > 0;
    }

    /**
     * 根据回归任务id查询
     *
     * @param returnTaskIdList 回归任务id
     * @return 回归任务目标
     */
    public List<ReturnTaskDemand> findByReturnTaskIds(List<Integer> returnTaskIdList) {
        Db<ReturnTaskDemand> use = Db.use(ReturnTaskDemand.class);
        use.where().in(ReturnTaskDemand::getReturnTaskId, returnTaskIdList);
        return use.execution().list();
    }

    /**
     * 根据回归id删除
     *
     * @param returnPlayerTimeId 回归id
     */
    public void delByReturnPlayerTimeId(Integer returnPlayerTimeId) {
        Db<ReturnTaskDemand> use = Db.use(ReturnTaskDemand.class);
        use.where().eq(ReturnTaskDemand::getReturnPlayerTimeId, returnPlayerTimeId);
        use.execution().delete();
    }

    /**
     * 查询全部
     *
     * @return list
     */
    public List<ReturnTaskDemand> findAll() {
        return Db.use(ReturnTaskDemand.class).execution().list();
    }

}
