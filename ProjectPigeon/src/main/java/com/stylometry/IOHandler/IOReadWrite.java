package com.stylometry.IOHandler;

import com.stylometry.controller.ClusterCommons;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.stylometry.model.Alias;
import com.stylometry.model.Posts;
import com.stylometry.model.ReturnSortedUserList;
import com.stylometry.model.User;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author ITE
 */
public class IOReadWrite {

    public IOReadWrite() {
    }

    public void writeToFile(String fileName, String content) throws IOException {
        String getUserFolderName = getFolderName(fileName);
        CreateDirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH, getUserFolderName);
        String fileLocation = IOProperties.INDIVIDUAL_USER_FILE_PATH + getUserFolderName;
        String tempfileName = fileName + IOProperties.USER_FILE_EXTENSION;
        String completeFileNameNPath = fileLocation + "/" + tempfileName;
        File file = new File(completeFileNameNPath);
        file.createNewFile();
        try (PrintWriter out = new PrintWriter(new FileWriter(completeFileNameNPath, true))) {
            out.append(content + IOProperties.DATA_SEPERATOR);
        }
    }

    /**
     * This method merges the content of two files
     *
     * @param newUser
     * @param existingUser
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void mergeUserPostTime(String newUser, String existingUser) throws FileNotFoundException, IOException {
        String getUserFolderName = getFolderName(existingUser);
        String existingUserFileLocation = IOProperties.All_YEAR_FILES_BASE_PATH + getUserFolderName;
        String existingUserFileName = existingUser + IOProperties.USER_FILE_EXTENSION;
        String existingUserFilePath = existingUserFileLocation + "/" + existingUserFileName;

        String newUserFileLocation = IOProperties.INDIVIDUAL_USER_FILE_PATH + getUserFolderName;
        String newUserFileName = newUser + IOProperties.USER_FILE_EXTENSION;
        String newUserFilePath = newUserFileLocation + "/" + newUserFileName;

        File existingUserFile = new File(existingUserFilePath);
        File newUserFile = new File(newUserFilePath);

        FileReader newFileReader = null;
        FileWriter newFileWriter = null;

        newFileReader = new FileReader(newUserFile);
        newFileWriter = new FileWriter(existingUserFile, true);

        int newFileContent = newFileReader.read();
        try {
            while (newFileContent != -1) {
                newFileWriter.write(newFileContent);
                newFileContent = newFileReader.read();
            }
        } catch (IOException e) {
        } finally {
            try {
                newFileReader.close();
            } catch (IOException e) {
            }
            try {
                newFileWriter.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * This method copy file from one directory to another
     *
     * @param fileName
     */
    public void copyFile(String fileName) {
        try {
            String getUserFolderName = getFolderName(fileName);
            String newUserFileLocation = IOProperties.INDIVIDUAL_USER_FILE_PATH + getUserFolderName;
            String newUserFileName = fileName + IOProperties.USER_FILE_EXTENSION;
            String newUserFilePath = newUserFileLocation + "/" + newUserFileName;

            String existingUserFileLocation = IOProperties.All_YEAR_FILES_BASE_PATH + getUserFolderName;
            String existingUserFilePath = existingUserFileLocation + "/" + newUserFileName;

            Path original = Paths.get(newUserFilePath);
            Path destination = Paths.get(existingUserFilePath);

            Files.copy(original, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(IOReadWrite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteFile(String fileName) {
        String getUserFolderName = getFolderName(fileName);
        String newUserFileName = fileName + IOProperties.USER_FILE_EXTENSION;
        String existingUserFileLocation = IOProperties.All_YEAR_FILES_BASE_PATH + getUserFolderName;
        String existingUserFilePath = existingUserFileLocation + "/" + newUserFileName;
        File file = new File(existingUserFilePath);
        file.delete();
    }

    public String getFolderName(String userId) {
        String folderName = "";
        int userID = Integer.valueOf(userId);

        if (userID > 0 && userID <= 25000) {
            folderName = "25K";
        } else if (userID > 25000 && userID <= 50000) {
            folderName = "50K";
        } else if (userID > 50000 && userID <= 100000) {
            folderName = "100K";
        } else if (userID > 100000 && userID <= 150000) {
            folderName = "150K";
        } else if (userID > 150000 && userID <= 200000) {
            folderName = "200K";
        } else if (userID > 200000 && userID <= 250000) {
            folderName = "250K";
        } else if (userID > 300000 && userID <= 350000) {
            folderName = "300K";
        } else if (userID > 350000 && userID <= 400000) {
            folderName = "350K";
        } else if (userID > 400000 && userID <= 450000) {
            folderName = "400K";
        } else if (userID > 450000 && userID <= 500000) {
            folderName = "450K";
        }
        return folderName;
    }

    public void CreateDirectory(String path, String folderName) {
        File directory = new File(path + "/" + folderName); //for mac use / and for windows use "\\"
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /*
     * Delete directory if it exists and will create new
     */
    public void checkAndCreateDirectory(String path, String folderName) {
        File directory = new File(path + "/" + folderName); //for mac use / and for windows use "\\"
        if (directory.exists()) {
            deleteDir(directory);
        }
        directory.mkdirs();
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete(); // The directory is empty now and can be deleted.
    }

    public File checkAndCreateFile(String fileName) throws IOException {
        File file = new File(fileName);
        /* if (file.exists()) {
         file.delete();
         }*/
        file.createNewFile();
        return file;
    }

    public File CreateFile(String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        return file;
    }

    public List<String> getAllDirectories(String basePath) {
        File file = new File(basePath);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });
        //System.out.print(Arrays.toString(directories));
        return Arrays.asList(directories);
    }

    public String readTxtFileAsString(String basePath, String directoryName, String fileName, String extension) throws FileNotFoundException, IOException {

        StringBuilder stringBuilder = new StringBuilder();
        File file;
        BufferedReader reader = null;
        try {
            file = new File(basePath + directoryName + "/" + fileName + extension);
            reader = new BufferedReader(new FileReader(file));
            String line = null;

            if (file.exists()) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        } finally {
            reader.close();
        }
        String a = stringBuilder.substring(0, (stringBuilder.length() - (IOProperties.DATA_SEPERATOR).length()));
        return a;
    }

    public String readTxtFileAsString(String basePath, String fileName, String extension)
            throws FileNotFoundException, IOException {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            File file = new File(basePath + "/" + fileName + extension);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            if (file.exists()) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        }
        String a = stringBuilder.substring(0, (stringBuilder.length() - (IOProperties.DATA_SEPERATOR).length()));
        return a;
    }

    /*public String readTxtFileAsString(List<String> document) throws FileNotFoundException, IOException {

     StringBuilder stringBuilder = new StringBuilder();
     try {
     File file = new File(document);
     BufferedReader reader = new BufferedReader(new FileReader(file));
     String line = null;
     if (file.exists()) {
     while ((line = reader.readLine()) != null) {
     stringBuilder.append(line);
     }
     }
     } catch (FileNotFoundException ex) {
     throw ex;
     } catch (IOException ex) {
     throw ex;
     }
     String a = stringBuilder.substring(0, (stringBuilder.length() - (IOProperties.DATA_SEPERATOR).length())).toString();
     return a;
     }*/
    public User convertTxtFileToUserObj(String basePath, String directoryName, String fileName, String extension) throws FileNotFoundException, IOException {
        String userPostAsString = readTxtFileAsString(basePath, directoryName, fileName, extension);
        String temp[];
        User user = new User();
        List postList = new ArrayList();
        user.setId(Integer.valueOf(fileName));
        if (userPostAsString.contains(IOProperties.DATA_SEPERATOR)) {
            temp = userPostAsString.split(IOProperties.DATA_SEPERATOR);
        } else {
            temp = new String[1];
            temp[0] = userPostAsString;

        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")
                    || temp[i].length() == 8) {
                temp[i] = temp[i] + "  ";
            }
            Posts posts = new Posts();
            String time = temp[i].substring(0, 8);
            String date = temp[i].substring(9, 19);
            String content = temp[i].substring(20);
            if (time.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                posts.setTime(time);
                posts.setDate(date);
                posts.setContent(content);
                postList.add(posts);
            } else {
            }
        }
        user.setUserPost(postList);
        return user;
    }

    public User convertTxtFileToUserObjSixMonthData(String basePath, String directoryName, String fileName, String extension) throws FileNotFoundException, IOException {
        String userPostAsString = readTxtFileAsString(basePath, directoryName, fileName, extension);
        String temp[];
        User user = new User();
        List postList = new ArrayList();
        user.setId(Integer.valueOf(fileName));
        if (userPostAsString.contains(IOProperties.DATA_SEPERATOR)) {
            temp = userPostAsString.split(IOProperties.DATA_SEPERATOR);
        } else {
            temp = new String[1];
            temp[0] = userPostAsString;

        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")
                    || temp[i].length() == 8) {
                temp[i] = temp[i] + "  ";
            }
            Posts posts = new Posts();
            String time = temp[i].substring(0, 8);
            String date = temp[i].substring(9, 19);
            if (time.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                String[] tempMonth = date.split("-");
                String month = tempMonth[1];
                int monthOfYear = Integer.parseInt(month);

                if (monthOfYear <= 6) {
                    posts.setTime(time);
                    posts.setDate(date);
//                posts.setContent(temp[i].substring(20, temp[i].length()));
                    postList.add(posts);
                }
                //System.out.println(date);
            } else {
            }
        }
        user.setUserPost(postList);
        return user;
    }

    public User convertDocumentToObj(int userID, String post, String seperator) throws FileNotFoundException, IOException {
        String temp[] = null;
        User user = new User();
        List postList = new ArrayList();
        user.setId(userID);
        if (post.contains(seperator)) {
            temp = post.split(seperator);
        } else {
            temp = new String[1];
            temp[0] = post;

        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")
                    || temp[i].length() == 8) {
                temp[i] = temp[i] + "  ";
            }
            Posts posts = new Posts();
            String date = temp[i].substring(0, 8);
            if (date.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                posts.setTime(date);
                posts.setContent(temp[i].substring(9, temp[i].length()));
                postList.add(posts);
            } else {
            }
        }
        user.setUserPost(postList);
        return user;
    }

    public List<User> getAllDocumentObj(List<String> document, String seperator) throws FileNotFoundException, IOException {
        List<User> userList = new ArrayList();
        for (int i = 0; i < document.size(); i++) {
            int ID = i + 1;
            User user = convertDocumentToObj(ID, document.get(i), seperator);
            userList.add(user);
        }
        return userList;
    }

    public User convertTxtFileToUserObj(String basePath, String fileName, String extension) throws FileNotFoundException, IOException {
        String userPostAsString = readTxtFileAsString(basePath, fileName, extension);
        String temp[] = null;
        User user = new User();
        List postList = new ArrayList();
        user.setId(Integer.valueOf(fileName));
        if (userPostAsString.contains(IOProperties.DATA_SEPERATOR)) {
            temp = userPostAsString.split(IOProperties.DATA_SEPERATOR);
        } else {
            temp = new String[1];
            temp[0] = userPostAsString;

        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")
                    || temp[i].length() == 8) {
                temp[i] = temp[i] + "  ";
            }
            Posts posts = new Posts();
            String date = temp[i].substring(0, 8);
            if (date.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                posts.setTime(date);
//                posts.setContent(temp[i].substring(9, temp[i].length()));
                posts.setContent("");
                postList.add(posts);
                //System.out.println(date);
            } else {
            }
        }
        user.setUserPost(postList);

        return user;
    }

    public List getAllFilesInADirectory(String directoryName) {
        List returnList = new ArrayList();
        File folder = new File(directoryName);
        File[] listOfFiles = folder.listFiles();
        for (File listOfFile : listOfFiles) {
            String a = listOfFile.getName();
            returnList.add(a.substring(0, a.length() - 4));
        }
        return returnList;
    }

    public int returnNumberofFileInSleepingClusterFolder(String folderPath) {
        int noOfFiles = new File(folderPath).list().length;
        return noOfFiles;
    }

    /**
     * Return users as an object and returns only those users who has posted
     * more than 60 messages in discussion board
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<User> getAllUsersAsObject() throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        System.out.println(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        List directoryList = ioRW.getAllDirectories(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        List allFiles = new ArrayList();
        List allFilesSize;
        for (Object directoryList1 : directoryList) {
            System.out.println(directoryList1);
            allFilesSize = ioRW.getAllFilesInADirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH + directoryList1);

            for (Object allFilesSize1 : allFilesSize) {
                if (allFilesSize1.toString().contains(".DS_S")) {
                    String dsFilePath = IOProperties.INDIVIDUAL_USER_FILE_PATH + directoryList1 + "/" + allFilesSize1.toString();
                    File dsFile = new File(dsFilePath);
                    dsFile.delete();
                } else {
                    User user = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH,
                            directoryList1.toString(), allFilesSize1.toString(), IOProperties.USER_FILE_EXTENSION);
                    /*User user = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH,
                     allFilesSize.get(j).toString(), IOProperties.USER_FILE_EXTENSION);*/
                    /*User user = ioRW.convertTxtFileToUserObjSixMonthData(IOProperties.INDIVIDUAL_USER_FILE_PATH,
                            directoryList1.toString(), allFilesSize1.toString(), IOProperties.USER_FILE_EXTENSION);*/
                    if (user.getUserPost().size() >= 60) {
                        allFiles.add(user);
                    }
                }
            }
        }
        return allFiles;
    }

    /**
     * Return users as an object and returns only those users who has posted
     * more than 60 messages in discussion board
     *
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<User> getAllUsersAsObject(String path) throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        System.out.println(path);
        List directoryList = ioRW.getAllDirectories(path);
        List allUsers = new ArrayList();
        List allFilesSize = new ArrayList();
        for (int i = 0; i < directoryList.size(); i++) {
            allFilesSize = ioRW.getAllFilesInADirectory(path + directoryList.get(i));
//        allFilesSize = ioRW.getAllFilesInADirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH);
            for (int j = 0; j < allFilesSize.size(); j++) {
                User user = ioRW.convertTxtFileToUserObj(path, directoryList.get(i).toString(),
                        allFilesSize.get(j).toString(), IOProperties.USER_FILE_EXTENSION);
                /*User user = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH,
                 allFilesSize.get(j).toString(), IOProperties.USER_FILE_EXTENSION);*/
                if (user.getUserPost().size() >= 60) {
                    allUsers.add(user);
                }
            }
        }
        return allUsers;
    }

    /**
     * return the random list of size numberSize
     *
     * @param numberSize
     * @return
     */
    public List<Integer> returnRandomNumberList(int numberSize) {
        List<Integer> numList = new ArrayList();
        Random rand = new Random();
        int randNum;
        for (int i = 0; i < numberSize; i++) {
            do {
                randNum = rand.nextInt(numberSize);
            } while (numList.contains(randNum));
            numList.add(randNum);
        }
        return numList;
    }

    public List<User> returnUsers() throws IOException {
        String filePath_06 = IOProperties.YEAR_DATA_FILE_PATH_06;
        String filePath_07 = IOProperties.YEAR_DATA_FILE_PATH_07;

        List users_07 = getAllUsersAsObject(filePath_07);
        List users_06 = getAllUsersAsObject(filePath_06);

        return returnCommonUsers(users_07, users_06);
    }

    private List<User> returnCommonUsers(List users_07, List users_06) {
        List<User> commonUsers = new ArrayList();
        List<User> sortedUsers_07 = returnLimitedSortedUser(users_07, 2000);
        List<User> sortedUsers_06 = returnLimitedSortedUser(users_06, 2000);

        for (User sortedUsers_071 : sortedUsers_07) {
            for (User sortedUsers_061 : sortedUsers_06) {
                if (sortedUsers_071.getId() == sortedUsers_061.getId()) {
                    commonUsers.add(sortedUsers_061);
                } else {

                }
            }
        }
        return commonUsers;
    }

    public User getUsersAsObject(int userID) throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        List directoryList = ioRW.getAllDirectories(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        List allFilesSize = new ArrayList();
        User user = new User();
        String tempUserID = String.valueOf(userID);
        for (Object directoryList1 : directoryList) {
            allFilesSize = ioRW.getAllFilesInADirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH + directoryList1);
            for (Object allFilesSize1 : allFilesSize) {
                user = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH, directoryList1.toString(), tempUserID, IOProperties.USER_FILE_EXTENSION);
            }
        }
        return user;
    }

    /**
     * split string into character and returns
     *
     * @param cluster
     * @return
     */
    public List<Integer> returnDigits(String cluster) {
        LinkedList<Integer> digits = new LinkedList<>();
        char[] cArray = cluster.toCharArray();
        for (int i = 0; i < cArray.length; i++) {
            String tempCharacter = String.valueOf(cArray[i]);
            int singleNumber = Integer.valueOf(tempCharacter);
            digits.add(i, singleNumber);
        }
        return digits;
    }

    /**
     * create files with clustering info of each users
     *
     * @param User
     * @param fileName
     * @throws IOException
     */
    public void writeStylometricClusterData(int User, int fileName) throws IOException {
        CreateDirectory(IOProperties.All_ACTIVITY_BASE_PATH, IOProperties.CLUSTER_FOLDER_NAME);
        String completeFileNameNPath = IOProperties.All_ACTIVITY_BASE_PATH + String.valueOf(IOProperties.CLUSTER_FOLDER_NAME)
                + "/" + String.valueOf(fileName)
                + IOProperties.SECOND_ACTIVITY_FILE_EXTENSION;
        File file = CreateFile(completeFileNameNPath);
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file, true))) {
            String toWriteContent = String.valueOf(User);
            output.write(toWriteContent);
            output.newLine();
        }
    }

    /**
     * read clustered files and return of List of object of user
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<String> readClusterData(String fileName) throws FileNotFoundException, IOException {
        File file = new File(fileName);
        List<String> dataList = new ArrayList<>();
        String line = "";
        //removeDuplicateRowsFromFile(file);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                dataList.add(line);
            }
        }
        return dataList;
    }

    /**
     * removes duplicate users from the row
     *
     * @param file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void removeDuplicateRowsFromFile(File file) throws FileNotFoundException, IOException {
        if (file.exists()) {
            Set<String> lines;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                lines = new HashSet<>(100000);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String unique : lines) {
                    writer.write(unique);
                    writer.newLine();
                }
            }
        }
    }

    public Alias convertTxtFileToAliasObj(String basePath, String directoryName, String fileName, String extension) throws FileNotFoundException, IOException {
        String userPostAsString = readTxtFileAsString(basePath, directoryName, fileName, extension);
        String temp[] = null;
        Alias alias = new Alias();
        List<String> postList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        alias.setUserID(fileName);
        if (userPostAsString.contains(IOProperties.DATA_SEPERATOR)) {
            temp = userPostAsString.split(IOProperties.DATA_SEPERATOR);
        } else {
            temp = new String[1];
            temp[0] = userPostAsString;
        }

        for (int i = 0; i < temp.length; i++) {
            if (temp[i].matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")
                    || temp[i].length() == 8) {
                temp[i] = temp[i] + "  ";
            }
            String date = temp[i].substring(0, 8);
            if (date.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                timeList.add(date);
                postList.add(temp[i].substring(9, temp[i].length()));
            } else {
                continue;
            }
        }
        alias.setPostTime(timeList);
        alias.setPostDate(postList);
        alias.setPosts(postList);
        return alias;
    }

    public List<Alias> convertTxtFileToAliasObjAndDivide(int divisionFlag, String basePath, String directoryName,
            String fileName, String extension, List<Alias> aliasList) throws FileNotFoundException, IOException {
        String userPostAsString = readTxtFileAsString(basePath, directoryName, fileName, extension);
        String temp[] = null;
        Alias aliasA = new Alias();
        Alias aliasB = new Alias();
        List<String> postListA = new ArrayList<>();
        List<String> timeListA = new ArrayList<>();
        List<String> postListB = new ArrayList<>();
        List<String> timeListB = new ArrayList<>();
        aliasA.setUserID(fileName);
        aliasB.setUserID(fileName);
        aliasA.setType("A");
        aliasB.setType("B");
        if (userPostAsString.contains(IOProperties.DATA_SEPERATOR)) {
            temp = userPostAsString.split(IOProperties.DATA_SEPERATOR);
        } else {
            temp = new String[1];
            temp[0] = userPostAsString;
        }

        for (int i = 0; i < temp.length; i++) {
            if (temp[i].matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")
                    || temp[i].length() == 8) {
                temp[i] = temp[i] + "  ";
            }
            String date = temp[i].substring(0, 8);
            String content = "";
            if (date.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                content = temp[i].substring(9, temp[i].length());
                if (i % 2 == 0 && divisionFlag == 2) {
                    timeListA.add(date);
                    postListA.add(content);
                } else if (i % 2 != 0) {
                    timeListB.add(date);
                    postListB.add(content);
                }
            } else {
                continue;
            }
        }

        if (divisionFlag == 2) {
            aliasA.setPostTime(timeListA);
            // aliasA.setPosts(postListA);
            aliasList.add(0, aliasA);
        }

        aliasB.setPostTime(timeListB);
        // aliasB.setPosts(postListB);
        aliasList.add(aliasB);

        return aliasList;
    }

    public List<Alias> returnSplitAliasObject(int UserID) throws FileNotFoundException, IOException {

        List<Alias> aliasList = new ArrayList();
        String basePath = IOProperties.INDIVIDUAL_USER_FILE_PATH;
        String filename = String.valueOf(UserID);
        String folderName = getFolderName(filename);
        String extension = IOProperties.USER_FILE_EXTENSION;

        String userPostAsString = readTxtFileAsString(basePath, folderName, filename, extension);

        String temp[] = null;
        Alias aliasA = new Alias();
        Alias aliasB = new Alias();
        List<String> postListA = new ArrayList<>();
        List<String> timeListA = new ArrayList<>();
        List<String> dateListA = new ArrayList<>();

        List<String> postListB = new ArrayList<>();
        List<String> timeListB = new ArrayList<>();
        List<String> dateListB = new ArrayList<>();

        aliasA.setType("A");
        aliasB.setType("B");

        if (userPostAsString.contains(IOProperties.DATA_SEPERATOR)) {
            temp = userPostAsString.split(IOProperties.DATA_SEPERATOR);
        } else {
            temp = new String[1];
            temp[0] = userPostAsString;
        }

        for (int i = 0; i < temp.length; i++) {
            if (temp[i].matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")
                    || temp[i].length() == 8) {
                temp[i] = temp[i] + "  ";
            }
            String time = temp[i].substring(0, 8);
            String content = "";
            String date = "";
            if (time.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                date = temp[i].substring(9, 19);
                content = temp[i].substring(20);
                if (i % 2 == 0) {
                    timeListA.add(time);
                    dateListA.add(date);
                    postListA.add(content);
                } else {
                    timeListB.add(time);
                    dateListB.add(date);
                    postListB.add(content);
                }
            }
        }

        aliasA.setPostTime(timeListA);
        aliasA.setPostDate(dateListA);
        aliasA.setPosts(postListA);
        aliasList.add(aliasA);

        aliasB.setPostTime(timeListB);
        aliasB.setPostDate(dateListB);
        aliasB.setPosts(postListB);
        aliasList.add(aliasB);

        return aliasList;
    }

    public List<User> returnDividedUserForTimeFeat(User user) throws FileNotFoundException, IOException {

        int UserID = user.getId();
        User userA = new User();
        User userB = new User();

        List<User> userList = new ArrayList<>();
        List<Posts> postListA = new ArrayList<>();
        List<Posts> postListB = new ArrayList<>();

        List userPost = user.getUserPost();
        int userPostSize = user.getUserPost().size();
//        int userPostSize = 200;
//        int halfPostSize = userPostSize/2;

        for (int i = 0; i < userPostSize; i++) {
            Posts individualPost = (Posts) userPost.get(i);

            if (i % 2 == 0) {
//            if (i <= halfPostSize && divisionFlag == 2) {
                postListA.add(individualPost);
            } else if (i % 2 != 0) {
//            } else if (i > halfPostSize) {
                postListB.add(individualPost);
            } else {
            }
        }

        userA.setId(UserID);
        userA.setType("A");
        userA.setUserPost(postListA);
        userList.add(0, userA);

        userB.setId(UserID);
        userB.setType("B");
        userB.setUserPost(postListB);
        userList.add(userB);

        return userList;
    }

    public Alias convertTxtFileToAliasObjAndDivide(String basePath, String directoryName,
            String fileName, String extension) throws FileNotFoundException, IOException {
        String userPostAsString = readTxtFileAsString(basePath, directoryName, fileName, extension);
        String temp[] = null;

        Alias aliasB = new Alias();
        List<String> postListB = new ArrayList<>();
        List<String> timeListB = new ArrayList<>();

        aliasB.setUserID(fileName);
        aliasB.setType("B");
        if (userPostAsString.contains(IOProperties.DATA_SEPERATOR)) {
            temp = userPostAsString.split(IOProperties.DATA_SEPERATOR);
        } else {
            temp = new String[1];
            temp[0] = userPostAsString;
        }

        for (int i = 0; i < temp.length; i++) {
            if (temp[i].matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")
                    || temp[i].length() == 8) {
                temp[i] = temp[i] + "  ";
            }
            String date = temp[i].substring(0, 8);
            String content = "";
            if (date.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                content = temp[i].substring(9, temp[i].length());
                if (i % 2 == 0) {
                } else if (i % 2 != 0) {
                    timeListB.add(date);
                    postListB.add(content);
                }
            } else {
                continue;
            }
        }
        aliasB.setPostTime(timeListB);
        aliasB.setPosts(postListB);

        return aliasB;
    }

    /**
     * returns user as an object
     *
     * @param userIDList
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<Alias> getUserAsAlias(List<String> userIDList) throws FileNotFoundException, IOException {

        Alias alias = new Alias();
        List<Alias> aliasList = new ArrayList<>();
        IOReadWrite ioReadWrite = new IOReadWrite();

        for (String userIDList1 : userIDList) {
            alias = ioReadWrite.convertTxtFileToAliasObj(IOProperties.INDIVIDUAL_USER_FILE_PATH,
                    ioReadWrite.getFolderName(userIDList1), userIDList1, IOProperties.USER_FILE_EXTENSION);
            aliasList.add(alias);
        }
        return aliasList;
    }

    public List<User> returnLimitedSortedUser(List<User> userList, int size) {
        Collections.sort(userList, new ReturnSortedUserList());
        List<User> tempUsers;
        tempUsers = userList.subList(0, size);
        return tempUsers;
    }

    public void writeFVToFile(String aliasID, List<Float> aliasFeatureVector) throws IOException {
        String fileName = "FeatureVectorListofAliasB";
        CreateDirectory(IOProperties.All_ACTIVITY_BASE_PATH, IOProperties.FEATURE_VECTOR_FOLDER_NAME);
        String fileLocation = IOProperties.All_ACTIVITY_BASE_PATH + IOProperties.FEATURE_VECTOR_FOLDER_NAME;
        String tempfileName = fileName + IOProperties.USER_FILE_EXTENSION;
        String completeFileNameNPath = fileLocation + "/" + tempfileName;
        File file = new File(completeFileNameNPath);
        file.createNewFile();
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file, true))) {
            String content = aliasID + " " + aliasFeatureVector;
            output.append(content);
            output.newLine();
        }
    }

    public List<Alias> convertUserToObj(List post1, List post2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int[] getUserTimeProfile(User user) throws ParseException {

        user.setCategorizedTimeToUser(user); //Number of messages in 6 hour interval of a day
        user.setCategorizedDayToUser(user); //Number of messages in 7 days of week
        user.setCategorizedHourOfDayToUser(user); //Number of messages in 24 hours of day
        user.setCategorizedTypeOfWeekToUser(user);

        int[] hourOfDay = user.getClassifiedHourOfDayVector();
        int[] timeOfInterval = user.getClassifiedTimeVector();
        int[] dayOfWeek = user.getClassifiedDayVector();
        int[] typeOfWeek = user.getClassifiedTypeOfWeekVector();

        hourOfDay = returnNormalizedVector(hourOfDay);
        timeOfInterval = returnNormalizedVector(timeOfInterval);
        dayOfWeek = returnNormalizedVector(dayOfWeek);
        typeOfWeek = returnNormalizedVector(typeOfWeek);

        int[] combined = ArrayUtils.addAll(hourOfDay, timeOfInterval);
        int[] combined1 = ArrayUtils.addAll(combined, dayOfWeek);
        int[] combined3 = ArrayUtils.addAll(combined1, typeOfWeek);

        return combined3;
    }

    public int[] getUserTimeProfile(List<String> userPostTime, List<String> userPostDate) throws ParseException {

        int[] hourOfDay = getHourOfDayVector(userPostTime);
        int[] timeOfInterval = getPeriodVector(userPostTime);

        int[] dayOfWeek = getDayofWeekVector(userPostDate);
        int[] typeOfWeek = getTypeOfWeekVector(userPostDate);

        hourOfDay = returnNormalizedVector(hourOfDay);
        timeOfInterval = returnNormalizedVector(timeOfInterval);
        dayOfWeek = returnNormalizedVector(dayOfWeek);
        typeOfWeek = returnNormalizedVector(typeOfWeek);

        int[] combined = ArrayUtils.addAll(hourOfDay, timeOfInterval);
        int[] combined1 = ArrayUtils.addAll(combined, dayOfWeek);
        int[] combined3 = ArrayUtils.addAll(combined1, typeOfWeek);

        return combined3;
    }

    public int[] getTimeVector(List<String> postTime) throws SQLException {

        int[] rr = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (String postHour : postTime) {
            String[] time = postHour.split(":");
            int hr = Integer.parseInt(time[0]);
            rr[hr]++;
        }
        return rr;
    }

    public int[] getTimeFeatureVector(List<String> postTime) throws SQLException {

        int[] rr = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (String time : postTime) {
            int hr = Integer.parseInt(time);
            rr[hr]++;
        }
        return rr;
    }

    /*public double[] normalizedFeatureVector(int[] featureVector) {
        double[] normFeatureVector = new double[24];
        double total = 0;

        for (int time : featureVector) {
            total += time;
        }

        for (int i = 0; i < featureVector.length; i++) {
            int j = featureVector[i];

            normFeatureVector[i] = j / total;
        }
        return normFeatureVector;
    }*/
    /**
     * return the normalized/percentage of posts
     *
     * @param timeVector
     * @param sum
     * @return
     */
    public int[] returnNormalizedVector(int[] timeVector) {
        int total = 0;

        for (int time : timeVector) {
            total += time;
        }

        for (int index = 0; index < timeVector.length; index++) {
            double time = timeVector[index];
            double perc = (double) (time / total);
            int temp = (int) ((perc * 100) + 0.5);
            timeVector[index] = temp;
        }
        return timeVector;
    }

    private int[] getHourOfDayVector(List<String> userPost) {
        int[] postHourVector = new int[24];
        for (String post : userPost) {
            Timestamp ts = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()).concat(post));
            int timeCategory = ts.getHours();
            postHourVector[timeCategory] = postHourVector[timeCategory] + 1;
        }
        return postHourVector;
    }

    private int[] getPeriodVector(List<String> userPost) {
        ClusterCommons cc = new ClusterCommons();
        int[] postPeriodVector = new int[6];

        for (String post : userPost) {
            Timestamp ts = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()).concat(post));
            int timeCategory = cc.getTimeCategory(ts.getHours());
            if (timeCategory < 6) {
                postPeriodVector[timeCategory] = postPeriodVector[timeCategory] + 1;
            }
        }
        return postPeriodVector;
    }

    private int[] getDayofWeekVector(List<String> userPost) throws ParseException {
        int[] dayOfWeekVector = new int[7];
        for (String post : userPost) {

            int DayOfWeek = getDayOfWeek(post) - 1;
            dayOfWeekVector[DayOfWeek] = dayOfWeekVector[DayOfWeek] + 1;
        }
        return dayOfWeekVector;
    }

    private int[] getTypeOfWeekVector(List<String> userPostDate) throws ParseException {
        int[] typeOfWeekVector = new int[2];
        for (String post : userPostDate) {

            int dayOfWeek = getDayOfWeek(post);
            int typeOfWeek = getTypeOfWeek(dayOfWeek);

            typeOfWeekVector[typeOfWeek] = typeOfWeekVector[typeOfWeek] + 1;
        }
        return typeOfWeekVector;
    }

    private int getDayOfWeek(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = format1.parse(date);
        c.setTime(dt1);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    private int getTypeOfWeek(int day) throws ParseException {
        if (day >= 2 && day <= 6) {
            return 0;
        } else {
            return 1;
        }
    }
}
