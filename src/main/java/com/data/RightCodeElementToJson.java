package com.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import gr.uom.java.xmi.diff.CodeRange;
import lombok.*;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class RightCodeElementToJson {

    private static String printTime(int commitTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestampString=String.valueOf(commitTime);
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = formatter.format(new Date(timestamp));
        return date;
    }

    public static boolean createJsonFile(Object jsonData, String filePath) {
        String content = JSON.toJSONString(jsonData, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        try {
            File file = new File(filePath);
            // 创建上级目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            // 如果文件存在，则删除文件
            if (file.exists()) {
                file.delete();
            }
            // 创建文件
            file.createNewFile();
            // 写入文件
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



    private static List<CodeElement> rightCodeElementList = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        Repository repo =  gitService.cloneIfNotExists("bigtop", "");
        miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                if (!refactorings.isEmpty()){
                    for (Refactoring ref : refactorings){

                        List<CodeRange> rightCodeRange = ref.rightSide();
                        for (CodeRange range : rightCodeRange){
                            CodeElement rightCodeElement = new CodeElement();

                            RevWalk walk = new RevWalk(repo);
                            ObjectId versionId = null;
                            try {
                                versionId = repo.resolve(commitId);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            RevCommit commit = null;
                            try {
                                commit = walk.parseCommit(versionId);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            int verCommitTime = commit.getCommitTime();
                            String commitTime = printTime(verCommitTime);
                            rightCodeElement.setTime(commitTime);
                            rightCodeElement.setCodeElement(range.getFilePath()+"\\"+range.getCodeElement());
                            rightCodeElement.setRefactoring(ref.getName());
                            rightCodeElement.setCommitId(commitId);
                            rightCodeElementList.add(rightCodeElement);
                        }


                    }
                }
            }
            @Override
            public void handleException(String commit, Exception e) {
                System.err.println("Error processing commit " + commit);
                e.printStackTrace(System.err);
            }
        });
        createJsonFile( rightCodeElementList,"D:\\360\\ts\\rightCodeElement.json");


    }

    /**
     * 功能:
     */
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pair {
        String afterCommitId;
        String element;
        String afterRefact;
        String beforeCommitId;
        String beforeRefact;
        String beforeMessage;
        String afterMessage;
    }
}
