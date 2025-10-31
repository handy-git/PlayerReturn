package cn.handyplus.pln.command.admin;

import cn.handyplus.lib.command.IHandyCommandEvent;
import cn.handyplus.lib.util.AssertUtil;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.lib.util.ItemStackUtil;
import cn.handyplus.pln.enter.ReturnShop;
import cn.handyplus.pln.service.ReturnShopService;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * 添加回归商店商品
 *
 * @author handy
 */
public class AddShopCommand implements IHandyCommandEvent {
    @Override
    public String command() {
        return "addShop";
    }

    @Override
    public String permission() {
        return "playerReturn.addShop";
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] args) {
        // 参数是否正常
        AssertUtil.notTrue(args.length < 2, BaseUtil.getLangMsg("paramFailureMsg"));
        // 是否为玩家
        Player player = AssertUtil.notPlayer(sender, BaseUtil.getLangMsg("noPlayerFailureMsg"));
        // 获取价格
        Integer price = AssertUtil.isNumericToInt(args[1], BaseUtil.getLangMsg("amountFailureMsg"));
        ItemStack itemInMainHand = ItemStackUtil.getItemInMainHand(player.getInventory());
        // 是否为物品
        AssertUtil.notTrue(Material.AIR.equals(itemInMainHand.getType()), BaseUtil.getLangMsg("noAir"));
        Integer number = 0;
        if (args.length > 2) {
            number = AssertUtil.isNumericToInt(args[2], BaseUtil.getLangMsg("amountFailureMsg"));
        }
        ReturnShop returnShop = new ReturnShop();
        returnShop.setPrice(price);
        returnShop.setNumber(number);
        returnShop.setItemStack(ItemStackUtil.itemStackSerialize(itemInMainHand));
        int rst = ReturnShopService.getInstance().add(returnShop);
        sender.sendMessage(BaseUtil.getLangMsg(rst > 0 ? "succeedMsg" : "failureMsg"));
    }

}