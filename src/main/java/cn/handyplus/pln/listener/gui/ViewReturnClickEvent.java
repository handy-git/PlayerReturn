package cn.handyplus.pln.listener.gui;

import cn.handyplus.lib.inventory.HandyInventory;
import cn.handyplus.lib.inventory.HandyInventoryUtil;
import cn.handyplus.lib.inventory.IHandyClickEvent;
import cn.handyplus.pln.constants.GuiTypeEnum;
import cn.handyplus.pln.inventory.ViewReturnGui;
import cn.handyplus.pln.util.ConfigUtil;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * 回归玩家管理
 *
 * @author handy
 */
public class ViewReturnClickEvent implements IHandyClickEvent {
    @Override
    public String guiType() {
        return GuiTypeEnum.VIEW_RETURN.getType();
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

        // 关闭按钮
        if (HandyInventoryUtil.isIndex(rawSlot, ConfigUtil.RETURN_CONFIG, "back")) {
            handyInventory.syncClose();
            return;
        }
        // 上一页
        if (HandyInventoryUtil.isIndex(rawSlot, ConfigUtil.RETURN_CONFIG, "previousPage")) {
            if (pageNum > 1) {
                handyInventory.setPageNum(handyInventory.getPageNum() - 1);
                ViewReturnGui.getInstance().setInventoryDate(handyInventory);
            }
            return;
        }
        // 下一页
        if (HandyInventoryUtil.isIndex(rawSlot, ConfigUtil.RETURN_CONFIG, "nextPage")) {
            if (pageNum + 1 <= pageCount) {
                handyInventory.setPageNum(handyInventory.getPageNum() + 1);
                ViewReturnGui.getInstance().setInventoryDate(handyInventory);
            }
        }
    }

}