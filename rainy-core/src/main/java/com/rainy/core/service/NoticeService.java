package com.rainy.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.core.entity.Notice;

/**
 * spring-boot-example-console
 *
 * @author renguangli
 * @date 2022/4/28 18:03
 */
public interface NoticeService extends IService<Notice> {

    boolean publish(Notice notice);
}
