package com.data;

import lombok.*;

/**
 * ����:
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChangeLeftLineJira {
    String commitId;
    Integer lineNumber;
    String filepath;

    /**
     * ����:
     */
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LeftRefact {
        String leftCommitId;
        String element;
        String leftRefact;
        String beforeCommitId;
        String beforeRefact;



    }
}
