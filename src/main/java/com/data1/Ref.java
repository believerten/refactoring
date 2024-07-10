package com.data1;

import gr.uom.java.xmi.diff.CodeRange;
import lombok.*;

import java.util.List;

/**
 * ¹¦ÄÜ:
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Ref {
    String commitId;
    String name;
    Integer lable;
    String type ;
    Integer isBuginducing;
    Integer isHaflfyearUnstable;
    Integer isYearUnstable;
    Integer isThreemonthUnstable;
    List<CodeRange> leftSideLocations;
    List<CodeRange> rightSideLocations;

}
