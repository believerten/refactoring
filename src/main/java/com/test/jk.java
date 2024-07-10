package com.test;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.util.List;

/**
 *
 */
public class jk {
    private static int number = 0;
    public static void main(String[] args) throws Exception {
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = gitService.cloneIfNotExists(
                "unomi",
                "");

        miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                System.out.println("Refactorings at " + refactorings.size());

                    if (!refactorings.isEmpty()){
                       number+= 1;

                }
            }
            @Override
            public void handleException(String commit, Exception e) {
                System.err.println("Error processing commit " + commit);
                e.printStackTrace(System.err);
            }

        });
        System.out.println(number);
    }

}
