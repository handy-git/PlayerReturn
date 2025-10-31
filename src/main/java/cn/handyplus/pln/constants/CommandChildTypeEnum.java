package cn.handyplus.pln.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 子命令类型
 *
 * @author handy
 */
@Getter
@AllArgsConstructor
public enum CommandChildTypeEnum {
    /**
     * 类型
     */
    COIN_GIVE("give", "coin"),
    COIN_SET("set", "coin"),
    COIN_TAKE("take", "coin"),

    NOT("not", "not");


    private final String childType;
    private final String parentType;

    /**
     * 获取对应类型
     *
     * @param childType  子类型
     * @param parentType 父类型
     * @return 类型
     */
    public static CommandChildTypeEnum getEnum(String childType, String parentType) {
        for (CommandChildTypeEnum commandChildTypeEnum : CommandChildTypeEnum.values()) {
            if (!commandChildTypeEnum.parentType.equalsIgnoreCase(parentType)) {
                continue;
            }
            if (!commandChildTypeEnum.childType.equalsIgnoreCase(childType)) {
                continue;
            }
            return commandChildTypeEnum;
        }
        return NOT;
    }
}
