package cn.butterfly.tree.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * bean 复制工具类
 *
 * @author zjw
 * @date 2021-12-12
 */
public class CopyBeanUtils {

    /**
     * 将原始列表转换为目标列表
     *
	 * @param sourceList 原始列表
	 * @param genFunction 目标实体生成函数
     * @return 转换列表结果
     */
    public static <S, R> List<R> copyList(List<S> sourceList, Supplier<R> genFunction) {
        return sourceList.stream()
                .map(source -> copy(source, genFunction))
                .collect(Collectors.toList());
    }

    /**
     * 将源实体转换为目标实体
     *
	 * @param source 源实体
	 * @param genFunction 目标实体生成函数
     * @return 目标实体
     */
    public static <S, R> R copy(S source, Supplier<R> genFunction) {
        R r = genFunction.get();
        BeanUtils.copyProperties(source, r);
        return r;
    }

    /**
     * 将原始列表转换为目标列表
     *
     * @param sourceList 原始列表
     * @param genFunction 目标实体生成函数
     * @param converterConsumer 自定义转换的 set 函数
     * @param converterSupplier 自定义转换的 get 函数
     * @return 转换列表结果
     */
    public static <S, R, T> List<R> copyList(
            List<S> sourceList,
            Supplier<R> genFunction,
            BiConsumer<R, T> converterConsumer,
            Function<S, T> converterSupplier
    ) {
        return sourceList.stream()
                .map(source -> copy(source, genFunction, converterConsumer, converterSupplier))
                .collect(Collectors.toList());
    }

    /**
     * 将源实体转换为目标实体并包含自定义转换函数
     *
     * @param source 源实体
     * @param genFunction 目标实体生成函数
     * @param converterConsumer 自定义转换的 set 函数
     * @param converterSupplier 自定义转换的 get 函数
     * @return 目标实体
     */
    public static <S, R, T> R copy(
            S source,
            Supplier<R> genFunction,
            BiConsumer<R, T> converterConsumer,
            Function<S, T> converterSupplier
    ) {
        R r = genFunction.get();
        BeanUtils.copyProperties(source, r);
        converterConsumer.accept(r, converterSupplier.apply(source));
        return r;
    }

    private CopyBeanUtils() {}

}
