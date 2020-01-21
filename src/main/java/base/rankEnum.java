package base;

/**
 * @author lmc
 * @date 2020/1/20 9:50
 */
public enum rankEnum {
    Gold("10000", "金奖"),
    Silver("5000", "银奖"),
    Bronze("3000", "铜奖");

    private String reward;
    private String remark;
    rankEnum(String reward, String remark) {
        this.reward = reward;
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public String getReward() {
        return reward;
    }
}
