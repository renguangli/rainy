package com.rainy.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * rainy-console
 *
 * @author renguangli
 * @date 2022/4/7 09:17
 */
public class PageInfo<T> implements IPage<T> {

    /* 自定义参数 begin*/
    @ApiModelProperty("是否分页:默认分页")
    private boolean paged = true;

    @ApiModelProperty("是否正序:默认正序")
    private boolean asc = true;

    @ApiModelProperty("排序字段：多个用英文逗号隔开")
    private List<String> columns;
    /* 自定义参数 end*/

    @ApiModelProperty("数据列表")
    protected List<T> records;

    @ApiModelProperty("当前页")
    protected long current;

    @ApiModelProperty("分页大小")
    protected long size;

    @ApiModelProperty("总数")
    protected long total;

    @ApiModelProperty("总页数")
    protected long pages;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    protected List<OrderItem> orders;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    protected boolean optimizeCountSql;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    protected boolean searchCount;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    protected boolean optimizeJoinOfCountSql;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    protected String countId;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    protected Long maxLimit;

    public PageInfo() {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
        this.orders = new ArrayList<>();
        this.optimizeCountSql = true;
        this.searchCount = true;
        this.optimizeJoinOfCountSql = true;
    }

    public PageInfo(long current, long size) {
        this(current, size, 0L);
    }

    public PageInfo(long current, long size, long total) {
        this(current, size, total, true);
    }

    public PageInfo(long current, long size, boolean searchCount) {
        this(current, size, 0L, searchCount);
    }

    public PageInfo(long current, long size, long total, boolean searchCount) {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
        this.orders = new ArrayList<>();
        this.optimizeCountSql = true;
        this.searchCount = true;
        this.optimizeJoinOfCountSql = true;
        if (current > 1L) {
            this.current = current;
        }

        this.size = size;
        this.total = total;
        this.searchCount = searchCount;
    }

    public boolean isPaged() {
        return paged;
    }

    public void setPaged(boolean paged) {
        this.paged = paged;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    @Override
    public long getPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

    @Override
    public PageInfo<T> setPages(long pages) {
        // to do nothing
        return this;
    }

    public boolean hasPrevious() {
        return this.current > 1L;
    }

    public boolean hasNext() {
        return this.current < this.getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public PageInfo<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public PageInfo<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public PageInfo<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public PageInfo<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    @Override
    public String countId() {
        return this.countId;
    }

    @Override
    public Long maxLimit() {
        return this.maxLimit;
    }

    private String[] mapOrderToArray(Predicate<OrderItem> filter) {
        List<String> columns = new ArrayList<>(this.orders.size());
        this.orders.forEach((i) -> {
            if (filter.test(i)) {
                columns.add(i.getColumn());
            }

        });
        return columns.toArray(new String[0]);
    }

    private void removeOrder(Predicate<OrderItem> filter) {
        for(int i = this.orders.size() - 1; i >= 0; --i) {
            if (filter.test(this.orders.get(i))) {
                this.orders.remove(i);
            }
        }

    }

    public PageInfo<T> addOrder(OrderItem... items) {
        this.orders.addAll(Arrays.asList(items));
        return this;
    }

    public PageInfo<T> addOrder(List<OrderItem> items) {
        this.orders.addAll(items);
        return this;
    }

    @Override
    public List<OrderItem> orders() {
        return this.orders;
    }

    @Override
    public boolean optimizeCountSql() {
        return this.optimizeCountSql;
    }

    public static <T> PageInfo<T> of(long current, long size, long total, boolean searchCount) {
        return new PageInfo<>(current, size, total, searchCount);
    }

    @Override
    public boolean optimizeJoinOfCountSql() {
        return this.optimizeJoinOfCountSql;
    }

    public PageInfo<T> setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
        return this;
    }

    public PageInfo<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

    public static <T> PageInfo<T> of(long current, long size) {
        return of(current, size, 0L);
    }

    public static <T> PageInfo<T> of(long current, long size, long total) {
        return of(current, size, total, true);
    }

    public static <T> PageInfo<T> of(long current, long size, boolean searchCount) {
        return of(current, size, 0L, searchCount);
    }

    @Override
    public boolean searchCount() {
        return this.total >= 0L && this.searchCount;
    }

    /** @deprecated */
    @Deprecated
    public String getCountId() {
        return this.countId;
    }

    /** @deprecated */
    @Deprecated
    public Long getMaxLimit() {
        return this.maxLimit;
    }

    /** @deprecated */
    @Deprecated
    public List<OrderItem> getOrders() {
        return this.orders;
    }

    /** @deprecated */
    @Deprecated
    public boolean isOptimizeCountSql() {
        return this.optimizeCountSql;
    }

    /** @deprecated */
    @Deprecated
    public boolean isSearchCount() {
        return this.searchCount;
    }

    public void setOrders(final List<OrderItem> orders) {
        this.orders = orders;
    }

    public void setOptimizeJoinOfCountSql(final boolean optimizeJoinOfCountSql) {
        this.optimizeJoinOfCountSql = optimizeJoinOfCountSql;
    }

    public void setCountId(final String countId) {
        this.countId = countId;
    }

    public void setMaxLimit(final Long maxLimit) {
        this.maxLimit = maxLimit;
    }

}
