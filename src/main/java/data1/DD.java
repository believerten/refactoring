package com.data1;

import lombok.SneakyThrows;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public class DD {

    private static Git git;
    private static Repository repository;
    public void diffMethod(String Child, String Parent){
        try {
            git=Git.open(new File("D:\\360\\WorkPlace\\dd\\untitled\\bigtop"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        repository=git.getRepository();
        ObjectReader reader = repository.newObjectReader();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();

        try {
            ObjectId old = repository.resolve(Child + "^{tree}");
            ObjectId head = repository.resolve(Parent+"^{tree}");
            oldTreeIter.reset(reader, old);
            CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
            newTreeIter.reset(reader, head);
            List<DiffEntry> diffs= git.diff()
                    .setNewTree(newTreeIter)
                    .setOldTree(oldTreeIter)
                    .call();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DiffFormatter df = new DiffFormatter(out);
            df.setRepository(git.getRepository());

            for (DiffEntry diffEntry : diffs) {
                df.format(diffEntry);
                String diffText = out.toString("UTF-8");
                System.out.println(diffText);
                //  out.reset();
            }
        } catch (IncorrectObjectTypeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
    private  static   GitService gitService = new GitServiceImpl();
    private  static  Repository repo;

    static {
        try {
            repo = gitService.cloneIfNotExists("bigtop", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public DD(){}
    public static void main(String[] args) throws Exception {

        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();


        RevWalk walk = new RevWalk(repo);

        DD dd = new DD();
        dd.diffMethod("ab4f414a945bcbf3924f4c4390b569b3b9175c0d","0f51fb32531daa6c0b66e6bbd8fc9813023019ba");
        miner.detectAll(repo, "master", new RefactoringHandler() {
            @SneakyThrows
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                if (!refactorings.isEmpty()) {
                    ObjectId versionId = null;
                    try {
                        versionId = repo.resolve(commitId);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    RevCommit verCommit = walk.parseCommit(versionId);



                }
            }
                @Override
                public void handleException(String commit, Exception e) {
                    System.err.println("Error processing commit " + commit);
                    e.printStackTrace(System.err);
                }
            });
        }

    }
