package com.fixInducing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.data.ChangeLeftLine;
import com.data.ChangeLeftLineJira;
import com.data.Result;
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

import java.io.*;
import java.util.*;


/**
 *
 */
public class inducefileRefactPair {
    private static int number;
    private static List<ChangeLeftLine> changeLeftLineList1 = new ArrayList<>();
    private static List<ChangeLeftLineJira> inducingLineList1 = new ArrayList<>();
    private static  List<Result> resultArrayList = new ArrayList<>();
    public static void main(String[] args) throws Exception {


        String name = "";

        //minerList
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = gitService.cloneIfNotExists(
                "ws-wss4j",
                "");

        miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                if (!refactorings.isEmpty()){
                    number+=refactorings.size();
                }

                //System.out.println("Refactorings at " + commitId);
                for (int i = 0 ; i < refactorings.size() ; i++) {
                    if (refactorings.size()!=0){
                        //System.out.println("�ع��ύ��"+commitId);
                        List<CodeRange> list1 = refactorings.get(i).leftSide();
                        for (CodeRange range : list1){
                            ChangeLeftLine changeLeftLine = new ChangeLeftLine();
                            changeLeftLine.setCommitId(commitId);
                            changeLeftLine.setRefactoring(refactorings.get(i).getName());
                            changeLeftLine.setLeftsideStartLine(range.getStartLine());
                            changeLeftLine.setLable(commitId+i);
                            changeLeftLine.setFilePath(range.getFilePath());
                            changeLeftLineList1.add(changeLeftLine);
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
        System.out.println(number);


        //jiraList
        String basic = "D:\\360\\SZZUnleashed\\SZZUnleashed\\szz\\build\\libs\\resultsWSS";
        File file = new File(basic);
        String[] resultList = file.list();
        /**
         for (String resultn : resultList){
         File file1 = new File(basic+"\\"+resultn+"\\fix_and_introducers_pairs.json");
         if (file1.exists()){
         String fixInducePair = getStr(file1);
         //JSONObject jsonObject = (JSONObject) JSON.parseObject(fixInducePair);
         JSONArray array = JSON.parseArray(fixInducePair);
         for (Object object : array){
         //�ڶ�������
         JSONArray array1 = (JSONArray) object;
         String inducingCommit = array.getString(1);
         inducingList.add(inducingCommit);
         }
         }else {

         }
         }
         **/
        for (String resultn : resultList){
            File file2 = new File(basic+"/"+resultn+"/annotations.json");
            if (file2.exists()){
                String annotation = getStr(file2);
                JSONObject jsonObjecta = (JSONObject) JSON.parseObject(annotation);
                chuli(jsonObjecta);
                /**Set<String> keys = jsonObjecta.keySet();
                for (String key : keys){
                    //�ڶ�������
                    JSONArray array = jsonObjecta.getJSONArray(key);
                    for (Object object : array){
                        JSONObject jsonObject = (JSONObject) object;
                        chuli(jsonObject);
                    }

                }*/

            }else{

            }
        }

        for (ChangeLeftLineJira changeLeftLineJira : inducingLineList1){
            String commitId = changeLeftLineJira.getCommitId();
            Integer line = changeLeftLineJira.getLineNumber();
            String path = changeLeftLineJira.getFilepath();


            for (ChangeLeftLine changeLeftLine :changeLeftLineList1){
                if (commitId.equals(changeLeftLine.getCommitId()) && line==(changeLeftLine.getLeftsideStartLine())&&path.equals(changeLeftLine.getFilePath())){
                    Result result = new Result();
                    result.setRefactoring(changeLeftLine.getRefactoring()) ;
                    result.setCommitId(changeLeftLine.getCommitId());
                    result.setLeftsideStartLine(changeLeftLine.getLeftsideStartLine());
                    result.setLable(changeLeftLine.getLable());
                    resultArrayList.add(result);


                }else{
                }
            }
        }



        File file2 = new File("D:\\360\\r\\r\\pair\\ws-wss4jidPair.csv" );
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
        //BufferedWriter bw = new BufferedWriter(fw);

        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("�ع�����","commitId","�����к�","lable","numberOfRefac");
        CSVPrinter printer = null;
        try {
            printer = csvFormat.print(fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Result r1 : resultArrayList){
            printer.printRecord(r1.getRefactoring(),r1.getCommitId(),r1.getLeftsideStartLine(),r1.getLable(),number);

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
    public static void chuli(JSONObject object){
        Set<String> keys = object.keySet();
        for (String key : keys){
            String inducecommit = key;

                //�ڶ�������
                JSONArray array = object.getJSONArray(key);
                for (Object objectsub : array){

                    JSONObject jsonObject1 = (JSONObject) objectsub;
                   String filePath = jsonObject1.getString("filePath");
                   JSONArray revisions = jsonObject1.getJSONArray("revisions");
                   if ((revisions.size() == 2) && revisions.get(0).equals(inducecommit) ) {
                       String fixcommit = (String) revisions.get(1);

                       JSONObject jsonObjectMapping = jsonObject1.getJSONObject("mappings");
                       Set<String> mappingkeyset = jsonObjectMapping.keySet();
                       for (String mappingkey : mappingkeyset) {
                           if (mappingkey.equals(fixcommit)) {
                               JSONObject fix = jsonObjectMapping.getJSONObject(fixcommit);

                               Set<String> line = fix.keySet();
                               for(String jiraline : line ){
                                   ChangeLeftLineJira changeLeftLineJira = new ChangeLeftLineJira();
                                   changeLeftLineJira.setLineNumber(Integer.valueOf(jiraline));
                                   changeLeftLineJira.setCommitId(inducecommit);
                                   changeLeftLineJira.setFilepath(filePath);
                                   inducingLineList1.add(changeLeftLineJira);
                               }



                           } else {
                           }
                       }

                   }





                }






        }




    }





    //jsonFile to string
    public static String getStr(File jsonFile) throws IOException {
        String jsonStr = "";
        FileReader fileReader = new FileReader(jsonFile);
        Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"UTF-8");
        int ch = 0 ;
        StringBuffer sb = new StringBuffer();
        while((ch=reader.read())!=-1){
            sb.append((char)ch);
        }
        fileReader.close();
        reader.close();
        jsonStr = sb.toString();
        return jsonStr;

    }
}
