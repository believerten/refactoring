package com.data;

import lombok.*;

/**
 * ¹¦ÄÜ:
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    String commitId;
    String refactoring;
    Integer leftsideStartLine;
    String lable;
}
