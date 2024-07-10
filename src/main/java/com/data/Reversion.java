package com.data;

import lombok.*;

/**
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reversion {
    String leftlable;
    String rightlable;
    String leftTime;
    String leftRefactory;
    String leftCommitId;
    String codeElement;
    String rightTime;
    String rightRefactory;
    String rightCommitId;
}
