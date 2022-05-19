package com.rainy.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.core.entity.Notice;
import com.rainy.core.entity.NoticeUserRel;
import com.rainy.core.mapper.NoticeMapper;
import com.rainy.core.service.NoticeService;
import com.rainy.core.service.NoticeUserRelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * spring-boot-example-console
 *
 * @author renguangli
 * @date 2022/4/28 18:04
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Resource
    private NoticeUserRelService noticeUserRelService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publish(Notice notice) {
        this.baseMapper.insert(notice);
        List<Integer> userIds = notice.getUserIds();
        List<NoticeUserRel> rels = new ArrayList<>();
        for (Integer userId : userIds) {
            NoticeUserRel rel = new NoticeUserRel();
            rel.setNoticeId(notice.getId());
            rel.setUserId(userId);
            rels.add(rel);
        }
        return noticeUserRelService.saveBatch(rels);
    }
}
