package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName winning_record
 */
@TableName(value ="winning_record")
@Data
public class WinningRecord {
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
     * 活动名称
     */
    private String activityName;

    /**
     * 奖品id
     */
    private Long prizeId;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 奖品等级
     */
    private String prizeTier;

    /**
     * 中奖人id
     */
    private Long winnerId;

    /**
     * 中奖人姓名
     */
    private String winnerName;

    /**
     * 中奖人邮箱
     */
    private String winnerEmail;

    /**
     * 中奖人电话
     */
    private String winnerPhoneNumber;

    /**
     * 中奖时间
     */
    private Date winningTime;

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
        WinningRecord other = (WinningRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getActivityId() == null ? other.getActivityId() == null : this.getActivityId().equals(other.getActivityId()))
            && (this.getActivityName() == null ? other.getActivityName() == null : this.getActivityName().equals(other.getActivityName()))
            && (this.getPrizeId() == null ? other.getPrizeId() == null : this.getPrizeId().equals(other.getPrizeId()))
            && (this.getPrizeName() == null ? other.getPrizeName() == null : this.getPrizeName().equals(other.getPrizeName()))
            && (this.getPrizeTier() == null ? other.getPrizeTier() == null : this.getPrizeTier().equals(other.getPrizeTier()))
            && (this.getWinnerId() == null ? other.getWinnerId() == null : this.getWinnerId().equals(other.getWinnerId()))
            && (this.getWinnerName() == null ? other.getWinnerName() == null : this.getWinnerName().equals(other.getWinnerName()))
            && (this.getWinnerEmail() == null ? other.getWinnerEmail() == null : this.getWinnerEmail().equals(other.getWinnerEmail()))
            && (this.getWinnerPhoneNumber() == null ? other.getWinnerPhoneNumber() == null : this.getWinnerPhoneNumber().equals(other.getWinnerPhoneNumber()))
            && (this.getWinningTime() == null ? other.getWinningTime() == null : this.getWinningTime().equals(other.getWinningTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getActivityId() == null) ? 0 : getActivityId().hashCode());
        result = prime * result + ((getActivityName() == null) ? 0 : getActivityName().hashCode());
        result = prime * result + ((getPrizeId() == null) ? 0 : getPrizeId().hashCode());
        result = prime * result + ((getPrizeName() == null) ? 0 : getPrizeName().hashCode());
        result = prime * result + ((getPrizeTier() == null) ? 0 : getPrizeTier().hashCode());
        result = prime * result + ((getWinnerId() == null) ? 0 : getWinnerId().hashCode());
        result = prime * result + ((getWinnerName() == null) ? 0 : getWinnerName().hashCode());
        result = prime * result + ((getWinnerEmail() == null) ? 0 : getWinnerEmail().hashCode());
        result = prime * result + ((getWinnerPhoneNumber() == null) ? 0 : getWinnerPhoneNumber().hashCode());
        result = prime * result + ((getWinningTime() == null) ? 0 : getWinningTime().hashCode());
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
        sb.append(", activityName=").append(activityName);
        sb.append(", prizeId=").append(prizeId);
        sb.append(", prizeName=").append(prizeName);
        sb.append(", prizeTier=").append(prizeTier);
        sb.append(", winnerId=").append(winnerId);
        sb.append(", winnerName=").append(winnerName);
        sb.append(", winnerEmail=").append(winnerEmail);
        sb.append(", winnerPhoneNumber=").append(winnerPhoneNumber);
        sb.append(", winningTime=").append(winningTime);
        sb.append("]");
        return sb.toString();
    }
}