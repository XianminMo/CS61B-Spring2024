package main;

import ngrams.NGramMap;
import ngrams.TimeSeries;
import wordnet.WordNet;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.*;
import java.util.stream.Collectors;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;
    public HyponymsHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }


    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        Map<String, Double> wordToCount = new HashMap<>();
        int k = q.k();
        int startYear = q.startYear();
        int endYear = q.endYear();
        Set<String> hyponyms0 = wn.hyponyms(words.get(0));
        if (words.size() == 2) {
            Set<String> hyponyms1 = wn.hyponyms(words.get(1));
            hyponyms0.retainAll(hyponyms1);
        }
        if (k == 0) {
            return convertSetToSortedString(hyponyms0);
        }
        for (String word : hyponyms0) {
            double count;
            TimeSeries ts = ngm.countHistory(word, startYear, endYear);
            if (ts.isEmpty()) {
                continue;
            }
            count = ts.values().stream().mapToDouble(Double::doubleValue).sum();
            wordToCount.put(word, count);
        }

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
