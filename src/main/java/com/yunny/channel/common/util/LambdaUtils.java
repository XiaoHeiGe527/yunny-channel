package com.yunny.channel.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <p>
 * JDK 8 lambda常用工具类
 * </p>
 *
 * @author lzy
 * @since 2021-01-22
 */
public class LambdaUtils {


    /**
     * list分组操作
     *
     * @param list：要操作的list集合
     * @param function:需要进行分组的对象属性
     * @return Map<K, List < V>>
     */
    public static <K, V> Map<K, List<V>> listGroupingBy(List<V> list, Function<V, K> function) {
        return list.stream().collect(Collectors.groupingBy(function));
    }

    /**
     * list根据对象指定属性进行转Map
     *
     * @param list：要操作的list集合
     * @param function:转成Map后的key 注意的是：如果有相同的key,则保留key1,key2,则保留key1舍弃key2
     * @return Map<K, V>
     */
    public static <K, V> Map<K, V> listToMap(List<V> list, Function<V, K> function) {
        return list.stream().collect(Collectors.toMap(function, a -> a, (k1, k2) -> k1));
    }

    /**
     * list根据对象指定属性+条件过滤出新的List
     *
     * @param list：要操作的list集合
     * @param predicate:      过滤条件，例如a->a.getUserName().equals("xxx)
     *                        过滤出名字=xxx的集合
     * @return List<T>
     */
    public static <T> List<T> listFilter(List<T> list, Predicate<? super T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * list根据对象指定属性满足指定条件并返回布尔值(任意匹配到一个)
     *
     * @param list：要操作的list集合
     * @param predicate:      过滤条件，例如a->a.getUserName().equals("xxx)
     *                        过滤出名字=xxx的集合
     * @return List<T>
     */
    public static <T> boolean listAnyMatch(List<T> list, Predicate<? super T> predicate) {
        return list.stream().anyMatch(predicate);
    }

    /**
     * list根据对象指定属性满足指定条件并返回布尔值(没有匹配到一个)
     *
     * @param list：要操作的list集合
     * @param predicate:      过滤条件，例如a->a.getUserName().equals("xxx)
     *                        过滤出名字=xxx的集合
     * @return List<T>
     */
    public static <T> boolean listNoMatch(List<T> list, Predicate<? super T> predicate) {
        return list.stream().noneMatch(predicate);
    }

    /**
     * list根据对象收集出新的list
     *
     * @param list   ：要操作的list集合
     * @param mapper : 收集
     * @return List<T>
     */
    public static <T, R, A> List<R> listToList(List<T> list, Function<? super T, ? extends R> mapper) {
        return list.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * list根据对象指定属性去重
     *
     * @param list：要操作的list集合
     * @param keyExtractor:   去重属性
     * @return List<T>
     */
    public static <T> List<T> listDistinctBy(List<T> list, Function<? super T, ?> keyExtractor) {
        return list.stream()
                .filter(distinctByKey(keyExtractor))
                .collect(Collectors.toList());
    }

    //jdk 8集合根据属性去重工具
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * list去重（适用于简单包装的类型，如String,Integer..）
     *
     * @param list：要操作的list集合
     * @return List<T>
     */
    public static <T> List<T> listDistinct(List<T> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * list求和 (int求和)
     *
     * @param list：要操作的list集合
     * @return List<T>
     */
    public static <T> int listToSumIntBy(List<T> list, ToIntFunction<? super T> mapper) {
        return list.stream().mapToInt(mapper).sum();
    }

    /**
     * list求和 (int求平均值)
     *
     * @param list：要操作的list集合(size不能为0,否则返回0)
     * @return List<T>
     */
    public static <T> double listToIntAvgBy(List<T> list, ToIntFunction<? super T> mapper) {
        boolean present = list.stream().mapToInt(mapper).average().isPresent();
        double asDouble = 0.0;
        if (present) {
            asDouble = list.stream().mapToInt(mapper).average().getAsDouble();
        }
        return asDouble;
    }

    /**
     * list求和 (int求最大值)
     *
     * @param list：要操作的list集合(size不能为0,否则返回0)
     * @return List<T>
     */
    public static <T> int listToMaxIntBy(List<T> list, ToIntFunction<? super T> mapper) {
        boolean present = list.stream().mapToInt(mapper).max().isPresent();
        int asInt = 0;
        if (present) {
            asInt = list.stream().mapToInt(mapper).max().getAsInt();
            return asInt;
        }
        return 0;
    }

    /**
     * list求和 (int求最小值)
     *
     * @param list：要操作的list集合(size不能为0,否则返回0)
     * @return List<T>
     */
    public static <T> int listToMinIntBy(List<T> list, ToIntFunction<? super T> mapper) {
        boolean present = list.stream().mapToInt(mapper).min().isPresent();
        int asInt = 0;
        if (present) {
            asInt = list.stream().mapToInt(mapper).min().getAsInt();
            return asInt;
        }
        return 0;
    }

    /**
     * list转换String 用逗号分割
     *
     * @param list：要操作的list集合(size不能为0,否则返回0)
     * @return List<T>
     */
    public static <T, R, A> String listToStringComma(List<T> list, Function<? super T, ? extends R> mapper) {
        return StringUtils.join(list.stream().map(mapper).collect(Collectors.toList()), ",");
    }

    /**
     * String转换list 用逗号分割
     *
     * @param data：操作数据 如：A=1,2,3  A= A,B,A
     * @return List<T>
     */
    public static <T> List<String> StringTolistComma(String data) {
        return Arrays.asList(StringUtils.split(data,",")).stream().map(s -> (s.trim())).collect(Collectors.toList());
    }


    /**
     * double 类型的集合求和
     *
     * @param doubles
     * @return
     */
    public static Double doubleSum(List<Double> doubles) {
        return doubles.stream().collect(Collectors.summarizingDouble(value -> value)).getSum();
    }


    /**
     * 两个字符串List 取交集
     *
     * @param listOne
     * @param listTwo
     * @return
     */
    public static List<String> intersectionListString(List<String> listOne, List<String> listTwo) {
        return listOne.stream().filter(item -> listTwo.contains(item)).collect(Collectors.toList());
    }

    /**
     * 两个字符串List 差集 (list1 - list2)
     *
     * @param listOne
     * @param listTwo
     * @return
     */
    public static List<String> subtractionListString(List<String> listOne, List<String> listTwo) {
        return listOne.stream().filter(item -> !listTwo.contains(item)).collect(Collectors.toList());
    }

    /**
     * List 并集
     *
     * @param listOne
     * @param listTwo
     * @return
     */
    public static List<String> unionListString(List<String> listOne, List<String> listTwo) {
        List<String> listAll = listOne.parallelStream().collect(Collectors.toList());
        List<String> listAll2 = listTwo.parallelStream().collect(Collectors.toList());
        listAll.addAll(listAll2);
        return listAll;
    }


    /**
     * 两个整形List 取交集
     *
     * @param listOne
     * @param listTwo
     * @return
     */
    public static List<Integer> intersectionListInt(List<Integer> listOne, List<Integer> listTwo) {
        return listOne.stream().filter(item -> listTwo.contains(item)).collect(Collectors.toList());
    }

    /**
     * List int 并集
     *
     * @param listOne
     * @param listTwo
     * @return
     */
    public static List<Integer> unionListInt(List<Integer> listOne, List<Integer> listTwo) {
        List<Integer> listAll = listOne.parallelStream().collect(Collectors.toList());
        List<Integer> listAll2 = listTwo.parallelStream().collect(Collectors.toList());
        listAll.addAll(listAll2);
        //List->Set
        Set<Integer> sortSet = new LinkedHashSet<>(listAll);
        //Set->List
        return new ArrayList<>(sortSet);
    }


    /**
     * 截取字符串,可以替代list.subList[start,end)
     *
     * @param list       要截取的List
     * @param startIndex 开始位置
     * @param endIndex   结束位置
     * @param <T>        返回类型
     * @return 截取后结果
     */
    public static <T> List<T> subList(List<T> list, int startIndex, int endIndex) {
        return list.stream()
                .skip(startIndex)
                .limit(endIndex - startIndex)
                .collect(Collectors.toList());
    }

    /**
     * 对每个元素执行给定的操作
     * @param elements 元素
     * @param action 每个元素要执行的操作
     * @param <T> T
     */
    public static <T> void forEach(Iterable<? extends T> elements, BiConsumer<Integer, ? super T> action) {
        forEach(0, elements, action);
    }

    /**
     * 对每个元素执行给定的操作
     * @param startIndex 开始下标
     * @param elements 元素
     * @param action 每个元素要执行的操作
     * @param <T> T
     */
    public static <T> void forEach(int startIndex, Iterable<? extends T> elements, BiConsumer<Integer, ? super T> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);
        if(startIndex < 0) {
            startIndex = 0;
        }
        int index = 0;
        for (T element : elements) {
            index++;
            if(index <= startIndex) {
                continue;
            }
            action.accept(index-1, element);
        }
    }

    //内部使用函数式接口
    @FunctionalInterface
    public interface ToBigDecimalFunction<T> {
        BigDecimal applyAsBigDecimal(T value);
    }

    static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();

    @SuppressWarnings("unchecked")
    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }

    static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        CollectorImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner,
                      Function<A, R> finisher, Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        CollectorImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner,
                      Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

    //求和方法
    private static <T> Collector<T, ?, BigDecimal> summingBigDecimal(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{BigDecimal.ZERO},
                (a, t) -> {
                    a[0] = a[0].add(mapper.applyAsBigDecimal(t));
                },
                (a, b) -> {
                    a[0] = a[0].add(b[0]);
                    return a;
                },
                a -> a[0], CH_NOID);
    }

    //求最大值
    private static <T> Collector<T, ?, BigDecimal> maxBy(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{new BigDecimal(Long.MIN_VALUE)},
                (a, t) -> {
                    a[0] = a[0].max(mapper.applyAsBigDecimal(t));
                },
                (a, b) -> {
                    a[0] = a[0].max(b[0]);
                    return a;
                },
                a -> a[0], CH_NOID);
    }

    //求最小值
    private static <T> Collector<T, ?, BigDecimal> minBy(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{new BigDecimal(Long.MAX_VALUE)},
                (a, t) -> {
                    a[0] = a[0].min(mapper.applyAsBigDecimal(t));
                },
                (a, b) -> {
                    a[0] = a[0].min(b[0]);
                    return a;
                },
                a -> a[0], CH_NOID);
    }

    //求平均值
    private static <T> Collector<T, ?, BigDecimal> averagingBigDecimal(ToBigDecimalFunction<? super T> mapper, int newScale, int roundingMode) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO},
                (a, t) -> {
                    a[0] = a[0].add(mapper.applyAsBigDecimal(t));
                    a[1] = a[1].add(BigDecimal.ONE);
                },
                (a, b) -> {
                    a[0] = a[0].add(b[0]);
                    return a;
                },
                a -> a[0].divide(a[1], BigDecimal.ROUND_HALF_UP).setScale(newScale, roundingMode), CH_NOID);
    }

    /**
     * list去重 (BigDecimal求和)
     *
     * @param list：要操作的list集合
     * @param newScale:       保留小数位
     * @param roundingMode    : 舍弃规则
     * @return List<T>
     */
    public static <T> BigDecimal listToSumBigDecimalBy(List<T> list, ToBigDecimalFunction<? super T> mapper, int newScale, int roundingMode) {
        return list.stream().collect(LambdaUtils.summingBigDecimal(mapper)).setScale(newScale, roundingMode);
    }

    /**
     * list去重 (BigDecimal求平均值)
     *
     * @param list：要操作的list集合
     * @param newScale:       保留小数位
     * @param roundingMode    : 舍弃规则
     * @return List<T>
     */
    public static <T> BigDecimal listToAveragingBigDecimalBy(List<T> list, ToBigDecimalFunction<? super T> mapper, int newScale, int roundingMode) {
        return list.stream().collect(LambdaUtils.averagingBigDecimal(mapper, newScale, roundingMode));
    }

    /**
     * list去重 (BigDecimal求大值)
     *
     * @param list：要操作的list集合
     * @param newScale:       保留小数位
     * @param roundingMode    : 舍弃规则
     * @return List<T>
     */
    public static <T> BigDecimal listToMaxBigDecimalBy(List<T> list, ToBigDecimalFunction<? super T> mapper, int newScale, int roundingMode) {
        return list.stream().collect(LambdaUtils.maxBy(mapper)).setScale(newScale, roundingMode);
    }

    /**
     * list去重 (BigDecimal求最小值)
     *
     * @param list：要操作的list集合
     * @param newScale:       保留小数位
     * @param roundingMode    : 舍弃规则
     * @return List<T>
     */
    public static <T> BigDecimal listToMinBigDecimalBy(List<T> list, ToBigDecimalFunction<? super T> mapper, int newScale, int roundingMode) {
        return list.stream().collect(LambdaUtils.maxBy(mapper)).setScale(newScale, roundingMode);
    }

    //结束-------------------------------------------------------
}


