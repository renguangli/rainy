package com.rainy.sys.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.JavaInfo;
import cn.hutool.system.JvmInfo;
import cn.hutool.system.RuntimeInfo;
import cn.hutool.system.SystemUtil;
import com.rainy.common.constant.CharConstants;
import com.rainy.sys.entity.SysMonitor;
import com.rainy.sys.service.SysMonitorService;
import org.springframework.stereotype.Service;

import java.lang.management.OperatingSystemMXBean;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/24 18:47
 */
@Service
public class SysMonitorServiceImpl implements SysMonitorService {

    private static final int mb = 1024 * 1024;

    @Override
    public SysMonitor getSysMonitor() {
        SysMonitor sysMonitor = new SysMonitor();
        // 操作系统信息
        OperatingSystemMXBean os = SystemUtil.getOperatingSystemMXBean();
        sysMonitor.setOsArch(os.getArch());
        sysMonitor.setOsName(os.getName() + CharConstants.SLASH + os.getVersion());
        sysMonitor.setAvailableProcessors(os.getAvailableProcessors());
        sysMonitor.setSystemLoadAverage(os.getSystemLoadAverage());

        // java 信息
        JavaInfo javaInfo = SystemUtil.getJavaInfo();
        sysMonitor.setJavaVendor(javaInfo.getVendor());
        sysMonitor.setJavaVersion(javaInfo.getVersion());

        // jvm 信息
        JvmInfo jvmInfo = SystemUtil.getJvmInfo();
        sysMonitor.setJvmName(jvmInfo.getName());
        sysMonitor.setJvmVersion(jvmInfo.getVersion());
        // 运行时长
        sysMonitor.setRuntime(System.currentTimeMillis() - SystemUtil.getRuntimeMXBean().getStartTime());
        RuntimeInfo runtimeInfo = SystemUtil.getRuntimeInfo();
        sysMonitor.setMaxMemory(runtimeInfo.getMaxMemory() / mb); // 最大内存
        sysMonitor.setTotalMemory(runtimeInfo.getTotalMemory() / mb); // 已分配内存
        sysMonitor.setFreeMemory(runtimeInfo.getFreeMemory() / mb); // 剩余内存
        long useMemory = runtimeInfo.getTotalMemory() - runtimeInfo.getFreeMemory(); // 已用内存
        sysMonitor.setUseMemory(useMemory / mb);
        // 内存使用率
        double useRate = NumberUtil.div(useMemory, runtimeInfo.getTotalMemory()) * 100;
        sysMonitor.setUseRate(NumberUtil.round(useRate, 2).doubleValue());
        return sysMonitor;
    }
}
