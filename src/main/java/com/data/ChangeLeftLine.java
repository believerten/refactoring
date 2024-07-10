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
public class ChangeLeftLine {
    String commitId;
    String refactoring;
    Integer leftsideStartLine;
    String lable;
    String filePath;


}
