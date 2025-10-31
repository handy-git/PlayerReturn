package cn.handyplus.pln.listener.gui;

import cn.handyplus.lib.inventory.HandyInventory;
import cn.handyplus.lib.inventory.HandyInventoryUtil;
import cn.handyplus.lib.inventory.IHandyClickEvent;
import cn.handyplus.pln.constants.GuiTypeEnum;
import cn.handyplus.pln.inventory.ViewShopGui;
import cn.handyplus.pln.service.ReturnShopService;
import cn.handyplus.pln.util.ConfigUtil;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

/**
 * 回归商店管理
 *
 * @author handy
 */
public class ViewShopClickEvent implements IHandyClickEvent {
    @Override
    public String guiType() {
        return GuiTypeEnum.VIEW_SHOP.getType();
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public void rawSlotClick(HandyInventory handyInventory, InventoryClickEvent event) {
        int rawSlot = event.getRawSlot();
        Integer pageNum = handyInventory.getPageNum();
        Integer pageCount = handyInventory.getPageCount();
        Map<Integer, Integer> map = handyInventory.getIntMap();

        // 关闭按钮
        if (HandyInventoryUtil.isIndex(rawSlot, ConfigUtil.SHOP_CONFIG, "back")) {
            handyInventory.syncClose();
            return;
        }
        // 上一页
        if (HandyInventoryUtil.isIndex(rawSlot, ConfigUtil.SHOP_CONFIG, "previousPage")) {
            if (pageNum > 1) {
                handyInventory.setPageNum(handyInventory.getPageNum() - 1);
                ViewShopGui.getInstance().setInventoryDate(handyInventory);
            }
            return;
        }
        // 下一页
        if (HandyInventoryUtil.isIndex(rawSlot, ConfigUtil.SHOP_CONFIG, "nextPage")) {
            if (pageNum + 1 <= pageCount) {
                handyInventory.setPageNum(handyInventory.getPageNum() + 1);
                ViewShopGui.getInstance().setInventoryDate(handyInventory);
            }
            return;
        }

        //  点击删除
        Integer id = map.get(rawSlot);
        if (id != null) {
            ReturnShopService.getInstance().delById(id);
            ViewShopGui.getInstance().setInventoryDate(handyInventory);
        }
    }

}