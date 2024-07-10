package com.fixInducing;

import com.data.ChangeLeftLine;
import gr.uom.java.xmi.diff.CodeRange;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.eclipse.jgit.lib.Repository;
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class GetMinerLeftSideLine {
    private static List<ChangeLeftLine> changeLeftLineList = new ArrayList<>();
    public static void main(String[] args) throws Exception {

        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = gitService.cloneIfNotExists(
                "jenkins",
                "");

        miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {

                //System.out.println("Refactorings at " + commitId);
                for (Refactoring ref : refactorings) {
                    if (refactorings.size()!=0){
                        //System.out.println("重构提交："+commitId);
                        List<CodeRange> list1 = ref.leftSide();
                        for (CodeRange range : list1){
                            ChangeLeftLine changeLeftLine = new ChangeLeftLine();
                            changeLeftLine.setCommitId(commitId);
                            changeLeftLine.setRefactoring(ref.getName());
                            changeLeftLine.setLeftsideStartLine(range.getStartLine());
                            changeLeftLineList.add(changeLeftLine);
                        }

                    }else{
                    }
                    //System.out.println(ref.getName());
                }
            }
            @Override
            public void handleException(String commit, Exception e) {
                System.err.println("Error processing commit " + commit);
                e.printStackTrace(System.err);
            }
        });


        File file2 = new File("D:\\360\\buginducing\\lineOfRefact\\jenkinsLeftSideLine.csv" );
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(file2,true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter bw = new BufferedWriter(fw);

        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("commitId","重构","开始行号");
        CSVPrinter printer = null;
        try {
            printer = csvFormat.print(fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (ChangeLeftLine leftLine : changeLeftLineList){
            try {
                printer.printRecord(leftLine.getCommitId(),leftLine.getRefactoring(),leftLine.getLeftsideStartLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            printer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            printer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        }

    }

