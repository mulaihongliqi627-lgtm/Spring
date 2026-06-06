package com.amadeus.lotterysystem.service.Impl;

import com.amadeus.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.amadeus.lotterysystem.common.exception.ServiceException;
import com.amadeus.lotterysystem.common.utils.JacksonUtil;
import com.amadeus.lotterysystem.common.utils.RedisUtil;
import com.amadeus.lotterysystem.controller.param.CreateActivityParam;
import com.amadeus.lotterysystem.controller.param.CreatePrizeByActivityParam;
import com.amadeus.lotterysystem.controller.param.CreateUserByActivityParam;
import com.amadeus.lotterysystem.controller.param.PageParam;
import com.amadeus.lotterysystem.dao.dataobject.ActivityDO;
import com.amadeus.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.amadeus.lotterysystem.dao.dataobject.ActivityUserDO;
import com.amadeus.lotterysystem.dao.dataobject.PrizeDO;
import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.mapper.ActivityMapper;
import com.amadeus.lotterysystem.dao.mapper.ActivityPrizeMapper;
import com.amadeus.lotterysystem.dao.mapper.ActivityUserMapper;
import com.amadeus.lotterysystem.dao.mapper.PrizeMapper;
import com.amadeus.lotterysystem.dao.mapper.UserMapper;
import com.amadeus.lotterysystem.service.ActivityService;
import com.amadeus.lotterysystem.service.dto.ActivityDTO;
import com.amadeus.lotterysystem.service.dto.ActivityDetailDTO;
import com.amadeus.lotterysystem.service.dto.CreateActivityDTO;
import com.amadeus.lotterysystem.service.dto.PageListDTO;
import com.amadeus.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.amadeus.lotterysystem.service.enums.ActivityPrizeTiersEnum;
import com.amadeus.lotterysystem.service.enums.ActivityStatusEnum;
import com.amadeus.lotterysystem.service.enums.ActivityUserStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PrizeMapper prizeMapper;
    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;

    @Autowired
    private ActivityUserMapper activityUserMapper;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 活动缓存前置，为了区分业务
     */
    private final String ACTIVITY_PREFIX = "ACTIVITY_";
    /**
     * 活动缓存过期时间
     */
    private final Long ACTIVITY_TIMEOUT = 60 * 60 * 24 * 3L;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateActivityDTO createActivity(CreateActivityParam param) {
        //校验活动参数
        Map<Long, UserDO> userDOMap = checkActivityParam(param);

        //保存活动信息
        ActivityDO activityDO = new ActivityDO();
        activityDO.setActivityName(param.getActivityName());
        activityDO.setDescription(param.getDescription());
        activityDO.setStatus(ActivityStatusEnum.RUNNING.name());
        activityMapper.insert(activityDO);

        //保存活动关联的奖品信息
        List<ActivityPrizeDO> activityPrizeDOList = param.getActicityPrizeList()
                .stream()
                .map(prize -> {
                    ActivityPrizeDO activityPrizeDO = new ActivityPrizeDO();
                    activityPrizeDO.setActivityId(activityDO.getId());
                    activityPrizeDO.setPrizeId(prize.getPrizeId());
                    activityPrizeDO.setPrizeAmount(prize.getPrizeAmount());
                    activityPrizeDO.setPrizeTiers(ActivityPrizeTiersEnum.forName(prize.getPrizeTiers()).name());
                    activityPrizeDO.setStatus(ActivityPrizeStatusEnum.INIT.name());
                    return activityPrizeDO;
                }).collect(Collectors.toList());
        activityPrizeDOList.forEach(activityPrizeMapper::insert);


        //保存活动关联的人员信息
        List<ActivityUserDO> activityUserDOList = param.getActivityUserList()
                .stream()
                .map(user -> {
                    ActivityUserDO activityUserDO = new ActivityUserDO();
                    UserDO userDO = userDOMap.get(user.getUsreId());
                    activityUserDO.setActivityId(activityDO.getId());
                    activityUserDO.setUserId(user.getUsreId());
                    activityUserDO.setUserName(userDO.getUserName());
                    activityUserDO.setStatus(ActivityUserStatusEnum.INIT.name());
                    return activityUserDO;
                }).collect(Collectors.toList());
        activityUserDOList.forEach(activityUserMapper::insert);

        //整合完整的活动信息,存储在redis中
        List<Long> prizeIds = param.getActicityPrizeList()
                .stream()
                .map(CreatePrizeByActivityParam::getPrizeId)
                .distinct()
                .collect(Collectors.toList());

        List<PrizeDO> prizeDOList = prizeMapper.selectBatchIds(prizeIds);
        ActivityDetailDTO detailDTO = convertToActivityDetailDTO(activityDO,activityUserDOList,prizeDOList,activityPrizeDOList);
        cacheActivity(detailDTO);

        //返回活动信息
        CreateActivityDTO createActivityDTO = new CreateActivityDTO();
        createActivityDTO.setActivityId(activityDO.getId());
        return createActivityDTO;
    }

    @Override
    public PageListDTO<ActivityDTO> findActivityList(PageParam param) {
        PageListDTO<ActivityDTO> pageListDTO = new PageListDTO<>();
        //计算总活动数
        pageListDTO.setTotal(activityMapper.selectCount(null));

        //查出活动列表
        List<ActivityDO> activityDOList = activityMapper.selectActivityList(param.offest(),param.getPageSize());

        List<ActivityDTO> activityDTOList = activityDOList
                .stream()
                .map(activityDO -> {
                    ActivityDTO activityDTO = new ActivityDTO();
                    activityDTO.setActivityId(activityDO.getId());
                    activityDTO.setActivityName(activityDO.getActivityName());
                    activityDTO.setDescription(activityDO.getDescription());
                    activityDTO.setStatus(ActivityStatusEnum.forName(activityDO.getStatus()));
                    return activityDTO;
                }).collect(Collectors.toList());

        pageListDTO.setRecords(activityDTOList);
        return pageListDTO;
    }

    /**
     * 缓存完整的活动信息 ActivityDetailDTO
     *
     * @param detailDTO
     */
    private void cacheActivity(ActivityDetailDTO detailDTO) {
        // key: ACTIVITY_12
        // value: ActivityDetailDTO(json)
        if (null == detailDTO || null == detailDTO.getActivityId()) {
            log.warn("要缓存的活动信息不存在!");
            return;
        }

        //加 try catch 是为了让“缓存失败”不要影响主业务。
        try {
            redisUtil.set(ACTIVITY_PREFIX + detailDTO.getActivityId(),
                    JacksonUtil.writeValueAsString(detailDTO),
                    ACTIVITY_TIMEOUT);
        } catch (Exception e) {
            log.error("缓存活动异常，ActivityDetailDTO={}",
                    JacksonUtil.writeValueAsString(detailDTO),
                    e);
        }
    }


    /**
     * 根据活动id从缓存中获取活动详细信息
     *
     * @param activityId
     * @return
     */
    private ActivityDetailDTO getActivityFromCache(Long activityId) {
        if (null == activityId) {
            log.warn("获取缓存活动数据的activityId为空！");
            return null;
        }
        try {
            String str = redisUtil.get(ACTIVITY_PREFIX + activityId);
            if (!StringUtils.hasText(str)) {
                log.info("获取的缓存活动数据为空！key={}", ACTIVITY_PREFIX + activityId);
                return null;
            }
            return JacksonUtil.readValue(str, ActivityDetailDTO.class);
        } catch (Exception e) {
            log.error("从缓存中获取活动信息异常，key={}", ACTIVITY_PREFIX + activityId, e);
            return null;
        }

    }


    private ActivityDetailDTO convertToActivityDetailDTO(ActivityDO activityDO,
                                                         List<ActivityUserDO> activityUserDOList,
                                                         List<PrizeDO> prizeDOList,
                                                         List<ActivityPrizeDO> activityPrizeDOList) {
        ActivityDetailDTO detailDTO = new ActivityDetailDTO();
        detailDTO.setActivityId(activityDO.getId());
        detailDTO.setActivityName(activityDO.getActivityName());
        detailDTO.setDesc(activityDO.getDescription());
        detailDTO.setStatus(ActivityStatusEnum.forName(activityDO.getStatus()));

        // apDO: {prizeId，amount, status}, {prizeId，amount, status}
        // pDO: {prizeid, name....},{prizeid, name....},{prizeid, name....}
        List<ActivityDetailDTO.PrizeDTO> prizeDTOList = activityPrizeDOList
                .stream()
                .map(apDO -> {
                    ActivityDetailDTO.PrizeDTO prizeDTO = new ActivityDetailDTO.PrizeDTO();
                    prizeDTO.setPrizeId(apDO.getPrizeId());
                    Optional<PrizeDO> optionalPrizeDO = prizeDOList.stream()
                            .filter(prizeDO -> prizeDO.getId().equals(apDO.getPrizeId()))
                            .findFirst();
                    // 如果PrizeDO为空，不执行当前方法，不为空才执行
                    optionalPrizeDO.ifPresent(prizeDO -> {
                        prizeDTO.setName(prizeDO.getName());
                        prizeDTO.setImageUrl(prizeDO.getImageUrl());
                        prizeDTO.setPrice(prizeDO.getPrice());
                        prizeDTO.setDescription(prizeDO.getDescription());
                    });
                    prizeDTO.setTiers(ActivityPrizeTiersEnum.forName(apDO.getPrizeTiers()));
                    prizeDTO.setPrizeAmount(apDO.getPrizeAmount());
                    prizeDTO.setStatus(ActivityPrizeStatusEnum.forName(apDO.getStatus()));
                    return prizeDTO;
                }).collect(Collectors.toList());
        detailDTO.setPrizeDTOList(prizeDTOList);

        List<ActivityDetailDTO.UserDTO> userDTOList = activityUserDOList.stream()
                .map(auDO -> {
                    ActivityDetailDTO.UserDTO userDTO = new ActivityDetailDTO.UserDTO();
                    userDTO.setUserId(auDO.getUserId());
                    userDTO.setUserName(auDO.getUserName());
                    userDTO.setStatus(ActivityUserStatusEnum.forName(auDO.getStatus()));
                    return userDTO;
                }).collect(Collectors.toList());
        detailDTO.setUserDTOList(userDTOList);
        return detailDTO;
    }

    private Map<Long, UserDO> checkActivityParam(CreateActivityParam param) {
        if (null == param
                || !StringUtils.hasText(param.getActivityName())
                || !StringUtils.hasText(param.getDescription())
                || CollectionUtils.isEmpty(param.getActivityUserList())
                || CollectionUtils.isEmpty(param.getActicityPrizeList())) {
            throw new ServiceException(ServiceErrorCodeConstants.CREATE_ACTIVITY_INFO_IS_EMPTY);
        }

        if (param.getActivityUserList().stream()
                .anyMatch(user -> user == null || user.getUsreId() == null || !StringUtils.hasText(user.getUserName()))) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_USER_ERROR);
        }

        if (param.getActicityPrizeList().stream()
                .anyMatch(prize -> prize == null || prize.getPrizeId() == null)) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_ERROR);
        }

        if (param.getActicityPrizeList().stream()
                .anyMatch(prize -> prize.getPrizeAmount() == null || prize.getPrizeAmount() <= 0)) {
            throw new ServiceException(ServiceErrorCodeConstants.USER_PRIZE_AMOUNT_ERROR);
        }

        // 人员id在人员表中是否存在
        List<Long> activityUserIds = param.getActivityUserList()
                .stream()
                .map(CreateUserByActivityParam::getUsreId)
                .distinct()
                .collect(Collectors.toList());

        List<UserDO> selectedUserList = userMapper.selectBatchIds(activityUserIds);
        if (CollectionUtils.isEmpty(selectedUserList)) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_USER_ERROR);
        }
        Map<Long, UserDO> selectedUserMap = selectedUserList.stream()
                .collect(Collectors.toMap(UserDO::getId, Function.identity()));
        for(Long activityUserId : activityUserIds){
            if(!selectedUserMap.containsKey(activityUserId)){
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_USER_ERROR);
            }
        }

        // 奖品id在奖品表中是否存在
        List<Long> activityPrizeIds = param.getActicityPrizeList()
                .stream()
                .map(CreatePrizeByActivityParam :: getPrizeId)
                .distinct()
                .collect(Collectors.toList());

        List<Long> selectedPrizeIds = prizeMapper.selectExistIdByIds(activityPrizeIds);
        if (CollectionUtils.isEmpty(selectedPrizeIds)) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_ERROR);
        }
        for(Long activityPrizeId : activityPrizeIds){
            if(!selectedPrizeIds.contains(activityPrizeId)){
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_ERROR);
            }
        }

        // 人员数量大于等于奖品数量
        int userAmount = param.getActivityUserList().size();
        Long prizeAmount = param.getActicityPrizeList()
                .stream()
                .mapToLong(CreatePrizeByActivityParam::getPrizeAmount)
                .sum();
        if(userAmount < prizeAmount){
            throw new ServiceException(ServiceErrorCodeConstants.USER_PRIZE_AMOUNT_ERROR);
        }

        // 校验活动奖品等奖有效性
        param.getActicityPrizeList().forEach(prize -> {
            if (null == ActivityPrizeTiersEnum.forName(prize.getPrizeTiers())) {
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_TIERS_ERROR);
            }
        });

        return selectedUserMap;
    }
}
