package cn.handyplus.pln.command.admin;

import cn.handyplus.lib.command.IHandyCommandEvent;
import cn.handyplus.lib.util.AssertUtil;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.lib.util.MessageUtil;
import cn.handyplus.pln.constants.CommandChildTypeEnum;
import cn.handyplus.pln.service.ReturnPlayerTimeService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * 回归积分管理
 * 子命令 give｜set｜take
 *
 * @author handy
 */
public class CoinCommand implements IHandyCommandEvent {
    @Override
    public String command() {
        return "coin";
    }

    @Override
    public String permission() {
        return "playerReturn.coin";
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] args) {
        // 参数是否正常
        AssertUtil.notTrue(args.length < 4, BaseUtil.getLangMsg("paramFailureMsg"));
        // 数量
        Integer amount = AssertUtil.isNumericToInt(args[3], BaseUtil.getLangMsg("amountFailureMsg"));
        if (amount < 1) {
            MessageUtil.sendMessage(sender, BaseUtil.getLangMsg("amountFailureMsg"));
            return;
        }
        boolean rst;
        // 操作类型
        switch (CommandChildTypeEnum.getEnum(args[1], "coin")) {
            case COIN_GIVE:
                rst = ReturnPlayerTimeService.getInstance().give(args[2], amount);
                break;
            case COIN_SET:
                rst = ReturnPlayerTimeService.getInstance().set(args[2], amount);
                break;
            case COIN_TAKE:
                rst = ReturnPlayerTimeService.getInstance().take(args[2], amount);
                break;
            default:
                MessageUtil.sendMessage(sender, BaseUtil.getLangMsg(("typeFailureMsg")));
                return;
        }
        MessageUtil.sendMessage(sender, BaseUtil.getLangMsg(rst ? "succeedMsg" : "noReturnMsg"));
    }

}
