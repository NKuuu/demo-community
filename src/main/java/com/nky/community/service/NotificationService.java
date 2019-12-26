package com.nky.community.service;

import com.nky.community.dto.NotificationDTO;
import com.nky.community.dto.PaginationDTO;
import com.nky.community.entity.Notification;
import com.nky.community.entity.NotificationExample;
import com.nky.community.entity.User;
import com.nky.community.entity.UserExample;
import com.nky.community.enums.NotificationStatusEnum;
import com.nky.community.enums.NotificationTypeEnum;
import com.nky.community.exception.CustomizeErrorCode;
import com.nky.community.exception.CustomizeException;
import com.nky.community.mapper.NotificationMapper;
import com.nky.community.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther:nky
 * @Date:2019/10/16
 * @Description:com.nky.community.service
 * @version:1.0
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 拿到消息数据
     */
    public PaginationDTO list(Long userId, Integer page, Integer size) {
        // 创建消息DTO
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        // 得到总页数
        Integer totalPage;
        // 创建mapper中sql的拼接条件
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        notificationExample.setOrderByClause("gmt_create desc");
        // 根据条件得到notification的记录总量
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);

        if (totalCount != 0 && totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);
        Integer offset = size * (page - 1);
        // 得到了分页后的请求数据
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));

        if (notifications.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOList = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOList);

        return paginationDTO;
    }

    /**
     * 未读数
     *
     * @param userId
     * @return
     */
    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());

        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {

        Notification notification = notificationMapper.selectByPrimaryKey(id);
        // 消息不存在
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        // 读取的消息不属于当前用户
        if (!(notification.getReceiver()).equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
