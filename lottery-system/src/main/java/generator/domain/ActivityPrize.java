package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName activity_prize
 */
@TableName(value ="activity_prize")
@Data
public class ActivityPrize {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 活动关联的奖品id
     */
    private Long prizeId;

    /**
     * 关联奖品的数量
     */
    private Long prizeAmount;

    /**
     * 奖品等级
     */
    private String prizeTiers;

    /**
     * 活动奖品状态
     */
    private String status;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ActivityPrize other = (ActivityPrize) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getActivityId() == null ? other.getActivityId() == null : this.getActivityId().equals(other.getActivityId()))
            && (this.getPrizeId() == null ? other.getPrizeId() == null : this.getPrizeId().equals(other.getPrizeId()))
            && (this.getPrizeAmount() == null ? other.getPrizeAmount() == null : this.getPrizeAmount().equals(other.getPrizeAmount()))
            && (this.getPrizeTiers() == null ? other.getPrizeTiers() == null : this.getPrizeTiers().equals(other.getPrizeTiers()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getActivityId() == null) ? 0 : getActivityId().hashCode());
        result = prime * result + ((getPrizeId() == null) ? 0 : getPrizeId().hashCode());
        result = prime * result + ((getPrizeAmount() == null) ? 0 : getPrizeAmount().hashCode());
        result = prime * result + ((getPrizeTiers() == null) ? 0 : getPrizeTiers().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", activityId=").append(activityId);
        sb.append(", prizeId=").append(prizeId);
        sb.append(", prizeAmount=").append(prizeAmount);
        sb.append(", prizeTiers=").append(prizeTiers);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}