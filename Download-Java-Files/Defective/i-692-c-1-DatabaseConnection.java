/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExamWebApp;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.lang.System;
/**
 *
 *
 */
public class DatabaseConnection {

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet reslt = null;
    public int CompletedRows = 0;
    public int UserRows = 0;
    public int CompletedRowss = 0;
    public int CompletedRowsss = 0;

    public DatabaseConnection() {
        String password = "8326.at8.6238";
        String username = "18agileteam8";
        String address = "jdbc:mysql://silva.computing.dundee.ac.uk/18agileteam8db";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        try {
            conn = DriverManager.getConnection(address, username, password);
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
    }

  
    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public String[][] getViewUsers() {
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT UserID,FirstName,Surname,Email FROM user;");

            int rows = 0;
            if (reslt.last()) {
                rows = reslt.getRow();
                reslt.beforeFirst();
            }
            UserRows = rows;
            String[][] ViewUsers = new String[rows][4];
            int i = 0;
            while (reslt.next()) {
                ViewUsers[i][0] = reslt.getString("UserID");
                ViewUsers[i][1] = reslt.getString("FirstName");
                ViewUsers[i][2] = reslt.getString("Surname");
                ViewUsers[i][3] = reslt.getString("Email");
                i++;
            }
            if (ViewUsers != null) {
                return ViewUsers;
            } else {
                return null;
            }
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
        return null;

    }
    
    
    public boolean EditExam(int ExamID, String Title, String School,String ModuleCoordinator,String ModuleCode,String ExamType,String ExamPeriod,String ExamLevel,String Semester,int Year, String DateCreated, String Status,int ExamSetter, int InternalModerator, int ExternalExaminer, int ExamVettingComittee )
    {
        DatabaseConnection db = new DatabaseConnection();
     //Try block to add the repsonse to the comment
        try {
            if(db.createOldVersion(ExamID)){
                stmt = conn.createStatement();
                int success = stmt.executeUpdate("UPDATE exam SET Title = '" + Title + "', School ='"+ School + "', ModuleCoordinator = '" + ModuleCoordinator +"', ModuleCode = '"+ ModuleCode + "', ExamType = '"+ ExamType  + "', ExamPeriod = '"+ ExamPeriod +"', ExamLevel = '"+ ExamLevel +"', Semester = '"+ Semester + "', Year = '"+ Year  + "', Status = '" + Status + "' WHERE ExamID = '" + ExamID + "';");
                int success1 = stmt.executeUpdate("UPDATE assignedexams SET  ModuleCode = '"+ ModuleCode + "', ExamPeriod = '"+ ExamPeriod +"', ExamLevel = '"+ ExamLevel +  "' WHERE AssignedExamID = '" + ExamID + "';");     
                //return true if success, false otherwise
                if (success + success1  != 2) {
                    return false;
                } else {
                    return true;
                }
            }else{
                System.out.println("Error creating old exam version");
                return false;
            } 
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return false;
    }
    
    
        public boolean AssignRole(String UserID, int es,int im,int em,int evc,int so,int leo)
    {
    
        try {
            stmt = conn.createStatement();
            int success = stmt.executeUpdate("UPDATE user SET ExamSetter = '" + es + "', InternalModerator ='"+ im + "', ExternalExaminer = '" + em +"', ExamVettingComittee = '"+ evc + "', SchoolOffice = '"+ so + "', LocalExamOfficer = '"+ leo +"' WHERE UserID = '" + UserID + "';");
                
            //return true if success, false otherwise
            if (success == 0) {
                return false;
            } else {
                return true;
            }
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return false;
    }
    
    
    public String[] checkUser(String email, String password){
        try{
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT UserID FROM user WHERE Email= '"+ email + "' AND Password = '" + password + "'");
            String[] userAccount = new String[7];
            while(reslt.next()){
                userAccount[0] = reslt.getString("UserID");
            }
            if(userAccount[0] != null){
                stmt = conn.createStatement();
                reslt = stmt.executeQuery("SELECT ExamSetter, InternalModerator, ExternalExaminer, ExamVettingComittee, SchoolOffice, LocalExamOfficer FROM role WHERE UserID = " + userAccount[0]);
                while(reslt.next()){
                    userAccount[1] = reslt.getString("ExamSetter");
                    userAccount[2] = reslt.getString("InternalModerator");
                    userAccount[3] = reslt.getString("ExternalExaminer");
                    userAccount[4] = reslt.getString("ExamVettingComittee");
                    userAccount[5] = reslt.getString("SchoolOffice");
                    userAccount[6] = reslt.getString("LocalExamOfficer");
                }
                return userAccount;
            }else{
                return null;
            }
        }catch(SQLException exc){
            
        }
        return null;
    }

    //Function that returns the module code for a given exam
    public String getExamModule(int examID) {
        //Try block to add the repsonse to the comment
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT ModuleCode FROM exam WHERE ExamID = " + examID + ";");

            //return string from query
            if (reslt.next()) {
                return reslt.getString(1);
            }
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    //Function that returns all the exams
    public String[][] getAllExams() {
        //Try block to add the repsonse to the comment
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT ModuleCode, examID, ExamPeriod, ExamLevel, Semester, Year FROM exam ORDER BY Semester, examID ASC;");

            int rows = 0;
            if (reslt.last()) {
                rows = reslt.getRow();
                reslt.beforeFirst();
            }

            String[][] list = new String[rows][6];
            int i = 0;
            //return string from query
            while (reslt.next()) {
                list[i][0] = reslt.getString("ModuleCode");
                list[i][1] = reslt.getString("examID");
                list[i][2] = reslt.getString("ExamPeriod");
                list[i][3] = reslt.getString("ExamLevel");
                list[i][4] = reslt.getString("Semester");
                list[i][5] = reslt.getString("Year");
                i++;
            }
            return list;
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }    
    
    //Function that returns all the exams that are not yet assigned.
    public String[][] getAllUnassignedExams() {
        //Try block to add the repsonse to the comment
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT exam.ModuleCode, exam.examID FROM exam LEFT JOIN assignedexams ON exam.examID = assignedexams.AssignedExamID WHERE assignedexams.ModuleCode IS NULL;");

            int rows = 0;
            if (reslt.last()) {
                rows = reslt.getRow();
                reslt.beforeFirst();
            }

            String[][] list = new String[rows][2];
            int i = 0;
            //return string from query
            while (reslt.next()) {
                list[i][0] = reslt.getString("ModuleCode");
                list[i][1] = reslt.getString("examID");
                i++;
            }
            return list;
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    
    public boolean createAssignedExam(int examID, String moduleCode, String examPeriod, String examLevel, int examSetter)
    {
        int success = 0;
                   
        try
        {
            stmt = conn.createStatement();            
            System.out.println("INSERT INTO assignedexams (AssignedExamID, ModuleCode, ExamPeriod, ExamLevel, ExamSetter), VALUES (" + examID + ", '" + moduleCode + "', '" + examPeriod + "', '" + examLevel + "', " + examSetter +");");
            success = stmt.executeUpdate("INSERT INTO assignedexams (AssignedExamID, ModuleCode, ExamPeriod, ExamLevel, ExamSetter) VALUES (" + examID + ", '" + moduleCode + "', '" + examPeriod + "', '" + examLevel + "', " + examSetter +");");
        }
        catch (SQLException e)
        {    
            System.out.println("Error: " + e);
            return false;
        }

        if (success == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean allocateExams(String[] examIDs, int internal, int external, int vet)
    {
        int success = 0;
        
        for (int i = 0; i < examIDs.length; i++)
        {            
            try
            {
                stmt = conn.createStatement();
                success = stmt.executeUpdate("UPDATE assignedexams SET InternalModerator = " + internal + ", ExternalExaminer = " + external + ", ExamVettingComittee = " + vet + " WHERE AssignedExamID = " + examIDs[i] + ";");
            }
            catch (SQLException e)
            {    
                System.out.println("Error: " + e);
                return false;
            }
        }
        if (success == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //Gets all exam setters
    public String[][] getAllSetters() {
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT UserID, FirstName, Surname FROM user WHERE ExamSetter = 1;");

            int rows = 0;
            if (reslt.last()) {
                rows = reslt.getRow();
                reslt.beforeFirst();
            }
            CompletedRows = rows;
            String[][] list = new String[rows][3];
            int i = 0;
            while (reslt.next()) {
                list[i][0] = reslt.getString("UserID");
                list[i][1] = reslt.getString("FirstName");
                list[i][2] = reslt.getString("Surname");
                i++;
            }

            if (list != null) {
                return list;
            } else {
                return null;
            }
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
        return null;
    }
    
    //Gets all internal moderators
    public String[][] getAllInternalMods() {
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT UserID, FirstName, Surname FROM user WHERE InternalModerator = 1 ORDER BY Surname;");

            int rows = 0;
            if (reslt.last()) {
                rows = reslt.getRow();
                reslt.beforeFirst();
            }
            CompletedRows = rows;
            String[][] list = new String[rows][3];
            int i = 0;
            while (reslt.next()) {
                list[i][0] = reslt.getString("UserID");
                list[i][1] = reslt.getString("FirstName");
                list[i][2] = reslt.getString("Surname");
                i++;
            }

            if (list != null) {
                return list;
            } else {
                return null;
            }
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
        return null;
    }
    
    //Gets all external examiners
    public String[][] getAllExternalExam() {
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT UserID, FirstName, Surname FROM user WHERE ExternalExaminer = 1 ORDER BY Surname;");

            int rows = 0;
            if (reslt.last()) {
                rows = reslt.getRow();
                reslt.beforeFirst();
            }
            CompletedRows = rows;
            String[][] list = new String[rows][3];
            int i = 0;
            while (reslt.next()) {
                list[i][0] = reslt.getString("UserID");
                list[i][1] = reslt.getString("FirstName");
                list[i][2] = reslt.getString("Surname");
                i++;
            }

            if (list != null) {
                return list;
            } else {
                return null;
            }
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
        return null;
    }
    
    //Gets all exam vetters
    public String[][] getAllExamVets() {
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT UserID, FirstName, Surname FROM user WHERE ExamVettingComittee = 1 ORDER BY Surname;");

            int rows = 0;
            if (reslt.last()) {
                rows = reslt.getRow();
                reslt.beforeFirst();
            }
            CompletedRows = rows;
            String[][] list = new String[rows][3];
            int i = 0;
            while (reslt.next()) {
                list[i][0] = reslt.getString("UserID");
                list[i][1] = reslt.getString("FirstName");
                list[i][2] = reslt.getString("Surname");
                i++;
            }

            if (list != null) {
                return list;
            } else {
                return null;
            }
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
        return null;
    }
    
    //Function that returns the comment for a given comment ID.
    public String getExamComment(int commentID) {
        //Try block to add the repsonse to the comment
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT Comment FROM comment WHERE CommentID = " + commentID + ";");

            //return string from query
            if (reslt.next()) {
                return reslt.getString(1);
            }
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    
    //Function that returns all comments for a given exam
    public String[] getAllExamComment(int examID) {
        //Try block to add the repsonse to the comment
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT Comment FROM comment WHERE examID = " + examID + ";");

            int rows = 0;
            if (reslt.last()) {
                rows = reslt.getRow();
                reslt.beforeFirst();
            }

            String[] list = new String[rows];
            int i = 0;
            //return string from query
            while (reslt.next()) {
                list[i] = reslt.getString("Comment");
                i++;
            }
            return list;
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
  
    //Function gets all responses from an exam
    public String[][] getAllResponse(int examID) {
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT responce.CommentID, responce.Responce, responce.ResponceTimestamp FROM comment INNER JOIN exam ON exam.ExamID = comment.ExamID INNER JOIN responce ON comment.CommentID = responce.CommentID  WHERE exam.ExamID = " + examID + ";");
        
            int rows = 0;
            
            if (reslt.last()) {
                rows = reslt.getRow();
                reslt.beforeFirst();
            }
            String[][]list1 = new String[rows][3];
            int i = 0;
            
            while (reslt.next()) {
                list1[i][0] = reslt.getString("CommentID");
                list1[i][1] = reslt.getString("Responce");
                list1[i][2] = reslt.getString("ResponceTimeStamp");
                i++;
            }
            if (list1 != null){
                return list1;
            } else {
                return null;
            }
            
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    //Function that adds a comment to an exam in the database
    public boolean setComment(int examID, int userID, String newComment) {
        //Try block to add the repsonse to the comment
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat (" dd/MM/yyyy hh:mm");
        String timeStamp = ft.format(dNow);
        try {
            stmt = conn.createStatement();
            int success = stmt.executeUpdate("INSERT INTO comment (ExamID, UserID, Comment, CommentTimeStamp) VALUES ( " + examID + ", " + userID + ", '" + newComment + "', '" + timeStamp +"');");

            //return true if success, false otherwise
            if (success != 0) {
                return true;
            } else {
                return false;
            }
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return false;
    }

    //Function that adds a response to a comment in the database
    public boolean setCommentResponse(int commentID, String newResponse) {
        //Try block to add the repsonse to the comment
        try {
            stmt = conn.createStatement();
            int success = stmt.executeUpdate("UPDATE comment SET Response = '" + newResponse + "' WHERE CommentID = " + commentID + ";");

            //return true if success, false otherwise
            if (success != 0) {
                return true;
            } else {
                return false;
            }
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return false;
    }
        
    public boolean DeleteAccount(String UserID) {
        //Try block to add the repsonse to the comment
        try {
            stmt = conn.createStatement();
            int success = stmt.executeUpdate("DELETE FROM user WHERE UserID = '" + UserID + "';"); 
                
            //return true if success, false otherwise
            if (success == 0) {
                return false;
            } else {
                return true;
            }
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return false;
    }
        
    public boolean SetDeadline(String Role, String Date) {
        //Try block to add the repsonse to the comment
        try {
            stmt = conn.createStatement();
            int success = stmt.executeUpdate("UPDATE deadline SET Date = '" + Date + "' WHERE Role = '" + Role + "';"); 
                
            //return true if success, false otherwise
            if (success == 0) {
                return false;
            } else {
                return true;
            }
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return false;
    }
        
        public boolean UpdateAccount(String UserID, String FirstName, String SurName, String Email, String Password) {
        //Try block to add the repsonse to the comment
        try {
            stmt = conn.createStatement();
            int success = stmt.executeUpdate("UPDATE user SET FirstName = '" + FirstName + "', Surname ='"+ SurName + "', Email = '" + Email +"', Password = '"+ Password + "' WHERE UserID = '" + UserID + "';");
                
            //return true if success, false otherwise
            if (success == 0) {
                return false;
            } else {
                return true;
            }
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return false;
    }
    
    
    
    public boolean CreateAccount(String UserID, String FirstName, String SurName, String Email, String Password, int es,int im, int em,int evc,int so,int leo) {
        //Try block to add the repsonse to the comment
        try {
            stmt = conn.createStatement();
            int success = stmt.executeUpdate("INSERT INTO user (UserID, FirstName, Surname, Email, Password, ExamSetter, InternalModerator, ExternalExaminer, ExamVettingComittee, SchoolOffice,LocalExamOfficer) VALUES ('" + UserID + "','" + FirstName + "', '" + SurName + "', '" + Email + "', '" + Password + "', '" + es + "', '" + im + "', '" + em + "', '" + evc + "', '" + so + "', '" + leo + "');");
                
            //return true if success, false otherwise
            if (success == 0) {
                return false;
            } else {
                return true;
            }
        } //Catch block for errors with SQL
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return false;
    }

    public String[][] getCompletedExams() {
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT * FROM exam WHERE Status = 'Completed' ORDER BY Semester, Status;");

            int rows = 0;
            if (reslt.last()) {
                rows = reslt.getRow();
                reslt.beforeFirst();
            }
            CompletedRows = rows;
            String[][] completedExams = new String[rows][13];
            int i = 0;
            while (reslt.next()) {
                completedExams[i][0] = reslt.getString("ExamID");
                completedExams[i][1] = reslt.getString("Title");
                completedExams[i][2] = reslt.getString("School");
                completedExams[i][3] = reslt.getString("ModuleCoordinator");
                completedExams[i][4] = reslt.getString("ModuleCode");
                completedExams[i][5] = reslt.getString("ExamType");
                completedExams[i][6] = reslt.getString("ExamPeriod");
                completedExams[i][7] = reslt.getString("ExamLevel");
                completedExams[i][8] = reslt.getString("Semester");
                completedExams[i][9] = reslt.getString("Year");
                completedExams[i][10] = reslt.getString("Status");
                completedExams[i][11] = reslt.getString("ExamPaper");
                completedExams[i][12] = reslt.getString("SolutionsPaper");
                i++;
            }

            if (completedExams != null) {
                return completedExams;
            } else {
                return null;
            }
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
        return null;
    }

    public String deadline(String role) {
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT Date FROM deadline WHERE Role = '" + role + "' ;");

            String deadlineDate = null;

            reslt.next();
            deadlineDate = reslt.getString("Date");

            if (deadlineDate != null) {
                return deadlineDate;
            } else {
                return null;
            }
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
        return null;
    }
    
    public String[][] getExamList(String ModuleCoordinator) {
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ExamID,Title,ModuleCode FROM exam WHERE ModuleCoordinator = '" + ModuleCoordinator + "' ORDER BY ExamID;");

            int row = 0;
            if (rs.last()) {
                row = rs.getRow();
                rs.beforeFirst();
            }
            CompletedRowss = row;
            String[][] staffExams = new String[row][4];
            int j = 0;
            while (rs.next()) {
                staffExams[j][0] = Integer.toString(rs.getInt("ExamID"));
                staffExams[j][1] = rs.getString("Title");
                staffExams[j][2] = ModuleCoordinator;
                staffExams[j][3] = rs.getString("ModuleCode");
                j++;
            }
            if (staffExams != null) {
                return staffExams;
            } else {
                System.out.println("The return is Null");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    
    public String[][] getExamLists(String role) {
        ResultSet rs;
        ResultSet commentCheckResult = null;
        ResultSet externalExaminer = null;
        ResultSet vettingCommittee = null;
        ResultSet internalModerator = null;
        String userID = LoginCheckClass.userID; 
        try {
            Statement satemnt = conn.createStatement();
            Statement commentCheck = conn.createStatement();
            Statement external = conn.createStatement();
            Statement internal = conn.createStatement();
            Statement vetting = conn.createStatement();

            switch (role) {
                case "Internal Moderator": {
                    rs = satemnt.executeQuery("SELECT ExamID,exam.Title,exam.ModuleCode,exam.ModuleCoordinator, assignedexams.ExternalExaminer, assignedexams.ExamVettingComittee FROM exam INNER JOIN assignedexams ON exam.ExamID = assignedexams.AssignedExamID WHERE assignedexams.InternalModerator = '" + LoginCheckClass.userID + "' ;");
                    commentCheckResult = commentCheck.executeQuery("SELECT exam.ExamID FROM exam INNER JOIN assignedexams ON exam.ExamID = assignedexams.AssignedExamID INNER JOIN comment ON exam.ExamID = comment.ExamID  WHERE assignedexams.InternalModerator = '" + LoginCheckClass.userID + "' AND assignedexams.InternalModerator = comment.userID ;");
                    break;
                }
                case "External Examiner": {
                    rs = satemnt.executeQuery("SELECT exam.ExamID,exam.Title,exam.ModuleCode,exam.ModuleCoordinator, assignedexams.InternalModerator, assignedexams.ExamVettingComittee FROM exam INNER JOIN assignedexams ON exam.ExamID = assignedexams.AssignedExamID INNER JOIN comment ON exam.ExamID = comment.ExamID  WHERE assignedexams.ExternalExaminer = '" + LoginCheckClass.userID + "' AND assignedexams.ExamVettingComittee = comment.userID ;");
                    commentCheckResult = commentCheck.executeQuery("SELECT exam.ExamID FROM exam INNER JOIN assignedexams ON exam.ExamID = assignedexams.AssignedExamID INNER JOIN comment ON exam.ExamID = comment.ExamID  WHERE assignedexams.ExternalExaminer = '" + LoginCheckClass.userID + "' AND assignedexams.ExternalExaminer = comment.userID ;");
                    break;
                }
                case "Exam Vetting Comittee": {
                    rs = satemnt.executeQuery("SELECT exam.ExamID,exam.Title,exam.ModuleCode,exam.ModuleCoordinator, assignedexams.InternalModerator, assignedexams.ExternalExaminer FROM exam INNER JOIN assignedexams ON exam.ExamID = assignedexams.AssignedExamID INNER JOIN comment ON exam.ExamID = comment.ExamID  WHERE assignedexams.ExamVettingComittee = '" + userID + "' AND assignedexams.InternalModerator = comment.userID ;");
                    commentCheckResult = commentCheck.executeQuery("SELECT exam.ExamID FROM exam INNER JOIN assignedexams ON exam.ExamID = assignedexams.AssignedExamID INNER JOIN comment ON exam.ExamID = comment.ExamID  WHERE assignedexams.ExamVettingComittee = '" + userID + "' AND assignedexams.ExamVettingComittee = comment.userID ;");
                    break;
                }
                default:
                    return null;
            }

            //ResultSet rs = stmt.executeQuery("SELECT ExamID,Title,ModuleCode FROM exam WHERE ModuleCoordinator = '" + ModuleCoordinator + "' ;");
            int row = 0;
            if (rs.last()) {
                if(commentCheckResult.last()){
                    row = rs.getRow() - commentCheckResult.getRow();
                }else{
                    row = rs.getRow();
                }
                rs.beforeFirst();
                commentCheckResult.beforeFirst();
            }
            CompletedRowss = row;
            String[][] staffExams = new String[row][7];
            int j = 0;
            while (rs.next()) {
                switch (role) {
                    case "Internal Moderator": {

                        externalExaminer = external.executeQuery("SELECT FirstName, Surname FROM user WHERE UserID = '" + rs.getInt("ExternalExaminer") + "' ;");
                        vettingCommittee = vetting.executeQuery("SELECT FirstName, Surname FROM user WHERE UserID = '" + rs.getInt("ExamVettingComittee") + "' ;");
                        break;
                    }
                    case "External Examiner": {

                        internalModerator = internal.executeQuery("SELECT FirstName, Surname FROM user WHERE UserID = '" + rs.getString("InternalModerator") + "' ;");
                        vettingCommittee = vetting.executeQuery("SELECT FirstName, Surname FROM user WHERE UserID = '" + rs.getString("ExamVettingComittee") + "' ;");
                        break;
                    }
                    case "Exam Vetting Comittee": {

                        internalModerator = internal.executeQuery("SELECT FirstName, Surname FROM user WHERE UserID = '" + rs.getString("InternalModerator") + "' ;");
                        externalExaminer = external.executeQuery("SELECT FirstName, Surname FROM user WHERE UserID = '" + rs.getString("ExternalExaminer") + "' ;");
                        break;
                    }
                    default:
                        return null;
                }

                switch (role) {
                    case "Internal Moderator": {
                        if(commentCheckResult.next()){
                            if(commentCheckResult.getInt("ExamID") == rs.getInt("ExamID")){
                                j--;
                                break;
                            }
                        }else{
                            commentCheckResult.previous();
                            if(commentCheckResult.getInt("ExamID") == rs.getInt("ExamID")){
                                j--;
                                break;
                            }
                        }
                        externalExaminer.next();
                        vettingCommittee.next();
                        staffExams[j][0] = Integer.toString(rs.getInt("ExamID"));
                        staffExams[j][1] = rs.getString("Title");
                        staffExams[j][2] = rs.getString("ModuleCode");
                        staffExams[j][3] = rs.getString("ModuleCoordinator");
                        if(rs.getString("ExternalExaminer")!=null){
                            staffExams[j][4] = externalExaminer.getString("FirstName") + " " + externalExaminer.getString("Surname");
                        }else{
                            staffExams[j][4] = "Not Assigned";
                        }
                          if(rs.getString("ExamVettingComittee")!=null){
                             staffExams[j][5] = vettingCommittee.getString("FirstName") + " " + vettingCommittee.getString("Surname");
                        }else{
                            staffExams[j][5] = "Not Assigned";
                        }
                     
                        break;
                    }
                    case "External Examiner": {
                        if(commentCheckResult.next()){
                            if(commentCheckResult.getInt("ExamID") == rs.getInt("ExamID")){
                                j--;
                                break;
                            }
                        }else{
                            commentCheckResult.previous();
                            if(commentCheckResult.getInt("ExamID") == rs.getInt("ExamID")){
                                j--;
                                break;
                            }
                        }
                        internalModerator.next();
                        vettingCommittee.next();
                        staffExams[j][0] = Integer.toString(rs.getInt("ExamID"));
                        staffExams[j][1] = rs.getString("Title");
                        staffExams[j][2] = rs.getString("ModuleCode");
                        staffExams[j][3] = rs.getString("ModuleCoordinator");
                           if(rs.getString("InternalModerator")!=null){
                            staffExams[j][4] = internalModerator.getString("FirstName") + " " + internalModerator.getString("Surname");
                        }else{
                            staffExams[j][4] = "Not Assigned";
                        }
                           if(rs.getString("ExamVettingComittee")!=null){
                             staffExams[j][5] = vettingCommittee.getString("FirstName") + " " + vettingCommittee.getString("Surname");
                        }else{
                            staffExams[j][5] = "Not Assigned";
                        }
                        break;
                    }
                    case "Exam Vetting Comittee": {
                        if(commentCheckResult.next()){
                            if(commentCheckResult.getInt("ExamID") == rs.getInt("ExamID")){
                                j--;
                                break;
                            }
                        }else{
                            commentCheckResult.previous();
                            if(commentCheckResult.getInt("ExamID") == rs.getInt("ExamID")){
                                j--;
                                break;
                            }
                        }
                           internalModerator.next();
                            externalExaminer.next();
                        staffExams[j][0] = Integer.toString(rs.getInt("ExamID"));
                        staffExams[j][1] = rs.getString("Title");
                        staffExams[j][2] = rs.getString("ModuleCode");
                        staffExams[j][3] = rs.getString("ModuleCoordinator");
                          if(rs.getString("InternalModerator")!=null){
                            staffExams[j][4] = internalModerator.getString("FirstName") + " " + internalModerator.getString("Surname");
                        }else{
                            staffExams[j][4] = "Not Assigned";
                        }
                   
                       if(rs.getString("ExternalExaminer")!=null){
                            staffExams[j][5] = externalExaminer.getString("FirstName") + " " + externalExaminer.getString("Surname");
                        }else{
                            staffExams[j][5] = "Not Assigned";
                        }
                        break;
                    }
                    default:
                        return null;
                }
                j++;
            }
            if (staffExams != null) {
                return staffExams;
            } else {
                System.out.println("The return is Null");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    
    public boolean checkForExternalExam(int examID, int userID){
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT AssignedExamID FROM assignedexams WHERE AssignedExamID = " + examID + " AND ExternalExaminer = " + userID +" ;");
            
            if (reslt.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
        return false;
    }
    
    public boolean markExamCompleted(int examID){
        try {
            stmt = conn.createStatement();
            int success = stmt.executeUpdate("UPDATE exam SET status = 'Completed' WHERE ExamID = " + examID + " ;");
            
            if(success != 0){
                return true;
            }else{
                return false;
            }
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
        return false;
    }
    
    public String[][] getFullySignedExams(){
        ResultSet commentexamrs = null;
        Statement commentexamstmt = null;
        try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT exam.* FROM exam INNER JOIN assignedexams ON exam.ExamID = assignedexams.AssignedExamID WHERE assignedexams.ExamSetter = " + LoginCheckClass.userID + " AND exam.status != 'Completed' ;");
            
            int row = 0;
            if (reslt.last()) {
                row = reslt.getRow();
                reslt.beforeFirst();
            }
            CompletedRowss = row;
            String[][] staffExams = new String[row][13];
            int j = 0;
            
            while (reslt.next()) {
                int examID = reslt.getInt("ExamID");
                commentexamstmt = conn.createStatement();
                commentexamrs = commentexamstmt.executeQuery("SELECT ExamID FROM comment WHERE ExamID = " + examID  + " ;");
                if (commentexamrs.last()) {
                row = commentexamrs.getRow();
                commentexamrs.beforeFirst();
                }
                if(row >= 3){
                    staffExams[j][0] = reslt.getString("ExamID");
                    staffExams[j][1] = reslt.getString("Title");
                    staffExams[j][2] = reslt.getString("School");
                    staffExams[j][3] = reslt.getString("ModuleCoordinator");
                    staffExams[j][4] = reslt.getString("ModuleCode");
                    staffExams[j][5] = reslt.getString("ExamType");
                    staffExams[j][6] = reslt.getString("ExamPeriod");
                    staffExams[j][7] = reslt.getString("ExamLevel");
                    staffExams[j][8] = reslt.getString("Semester");
                    staffExams[j][9] = reslt.getString("Year");
                    staffExams[j][10] = reslt.getString("Status");
                    staffExams[j][11] = reslt.getString("ExamPaper");
                    staffExams[j][12] = reslt.getString("SolutionsPaper");
                    j++;
                }
                
            }
            CompletedRowss = j;
            return staffExams;
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
        return null;
    }

    public String getName(String id){
      try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT FirstName FROM user WHERE UserID = '" + id + "' ;");

            String name = null;

            reslt.next();
            name = reslt.getString("FirstName");

            if (name != null) {
                return name;
            } else {
                return null;
            }
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
        return null;
    }   
    
    public Blob getExamPaper(String id){
      try {
            stmt = conn.createStatement();
            reslt = stmt.executeQuery("SELECT ExamPaper FROM exam WHERE ExamID = '" + id + "' ;");

            Blob exam = null;

            reslt.next();
            exam = reslt.getBlob("ExamPaper");

            if (exam != null) {
                return exam;
            } else {
                return null;
            }
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }
        return null;
    }
    
    public boolean createOldVersion(int examID){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat (" dd/MM/yyyy hh:mm:ss");
        String timeStamp = ft.format(dNow);
      try {
            stmt = conn.createStatement();
            int success = stmt.executeUpdate("Insert INTO oldexams (oldexams.ExamID, oldexams.Title, oldexams.School, oldexams.ModuleCoordinator, oldexams.ModuleCode, oldexams.ExamType, oldexams.ExamPeriod, oldexams.ExamLevel,  oldexams.Semester, oldexams.Year, oldexams.Status, oldexams.ExamPaper, oldexams.SolutionsPaper, oldexams.TimeStamp) (SELECT exam.ExamID, exam.Title, exam.School, exam.ModuleCoordinator, exam.ModuleCode, exam.ExamType, exam.ExamPeriod, exam.ExamLevel,  exam.Semester, exam.Year, exam.Status, exam.ExamPaper, exam.SolutionsPaper, '" + timeStamp + "' FROM exam WHERE exam.examID = " + examID +" );");

            if(success == 1){
                return true;
            }else{
                System.out.println("Exam Does Not Exist");
                return false;
            }
        } catch (SQLException exc) {
            System.out.println("Error: " + exc);
        }        
        return false;
    }
  
    public String[][] getOldExams() {
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT oldexams.ExamID,oldexams.TimeStamp,oldexams.Title,oldexams.School,oldexams.ModuleCoordinator,oldexams.ModuleCode,oldexams.ExamType,oldexams.ExamPeriod,oldexams.ExamLevel,oldexams.Semester,oldexams.Year FROM oldexams INNER JOIN assignedexams ON oldexams.ExamID = assignedexams.AssignedExamID WHERE assignedexams.ExamSetter = '" + LoginCheckClass.userID + "' ORDER BY `ExamID` ASC, `TimeStamp` DESC ;");

            int row = 0;
            if (rs.last()) {
                row = rs.getRow();
                rs.beforeFirst();
            }
            CompletedRowsss = row;
            String[][] oldExams = new String[row][11];
            int j = 0;
            while (rs.next()) {
                oldExams[j][0] = rs.getString("ExamID");
                oldExams[j][1] = rs.getString("TimeStamp");
                oldExams[j][2] = rs.getString("Title");
                oldExams[j][3] = rs.getString("School");
                oldExams[j][4] = rs.getString("ModuleCoordinator");
                oldExams[j][5] = rs.getString("ModuleCode");
                oldExams[j][6] = rs.getString("ExamType");
                oldExams[j][7] = rs.getString("ExamPeriod");
                oldExams[j][8] = rs.getString("ExamLevel");
                oldExams[j][9] = rs.getString("Semester");
                oldExams[j][10] = rs.getString("Year");
                j++;
            }
            if (oldExams != null) {
                return oldExams;
            } else {
                System.out.println("The query returned no results");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }    
    
}
