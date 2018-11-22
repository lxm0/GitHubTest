package Jgit;

import entity.CommitInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import javax.sql.rowset.serial.SerialBlob;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitHubInfo {
    final static String login = "lxm0";
    final static String passwd = "githublxm0";

    public static void main(String[] args)throws ClassNotFoundException, SQLException{
        List<CommitInfo> commitInfos =getGitHubCommit();
        //getExcel(commitInfos);
        //insertCommitInfo(commitInfos);
    }
    public static List<CommitInfo> getGitHubCommit(){
        try {
            GitHub github = GitHub.connectUsingPassword(login,passwd);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            GHRepository ghRepository = github.getRepository("python/cpython");
            ghRepository.listCommits();
            int count= 0;
            String changeContent="";

            List<CommitInfo> commitInfos = new ArrayList<CommitInfo>();
            for(GHCommit commit:ghRepository.listCommits()){
                CommitInfo commitInfo = new CommitInfo();

                count++;
                commitInfo.setCommitName(commit.getCommitShortInfo().getAuthor().getName());
                commitInfo.setCommitId(commit.getSHA1());
                commitInfo.setCommitTime(new java.sql.Date( commit.getCommitShortInfo().getCommitDate().getTime()));
                commitInfo.setCommitMessage(commit.getCommitShortInfo().getMessage());
                System.out.println( count +" **********************************");
                System.out.println( "提交人： "+commitInfo.getCommitName());
                System.out.println( "提交SHA1： "+commit.getSHA1());
                System.out.println( "提交信息： "+commit.getCommitShortInfo().getMessage());
                System.out.println( "提交时间： "+format.format(commit.getCommitShortInfo().getCommitDate()));
                Map<String,String> map  =new HashMap<String, String>();
                for(GHCommit.File file:commit.getFiles()){
                    changeContent = changeContent+file.getPatch();
                    map.put(file.getFileName(),file.getPatch());
                }
                JSONObject json = new JSONObject(map);
                System.out.println("Patch:  "+json);
                System.out.println("Patch:  "+changeContent);

                Blob b = new SerialBlob(changeContent.getBytes("GBK"));
                commitInfo.setCommitContent(b);
                commitInfos.add(commitInfo);
                changeContent = "";
                if (count>5){break;}
            }
            //System.out.println(count);
            return commitInfos;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static void insertCommitInfo(List<CommitInfo> commitInfos)throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8","root","123456");
            Statement stmt = conn.createStatement();
            //conn.setAutoCommit(false);
            PreparedStatement pstmt;
            int i = 0;
            String sql = "insert into commitInfo(commit_id,commit_name,commit_time,commit_message,commit_content) values(?,?,?,?,?)";
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            for(CommitInfo commitInfo:commitInfos){
                pstmt.setString(1,commitInfo.getCommitId());
                pstmt.setString(2,commitInfo.getCommitName());
                pstmt.setDate(3,commitInfo.getCommitTime());
                pstmt.setString(4,commitInfo.getCommitMessage());
                pstmt.setBlob(5,commitInfo.getCommitContent());

                pstmt.addBatch();
            }
            pstmt.executeBatch();
            // pstmt.setInt(1, user.getId());

            // pstmt.setInt(4, user.getRoleId());
            //i = pstmt.executeUpdate();
            pstmt.close();
            stmt.close();
            conn.close();
        } catch (Exception e){
            System.out.println(e);

        }

    }

    public static void getExcel(List<CommitInfo> commitInfos)throws ClassNotFoundException, SQLException{
        //获取项目的webroot路径
        String pathStr = "C:\\Users\\GT\\Desktop\\文档";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pathStr+"\\commitInfo.xlsx");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //List<Student> list= stuService.getAll();
        //创建excel
        Workbook workbook = new HSSFWorkbook();
        //创建sheet--子工作表
        Sheet sheet = workbook.createSheet("commitInfo");
        //获取子工作表的第一行<设置标题>
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("commit_id");
        row.createCell(1).setCellValue("commit_name");
        row.createCell(2).setCellValue("commit_time");
        row.createCell(3).setCellValue("commit_massage");
        row.createCell(4).setCellValue("commit_content");
        for(int i = 0;i <commitInfos.size();i++){
            String blobString = new String(commitInfos.get(i).getCommitContent().getBytes(1, (int) commitInfos.get(i).getCommitContent().length()),"GBK");
            //工作表赋值从第二行开始
            row = sheet.createRow((i+1));
            row.createCell(0).setCellValue(commitInfos.get(i).getCommitId());
            row.createCell(1).setCellValue(commitInfos.get(i).getCommitName());
            row.createCell(2).setCellValue(format.format(commitInfos.get(i).getCommitTime()));
            row.createCell(3).setCellValue(commitInfos.get(i).getCommitMessage());
            row.createCell(4).setCellValue(blobString);
        }

            workbook.write(fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
