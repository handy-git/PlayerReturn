package cn.handyplus.pln.command.admin;

import cn.handyplus.lib.command.IHandyCommandEvent;
import cn.handyplus.lib.constants.BaseConstants;
import cn.handyplus.lib.db.Db;
import cn.handyplus.lib.db.DbTypeEnum;
import cn.handyplus.lib.db.SqlManagerUtil;
import cn.handyplus.lib.util.AssertUtil;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.lib.util.HandyConfigUtil;
import cn.handyplus.lib.util.MessageUtil;
import cn.handyplus.pln.enter.ReturnPlayerInfo;
import cn.handyplus.pln.enter.ReturnPlayerSignIn;
import cn.handyplus.pln.enter.ReturnPlayerTime;
import cn.handyplus.pln.enter.ReturnShop;
import cn.handyplus.pln.enter.ReturnShopLog;
import cn.handyplus.pln.enter.ReturnTask;
import cn.handyplus.pln.enter.ReturnTaskDemand;
import cn.handyplus.pln.service.ReturnPlayerInfoService;
import cn.handyplus.pln.service.ReturnPlayerSignInService;
import cn.handyplus.pln.service.ReturnPlayerTimeService;
import cn.handyplus.pln.service.ReturnShopLogService;
import cn.handyplus.pln.service.ReturnShopService;
import cn.handyplus.pln.service.ReturnTaskDemandService;
import cn.handyplus.pln.service.ReturnTaskService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

/**
 * 转换数据-从mysql转换到sqlite，或者反之
 *
 * @author handy
 */
public class ConvertCommand implements IHandyCommandEvent {

    @Override
    public String command() {
        return "convert";
    }

    @Override
    public String permission() {
        return "playerReturn.convert";
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // 参数是否正常
        AssertUtil.notTrue(args.length < 2, BaseUtil.getLangMsg("paramFailureMsg"));
        String storageMethod = args[1];
        if (!DbTypeEnum.MySQL.getType().equalsIgnoreCase(storageMethod) && !DbTypeEnum.SQLite.getType().equalsIgnoreCase(storageMethod)) {
            sender.sendMessage(BaseUtil.getLangMsg("paramFailureMsg"));
            return;
        }
        if (storageMethod.equalsIgnoreCase(BaseConstants.STORAGE_CONFIG.getString(SqlManagerUtil.STORAGE_METHOD))) {
            MessageUtil.sendMessage(sender, "&4禁止转换！原因，您当前使用的存储方式已经为：" + storageMethod);
            return;
        }
        // 查询当前全部数据
        List<ReturnPlayerInfo> all = ReturnPlayerInfoService.getInstance().findAll();
        List<ReturnPlayerSignIn> all1 = ReturnPlayerSignInService.getInstance().findAll();
        List<ReturnPlayerTime> all2 = ReturnPlayerTimeService.getInstance().findAll();
        List<ReturnShop> all3 = ReturnShopService.getInstance().findAll();
        List<ReturnShopLog> all4 = ReturnShopLogService.getInstance().findAll();
        List<ReturnTask> all5 = ReturnTaskService.getInstance().findAll();
        List<ReturnTaskDemand> all6 = ReturnTaskDemandService.getInstance().findAll();
        // 修改链接方式
        HandyConfigUtil.setPath(BaseConstants.STORAGE_CONFIG, "storage-method", storageMethod, Collections.singletonList("存储方法(MySQL,SQLite)请复制括号内的类型,不要自己写"), "storage.yml");
        // 加载新连接
        SqlManagerUtil.getInstance().enableSql();
        // 新连接创建表
        Db.use(ReturnPlayerInfo.class).createTable();
        Db.use(ReturnPlayerSignIn.class).createTable();
        Db.use(ReturnPlayerTime.class).createTable();
        Db.use(ReturnShop.class).createTable();
        Db.use(ReturnShopLog.class).createTable();
        Db.use(ReturnTask.class).createTable();
        Db.use(ReturnTaskDemand.class).createTable();
        // 插入数据
        Db.use(ReturnPlayerInfo.class).execution().insertBatch(all);
        Db.use(ReturnPlayerSignIn.class).execution().insertBatch(all1);
        Db.use(ReturnPlayerTime.class).execution().insertBatch(all2);
        Db.use(ReturnShop.class).execution().insertBatch(all3);
        Db.use(ReturnShopLog.class).execution().insertBatch(all4);
        Db.use(ReturnTask.class).execution().insertBatch(all5);
        Db.use(ReturnTaskDemand.class).execution().insertBatch(all6);
        MessageUtil.sendMessage(sender, "&4转换数据完成，请务必重启服务器，不然有可能会出现未知bug");
    }

}