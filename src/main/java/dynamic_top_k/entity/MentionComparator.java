package dynamic_top_k.entity;

import java.util.Comparator;

public class MentionComparator implements Comparator<Mention> {
    @Override
    public int compare(Mention o1, Mention o2) {
        if (o1.getCount() != o2.getCount()) {
            return Long.compare(o2.getCount(), o1.getCount());
        } else if (o1.getLastMentioned() != o2.getLastMentioned()) {
            return Long.compare(o2.getLastMentioned(), o1.getLastMentioned());
        } else {
            return o1.getWord().compareTo(o2.getWord());
        }
    }
}
