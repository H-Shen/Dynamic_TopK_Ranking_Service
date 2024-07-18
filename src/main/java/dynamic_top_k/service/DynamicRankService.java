package dynamic_top_k.service;

import dynamic_top_k.entity.Mention;

import java.util.List;

public interface DynamicRankService {
    Mention update(String word);

    List<Mention> query(int k);
}
