package com.data;

import lombok.*;

/**
 * 功能:
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
     * 功能:
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
