package dynamic_top_k.service.impl;

import dynamic_top_k.common.Constants;
import dynamic_top_k.entity.Mention;
import dynamic_top_k.entity.MentionComparator;
import dynamic_top_k.service.DynamicRankService;
import jakarta.annotation.Resource;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("dynamicRankService")
public class DynamicRankServiceImpl implements DynamicRankService {

    private final HashMap<String, Mention> string2Mention = new HashMap<>();

    private final TreeSet<Mention> sortedMentions = new TreeSet<>(new MentionComparator());

    @Resource
    private CacheManager cacheManager;

    @Override
    public synchronized Mention update(String word) {
        // Null or empty words would be ignored.
        if (word == null || word.isEmpty()) {
            return null;
        }
        Mention mention = string2Mention.get(word);
        if (mention != null) {
            sortedMentions.remove(mention);
            mention.setCount(mention.getCount() + 1);
            mention.setLastMentioned(System.currentTimeMillis());
        } else {
            if (string2Mention.size() == Constants.MAX_RANK_LIST_CAPACITY) {
                Mention lastMention = sortedMentions.pollLast();
                if (lastMention != null) {
                    string2Mention.remove(lastMention.getWord());
                }
            }
            mention = new Mention(word, 1, System.currentTimeMillis());
            string2Mention.put(mention.getWord(), mention);
        }
        sortedMentions.add(mention);
        // Clear the cache
        Objects.requireNonNull(cacheManager.getCache("topKCache")).clear();
        return mention;
    }

    @Override
    @Cacheable(value = "topKCache", key = "#k")
    public synchronized List<Mention> query(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("The rank to query cannot be less than 0.");
        }
        List<Mention> list = new ArrayList<>(k);
        Iterator<Mention> iterator = sortedMentions.iterator();
        while (list.size() < k && iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
}
