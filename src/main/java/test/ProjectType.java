package com.test;
import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.util.*;

/**
 */
public class ProjectType {
    private static int yes = 0;
    private static int no = 0;
    public static void main(String[] args) throws Exception {


        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = null;
        String name = "leshan";
        repo = gitService.cloneIfNotExists(name, "");
        miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                //System.out.println("Refactorings at " + commitId);
                Set<String> typeSet = new HashSet<>();
                if (refactorings.isEmpty()) {
                   no += 1;

                }else{
                    yes += 1;
                }
            }


            @Override
            public void handleException(String commit, Exception e) {
                System.err.println("Error processing commit " + commit);
                e.printStackTrace(System.err);
            }


        });
        System.out.println("含有重构的commit："+yes);
        System.out.println("不含重构的commit"+no);


    }
}
