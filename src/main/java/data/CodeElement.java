package com.data;

import lombok.*;

/**
 * ����:
 * ����:LXY
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CodeElement {
    String lable;
    String codeElement;
    String time;
    String refactoring;
    String commitId;
}
