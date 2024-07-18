package dynamic_top_k.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mention implements Serializable {
    private static final long serialVersionUID = 3567653491060394671L;
    private String word;
    private long count;
    private long lastMentioned;
}
