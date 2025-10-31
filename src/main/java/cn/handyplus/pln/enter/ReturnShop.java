package cn.handyplus.pln.enter;

import cn.handyplus.lib.annotation.TableField;
import cn.handyplus.lib.annotation.TableName;
import lombok.Data;

/**
 * 回归商店
 *
 * @author handy
 */
@Data
@TableName(value = "return_shop", comment = "回归商店")
public class ReturnShop {

    @TableField(value = "id", comment = "ID")
    private Integer id;

    @TableField(value = "price", comment = "价格", notNull = true)
    private Integer price;

    @TableField(value = "item_stack", comment = "自定义物品", length = 10000, notNull = true)
    private String itemStack;

    @TableField(value = "number", comment = "限购次数", notNull = true, fieldDefault = "0")
    private Integer number;

}