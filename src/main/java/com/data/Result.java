package com.data;

import lombok.*;

/**
 * ����:
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
