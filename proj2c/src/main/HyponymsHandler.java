package main;

import browser.NgordnetQueryType;
import edu.princeton.cs.algs4.In;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import wordnet.WordNet;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.*;
import java.util.stream.Collectors;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn; // 存放单词图（上位词、下位词）
    private NGramMap ngm; // 存放单词频次信息
    public HyponymsHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }


    @Override
    public String handle(NgordnetQuery q) {
        // 获取参数以及查询类型
        List<String> words = q.words();
        int k = q.k();
        int startYear = q.startYear();
        int endYear = q.endYear();
        NgordnetQueryType queryType = q.ngordnetQueryType();

        if (queryType == NgordnetQueryType.HYPONYMS) {
            // 处理 hyponyms 类型的查询
            return handleHyponymsQuery(words, k, startYear, endYear);
        } else if (queryType == NgordnetQueryType.ANCESTORS) {
            // 处理 common ancestors 类型的查询
            return handleAncestorsQuery(words, k, startYear, endYear);
        } else {
            // 如果查询类型不匹配，返回一个错误信息
            return "Unsupported query type.";
        }
    }

    private String handleHyponymsQuery(List<String> words, int k, int startYear, int endYear) {
        Set<String> hyponyms0 = wn.hyponyms(words.get(0)); // 获取第一个单词的下位词
        Map<String, Double> wordToCount = new HashMap<>(); // 存放时间区间内单词出现的频次
        if (words.size() == 2) {
            Set<String> hyponyms1 = wn.hyponyms(words.get(1));
            hyponyms0.retainAll(hyponyms1);  // 获取两个单词下位词集合的交集
        }
        // 如果 k == 0, 那么直接返回，没有输入参数k的时候k的默认值即为0
        if (k == 0) {
            return convertSetToSortedString(hyponyms0);
        }

        // 下面处理 k != 0 的情况，根据时间区间内出现频次对所有下位词进行排序并返回前k个词
        for (String word : hyponyms0) {
            double count;
            TimeSeries ts = ngm.countHistory(word, startYear, endYear);
            if (ts.isEmpty()) {
                continue;
            }
            count = ts.values().stream().mapToDouble(Double::doubleValue).sum();
            wordToCount.put(word, count);
        }

        // 如果对应时间段内没有单词，则直接返回一个 empty set
        if (wordToCount.isEmpty()) {
            return convertSetToSortedString(new HashSet<>());
        }

        // 将 Map 转换为 List<Map.Entry<String, Double>>
        List<Map.Entry<String, Double>> list = new ArrayList<>(wordToCount.entrySet());

        // 对 List 进行排序，按 count 大小降序排序
        list.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // 选取前 k 个元素
        List<Map.Entry<String, Double>> topK = list.subList(0, Math.min(k, list.size()));

        // 转成Set
        Set<String> newHyponyms = new HashSet<>();
        for (Map.Entry<String, Double> entry : topK) {
            newHyponyms.add(entry.getKey());
        }
//         以上代码都可以用下面的流式处理简化
//         Set<String> newHyponyms = wordToCount.entrySet().stream()
//                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // 使用 Lambda 表达式
//                .limit(k)
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toSet());


        return convertSetToSortedString(newHyponyms);
    }

    private String handleAncestorsQuery(List<String> words, int k, int startYear, int endYear) {
        Set<String> ancestors0 = wn.ancestors(words.get(0)); // 获取第一个单词的上位词
        Set<String> commonAncestors = new HashSet<>(ancestors0); // 交集初始化
        Map<String, Double> wordToCount = new HashMap<>(); // 存放时间区间内单词出现的频次
        for (String word : words) {
            commonAncestors.retainAll(wn.ancestors(word)); // 寻找所有单词上位词集合的交集
        }
        // 如果 k == 0, 那么直接返回，没有输入参数k的时候k的默认值即为0
        if (k == 0) {
            return convertSetToSortedString(commonAncestors);
        }

        // 下面处理 k != 0 的情况，根据时间区间内出现频次对所有下位词进行排序并返回前k个词
        for (String word : commonAncestors) {
            double count;
            TimeSeries ts = ngm.countHistory(word, startYear, endYear);
            if (ts.isEmpty()) {
                continue;
            }
            count = ts.values().stream().mapToDouble(Double::doubleValue).sum();
            wordToCount.put(word, count);
        }

        // 如果对应时间段内没有单词，则直接返回一个 empty set
        if (wordToCount.isEmpty()) {
            return convertSetToSortedString(new HashSet<>());
        }

        // 根据单词频次选出前k个频次最高的（流式处理）
        Set<String> newAncestors = wordToCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // 使用 Lambda 表达式
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        return convertSetToSortedString(newAncestors);

    }

    // 将一个set变成一个web界面输出格式的string
    public static String convertSetToSortedString(Set<String> set) {
        List<String> sortedList = new ArrayList<>(set);
        Collections.sort(sortedList);
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (String word : sortedList) {
            joiner.add(word);
        }
        return joiner.toString();
    }
}
