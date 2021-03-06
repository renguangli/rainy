package com.rainy.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.sys.entity.Notice;
import com.rainy.sys.entity.NoticeUserRel;
import com.rainy.sys.mapper.NoticeMapper;
import com.rainy.sys.service.NoticeService;
import com.rainy.sys.service.NoticeUserRelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * rainy-console
 *
 * @author renguangli
 * @date 2022/4/28 18:04
 */
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    private final NoticeUserRelService noticeUserRelService;

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
