package com.amadeus.lotterysystem.controller.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WinningRecordResult implements Serializable {

    private Long activityId;

    private Long prizeId;

    private String prizeName;

    private String prizeTier;

    private Long winnerId;

    private String winnerName;

    private Date winningTime;
}
