package com.zksy.reservationsystem.common;

import com.github.pagehelper.PageInfo;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 分页数据封装类
 *
 * @author kkkoke
 * @since 2022/11/28
 */
public class CommonPage<T> {
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 总条数
     */
    private Long total;
    /**
     * 分页数据
     */
    private List<T> list;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    /**
     * 将分页后再次封装的数据进行设置
     */
    public static <T> CommonPage<T> setNewList(Integer pageNum, Integer pageSize, Integer totalPage, Long total, List<T> newList) {
        CommonPage<T> result = new CommonPage<T>();
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setTotalPage(totalPage);
        result.setTotal(total);
        result.setList(newList);
        return result;
    }

    public static <T> CommonPage<T> setNewList(CommonPage oldCommonPage, List<T> newList) {
        int total = newList.size();
        int pageSize = oldCommonPage.getPageSize();
        CommonPage<T> result = new CommonPage<T>();
        result.setPageNum(oldCommonPage.getPageNum());
        result.setPageSize(pageSize);
        result.setTotalPage((total + pageSize - 1) / pageSize);
        result.setTotal(oldCommonPage.getTotal());
        result.setList(newList);
        return result;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
