package com.git;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.data.Fix;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class CommitMessage {
    private static String regex = "(?i)(fix|solve|close).*?(bug|defect|crash|fail|error)";
    private static Repository repo = null;
    public static void main(String[] args) throws Exception {
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        String repoName = "erlide_eclipse";
         repo = gitService.cloneIfNotExists(repoName,"");
          miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                File fileFixJson = new File("D:\\360\\SZZUnleashed\\SZZUnleashed\\szz\\build\\libs\\issue_list"+repoName+".json");
                if (!fileFixJson.exists()) {
                    try {
                        fileFixJson.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                FileWriter fw = null;
                try {
                    fw = new FileWriter(fileFixJson,true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedWriter bw = new BufferedWriter(fw);

                String versionTag = commitId;
                RevWalk walk = new RevWalk(repo);
                ObjectId versionId = null;
                try {
                    versionId = repo.resolve(versionTag);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                RevCommit commit = null;
                try {
                    commit = walk.parseCommit(versionId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String message = commit.getFullMessage();
                Pattern pattern = Pattern.compile(regex);
                Matcher match = pattern.matcher(message);

                if (match.find()){
                    try {
                        bw.write(" \"");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        bw.write(commitId);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        bw.write("\"");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        bw.write(":");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Fix fix = new Fix();
                    int time = commit.getCommitTime();
                    String rTime = stringCommitTime(time);
                    fix.setCreationdate(rTime);
                    fix.setResolutiondate(rTime);
                    fix.setCommitdate(rTime);
                    fix.setHash(commit.getName());
                    JSONObject object = (JSONObject) JSON.toJSON(fix);
                    String content = JSON.toJSONString(object, SerializerFeature.PrettyFormat,
                            SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
                    try {
                        bw.write(content);
                        bw.write(",");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println(commitId);

                }

                try {
                    bw.close();
                    fw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
            @Override
            public void handleException(String commit, Exception e) {
                System.err.println("Error processing commit " + commit);
                e.printStackTrace(System.err);
            }
        });

    }
    private static JSONObject gitToJson(RevCommit revCommit){
        Fix fix = new Fix();
        int time = revCommit.getCommitTime();
        String rTime = stringCommitTime(time);
        fix.setCreationdate(rTime);
        fix.setResolutiondate(rTime);
        fix.setCommitdate(rTime);
        fix.setHash(revCommit.getName());
        JSONObject jsonObject = (JSONObject) JSON.toJSON(fix);
        return jsonObject;
    }/*
    private static void jsonFile(JSONObject jsonObject, BufferedWriter bw) throws IOException {
        String content = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
        bw.write(content);

    }*/
    private static String stringCommitTime(int commitTime){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        String timestampString=String.valueOf(commitTime);
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = formatter.format(new Date(timestamp));
        return date;
    }
}
