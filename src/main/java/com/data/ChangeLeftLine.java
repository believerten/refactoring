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
public class ChangeLeftLine {
    String commitId;
    String refactoring;
    Integer leftsideStartLine;
    String lable;
    String filePath;


}
