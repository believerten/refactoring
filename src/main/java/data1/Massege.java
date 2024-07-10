package com.data1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.SneakyThrows;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Massege {

    public static boolean createJsonFile(Object jsonData, String filePath) {
        String content = JSON.toJSONString(jsonData, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            Writer write = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            write.write(content);
            write.flush();
            write.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
    public static void main(String[] args) throws Exception {
        List<Ref> list = new ArrayList<>();
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        Repository repo =  gitService.cloneIfNotExists("bigtop", "");

        RevWalk walk = new RevWalk(repo);
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

                    for (int i = 0; i < refactorings.size(); i++) {
                        Ref ref = new Ref();
                        ref.setCommitId(commitId);
                        ref.setLable(i);
                        ref.setType(refactorings.get(i).getName());
                        ref.setName(verCommit.getAuthorIdent().getName());
                        ref.setLeftSideLocations(refactorings.get(i).leftSide());
                        ref.setRightSideLocations(refactorings.get(i).rightSide());


                       list.add(ref);

                    }

                }
            }
            @Override
            public void handleException(String commit, Exception e) {
                System.err.println("Error processing commit " + commit);
                e.printStackTrace(System.err);
            }
        });


        createJsonFile( list,"D:\\360\\ts\\bigtop.json");
    }
}
