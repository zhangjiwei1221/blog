package cn.butterfly.back.util;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * 分页工具类
 *
 * @author zjw
 * @date 2022-01-21
 */
public class PageUtils {

    /**
     * 获取分页信息
     *
	 * @param originList 原列表
	 * @param pageList 分页列表
     * @return 分页信息
     */
    public static <P, T> Page<T> getPage(List<P> originList, List<T> pageList) {
        Page<T> page = new Page<>();
        page.setTotal(new PageInfo<>(originList).getTotal());
        page.setRows(pageList);
        return page;
    }

    /**
     * 自定义分页类
     *
     * @author zjw
     * @date 2022-01-21
     */
    @Data
    public static class Page<T> {

        /**
         * 数据列表
         */
        private List<T> rows;

        /**
         * 总数
         */
        private long total;

    }

    private PageUtils() {}

}
