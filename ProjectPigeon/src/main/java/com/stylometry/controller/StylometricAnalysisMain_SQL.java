package com.stylometry.controller;

/**
 *
 * @author ITE
 */
import com.stylometry.IOHandler.IOProperties;
import com.stylometry.IOHandler.IOReadWrite;
import com.stylometry.model.Alias;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 * This is some code for doing stylometric matching of aliases based on posts
 * (such as discussion board messages). Features: letters (26), digits (10),
 * punctuation (11), function words (293), word length (20), sentence length
 * (6). Except for freq. of sentence lengths, this is a subset of the features
 * used in Narayanan et al. (On the Feasibility of Internet-Scale Author
 * Identification)
 *
 * Some problems to consider: The more features, the more "sparse" the feature
 * vectors will be (many zeros) in case of few posts --> similar feature vectors
 * due to a majority of zeros
 *
 * Since all features are not of the same "dimension", it makes sense to
 * normalize/standardize the features to have mean 0 and variance 1, as in
 * Narayanan et al. The above standardization works when finding the best
 * matching candidate, but may be problematic since the "similarity" between two
 * aliases will depend on the features of other aliases (since the
 * standardization works column/(feature)-wise).
 *
 * If we do not use normalization/standardization, we cannot use feature which
 * are not frequencies, since the features with large magnitudes otherwise will
 * dominate completely!!! Even if we do only use frequencies, the results
 * without normalization seems poor (good with normalization) Try to improve the
 * unnormalized version before using it on real problems...
 *
 * Observe that the obtained similarity values cannot be used directly as a
 * measure of the "match percentage"!
 *
 *
 * @author frejoh
 *
 */
public class StylometricAnalysisMain_SQL {

    private List<String> functionWords;			// Contains the function words we are using
    private List<Alias> aliases;				// The aliases we are interested in to compare        
    private List<List<Float>> featVectorForAllAliases;

    public StylometricAnalysisMain_SQL() {
        super();
        loadFunctionWords(IOProperties.FUNCTION_WORDS_PATH);
        //loadDataFile(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        System.out.println("This is testing from constructor");
        aliases = new ArrayList<>();
    }

    private void loadDataFile(String path) {
        System.out.println(getClass().getResource("../../../").getFile());
        String filepath = getClass().getResource("../../../").getFile() + path;
        System.out.println(filepath);
    }

    public void executeAnalysis(String ID) throws IOException, SQLException {
        IOReadWrite ioRW = new IOReadWrite();
        Alias user = new Alias();
        String styloJSONfilename = "stylo.json";
        String timeJSONfilename = "timeSeries.json";
        /*String tempBasePath = IOProperties.INDIVIDUAL_USER_FILE_PATH;
        String basePath = getClass().getResource("../../../../").getFile() + tempBasePath;
        String ext = IOProperties.USER_FILE_EXTENSION;

        user = ioRW.convertTxtFileToAliasObj(basePath, ID, ext);*/
        ArrayList post = getUserPost(ID);
        user.setPosts(post);
        ArrayList time = getUserPostTime(ID);
        user.setPostTime(time);

        List<Float> freatuteVector = createFeatureVectors(user);
        int[] tempTimeFeatureVector = ioRW.getTimeFeatureVector(user.getPostTime());
        double[] timeFeatureVector = normalizedFeatureVector(tempTimeFeatureVector);

        returnJSONfile(freatuteVector, styloJSONfilename);
        returnJSONfile(timeFeatureVector, timeJSONfilename);
        //return featureObject;
    }

    double[] normalizedFeatureVector(int[] featureVector) {
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
    }

    private ArrayList getUserPost(String Id) throws SQLException {
        ArrayList userPosts = new ArrayList();
        ResultSet result = null;
        Statement stmt = null;
        Connection conn = null;

        try {
            Class c = Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:8889/twitter_stream",
                    "root", "root");
            String query = "select Text from twitter_data_view where User_ID = " + Id;
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);

            while (result.next()) {
                String post = result.getString("Text");
                userPosts.add(post);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error!!!!!!" + e);
        } finally {
            if (null != conn) {
                conn.close();
            }
        }
        return userPosts;
    }

    private ArrayList getUserPostTime(String Id) throws SQLException {
        ArrayList<String> userPostsTime = new ArrayList();
        ResultSet result = null;
        Statement stmt = null;
        Connection conn = null;

        try {
            Class c = Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:8889/twitter_stream",
                    "root", "root");
            String query = "select Created_at from twitter_data_view where User_ID = " + Id;
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);

            while (result.next()) {
                String postTime = result.getString("Created_at");
                System.out.println(postTime);
                String time = postTime.substring(11, 13);
                userPostsTime.add(time);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error!!!!!!" + e);
        } finally {
            if (null != conn) {
                conn.close();
            }
        }
        return userPostsTime;
    }

    public JSONObject executePostAnalysis(List posts) throws IOException {
        String filename = "stylo.json";
        Alias user = new Alias();
        user.setPosts(posts);
        List<Float> freatuteVector = createFeatureVectors(user);
        JSONObject featureObject = returnJSONfile(freatuteVector, filename);
        return featureObject;
    }

    private JSONObject returnJSONfile(List<Float> freatuteVector, String filename) {
        JSONObject obj = new JSONObject();
        for (int i = 0; i < freatuteVector.size(); i++) {
            obj.put(i+1, freatuteVector.get(i));
        }
        writeToJSONFile(obj, filename);
        return obj;
    }

    private JSONObject returnJSONfile(double[] freatuteVector, String filename) {
        JSONObject obj = new JSONObject();
        for (int i = 0; i < freatuteVector.length; i++) {
            obj.put(i+1, freatuteVector[i]);
        }
        writeToJSONFile(obj, filename);
        return obj;
    }

    private void writeToJSONFile(JSONObject obj, String filename) {
        try {
            System.out.println("UserPath" + System.getProperty("user.home"));
            String filePath = "/Users/amendrashrestha/NetBeansProjects/ProjectPigeon/ProjectPigeon/src/main/webapp/utilities" + File.separator + filename;

            FileWriter file = new FileWriter(filePath);

            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException ex) {

        }
    }

    public List<Float> returnPostAnalysis(List posts) throws IOException {
        Alias user = new Alias();

        user.setPosts(posts);
        List<Float> freatuteVector = createFeatureVectors(user);
        return freatuteVector;
    }

    /*private JSONObject returnJSONfileTemp(List<Float> freatuteVector) {
        Map<Integer, Float> data = new HashMap<>();
        JSONArray array = new JSONArray();
        
        for (int i = 0; i < freatuteVector.size(); i++) {
            data.put(i, freatuteVector.get(i));

            JSONObject json = new JSONObject(data.get(i));
             array.put(json);
        }
        
        JSONObject finalObject = new JSONObject();
        return finalObject.put("features", array);
    }*/
    public double returnStylo(List post1, List post2) {
        double stylo = 0.0;
        try {
            IOReadWrite ioRW = new IOReadWrite();
            List<Alias> aliasList = ioRW.convertUserToObj(post1, post2);
            stylo = executeStylo(aliasList);
        } catch (SQLException ex) {
            Logger.getLogger(StylometricAnalysisMain_SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stylo;
    }

    public double executeStylo(List<Alias> aliasList) throws SQLException {
        this.aliases = aliasList;
        createFeatureVectors();
        double stylo = compareFeatureVectors(aliases.get(0).getFeatureVector(), aliases.get(1).getFeatureVector());
        return stylo;
    }

    public List<Float> executeTxtStylo(String post) {
        Alias user = new Alias();
        user.setSinglePost(post);
        List<Float> freatuteVector = createFeatureVectors(user);
        return freatuteVector;
    }

    /**
     * Extract words from text string, remove punctuation etc.
     *
     * @param text
     * @return
     */
    public static List<String> extractWords(String text) {
        List<String> wordList = new ArrayList<String>();
        String[] words = text.split("\\s+");
        wordList.addAll(Arrays.asList(words)); // words[i] = words[i].replaceAll("[^\\w]", "");
        return wordList;
    }

    /**
     * Load the list of function words from file
     *
     * @param path
     */
    private void loadFunctionWords(String path) {
        functionWords = new ArrayList<>();
        BufferedReader br;
        try {
            path = System.getProperty("user.home") + File.separator + path;
            System.out.println(path);
            br = new BufferedReader(new FileReader(path));

            String strLine;
            while ((strLine = br.readLine()) != null) {
                String trimmedLine = strLine.trim();
                if (!"".equals(trimmedLine)) {
                    functionWords.add(trimmedLine.trim());
                }
            }
            br.close();
        } catch (Exception e) {
        }
    }

    /**
     * Create a list containing the number of occurrences of the various
     * function words in the post (list of extracted words)
     *
     * @param words
     * @return
     */
    public ArrayList<Float> countFunctionWords(List<String> words) {
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(functionWords.size(), 0.0f));	// Initialize to zero
        for (String word : words) {
            if (functionWords.contains(word)) {
                int place = functionWords.indexOf(word);
                float value = tmpCounter.get(place);
                value++;
                tmpCounter.set(place, value);
            }
        }
        // "Normalize" the values by dividing with length of the post (nr of words in the post)
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) words.size());
        }
        return tmpCounter;
    }

    /**
     * Create a list containing the number of occurrences of letters a to z in
     * the text
     *
     * @param post
     * @return
     */
    public ArrayList<Float> countCharactersAZ(String post) {
        post = post.toLowerCase();	// Upper or lower case does not matter, so make all letters lower case first...
        char[] ch = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(ch.length, 0.0f));
        for (int i = 0; i < ch.length; i++) {
            int value = countOccurrences(post, ch[i]);
            tmpCounter.set(i, (float) value);
        }
        // "Normalize" the values by dividing with total nr of characters in the post (excluding white spaces)
        int length = post.replaceAll(" ", "").length();
        if (0 == length) {
            length = 1;
        }
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) length);
        }
        return tmpCounter;
    }

    /**
     * Create a list containing the number of special characters in the text
     *
     * @param post
     * @return
     */
    public ArrayList<Float> countSpecialCharacters(String post) {
        post = post.toLowerCase();	// Upper or lower case does not matter, so make all letters lower case first...
        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '?', '!', ',', ';', ':', '(', ')', '"', '-', '´'};
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(ch.length, 0.0f));
        for (int i = 0; i < ch.length; i++) {
            int value = countOccurrences(post, ch[i]);
            tmpCounter.set(i, (float) value);
        }
        // "Normalize" the values by dividing with total nr of characters in the post (excluding whitespaces)
        int length = post.replaceAll(" ", "").length();
        if (0 == length) {
            length = 1;
        }
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) length);
        }
        return tmpCounter;
    }

    /**
     * Counts the frequency of various word lengths in the list of words.
     *
     * @param words
     * @return
     */
    public ArrayList<Float> countWordLengths(List<String> words) {
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(20, 0.0f));	// Where 20 corresponds to the number of word lengths of interest 
        int wordLength = 0;
        for (String word : words) {
            wordLength = word.length();
            // We only care about wordLengths in the interval 1-20
            if (wordLength > 0 && wordLength <= 20) {
                float value = tmpCounter.get(wordLength - 1);	// Observe that we use wordLength-1 as index!
                value++;
                tmpCounter.set(wordLength - 1, value);
            }
        }
        // "Normalize" the values by dividing with length of the post (nr of words in the post)
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) words.size());
        }
        return tmpCounter;
    }

    /**
     * Counts the frequency of various sentence lengths in the post.
     *
     * @param post
     * @return
     */
    public ArrayList<Float> countSentenceLengths(String post) {
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(6, 0.0f));	// Where 6 corresponds to the number of sentence lengths of interest
        // Split the post into a number of sentences
        List<String> sentences = splitIntoSentences(post);
        int nrOfWords = 0;
        for (String sentence : sentences) {
            // Get number of words in the sentence
            List<String> words = extractWords(sentence);
            nrOfWords = words.size();
            if (nrOfWords > 0 && nrOfWords <= 10) {
                tmpCounter.set(0, tmpCounter.get(0) + 1);
            } else if (nrOfWords <= 20) {
                tmpCounter.set(1, tmpCounter.get(1) + 1);
            } else if (nrOfWords <= 30) {
                tmpCounter.set(2, tmpCounter.get(2) + 1);
            } else if (nrOfWords <= 40) {
                tmpCounter.set(3, tmpCounter.get(3) + 1);
            } else if (nrOfWords <= 50) {
                tmpCounter.set(4, tmpCounter.get(4) + 1);
            } else if (nrOfWords >= 51) {
                tmpCounter.set(5, tmpCounter.get(5) + 1);
            }
        }
        // "Normalize" the values by dividing with nr of sentences in the post
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) sentences.size());
        }
        return tmpCounter;
    }

    /**
     * Splits a post/text into a number of sentences
     *
     * @param text
     * @return
     */
    public List<String> splitIntoSentences(String text) {
        List<String> sentences = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            sentences.add(text.substring(start, end));
        }
        return sentences;
    }

    /**
     * Count the number of occurrences of certain character in a String
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }

    /**
     * return time feature vector of each user
     *
     * @param user
     * @return
     */
    private List<Float> createTimeFeatureVectors(Alias user) {
        List<Float> timeFeatureVector = new ArrayList<>();
        List postTimeVector = new ArrayList();

        for (Object postTime : user.getPostTime()) {

        }

        return timeFeatureVector;
    }

    /**
     * Loops through all aliases and construct their feature vectors
     *
     * @param user
     * @return
     */
    public List<Float> createFeatureVectors(Alias user) {
        List<Float> featureVector = new ArrayList<>();
        featVectorForAllAliases = new ArrayList<>();
        //  for (Alias alias : aliases) {
        int cnt = 0;
        user.setFeatureVectorPosList(user.initializeFeatureVectorPostList());
        // Calculate each part of the "feature vector" for each individual post
        for (String post : user.getPosts()) {
            List<String> wordsInPost = extractWords(post);
            int placeInFeatureVector = 0;

            placeInFeatureVector = countFunctionWords(wordsInPost).size();
            System.out.println("FunctionWOrdSize: " + placeInFeatureVector);
            user.addToFeatureVectorPostList(countFunctionWords(wordsInPost), 0, cnt);

            user.addToFeatureVectorPostList(countWordLengths(wordsInPost), placeInFeatureVector, cnt);
            placeInFeatureVector = placeInFeatureVector + countWordLengths(wordsInPost).size();
            System.out.println("WordLengthSize: " + placeInFeatureVector);

            user.addToFeatureVectorPostList(countCharactersAZ(post), placeInFeatureVector, cnt);
            placeInFeatureVector = placeInFeatureVector + countCharactersAZ(post).size();
            System.out.println("DigitNCharacters: " + placeInFeatureVector);

            user.addToFeatureVectorPostList(countSpecialCharacters(post), placeInFeatureVector, cnt);
            placeInFeatureVector = placeInFeatureVector + countSpecialCharacters(post).size();
            System.out.println("Special Character: " + placeInFeatureVector);
            cnt++;
            //   }

            ArrayList<ArrayList<Float>> featureVectorList = user.getFeatureVectorPosList();

            int numberOfPosts = user.getPosts().size();
            int nrOfFeatures = featureVectorList.get(0).size();
            featureVector = new ArrayList<>(Collections.nCopies(nrOfFeatures, 0.0f));
            // Now we average over all posts to create a single feature vector for each alias
            for (int i = 0; i < nrOfFeatures; i++) {
                float value = 0.0f;
                for (int j = 0; j < numberOfPosts; j++) {
                    value += featureVectorList.get(j).get(i);
                }
                value /= numberOfPosts;
                featureVector.set(i, value);
            }
            user.setFeatureVector(featureVector);
            featVectorForAllAliases.add(featureVector);
        }
        return featureVector;
        //normalizeFeatureVector();
    }

    /**
     * Loops through all aliases and construct their feature vectors
     *
     * @param user
     */
    public void createFeatureVectors(List<Alias> user) {
        List<Float> featureVector = new ArrayList<>();
        featVectorForAllAliases = new ArrayList<>();
        for (Alias alias : user) {
            int cnt = 0;
            alias.setFeatureVectorPosList(alias.initializeFeatureVectorPostList());
            // Calculate each part of the "feature vector" for each individual post
            for (String post : alias.getPosts()) {
                List<String> wordsInPost = extractWords(post);
                int placeInFeatureVector = 0;

                placeInFeatureVector = countFunctionWords(wordsInPost).size();

                alias.addToFeatureVectorPostList(countFunctionWords(wordsInPost), 0, cnt);
                alias.addToFeatureVectorPostList(countWordLengths(wordsInPost), placeInFeatureVector, cnt);

                placeInFeatureVector = placeInFeatureVector + countWordLengths(wordsInPost).size();
                alias.addToFeatureVectorPostList(countCharactersAZ(post), placeInFeatureVector, cnt);

                placeInFeatureVector = placeInFeatureVector + countSpecialCharacters(post).size();
                alias.addToFeatureVectorPostList(countSpecialCharacters(post), placeInFeatureVector, cnt);
                cnt++;
            }

            ArrayList<ArrayList<Float>> featureVectorList = alias.getFeatureVectorPosList();

            int numberOfPosts = alias.getPosts().size();
            int nrOfFeatures = featureVectorList.get(0).size();
            featureVector = new ArrayList<>(Collections.nCopies(nrOfFeatures, 0.0f));
            // Now we average over all posts to create a single feature vector for each alias
            for (int i = 0; i < nrOfFeatures; i++) {
                float value = 0.0f;
                for (int j = 0; j < numberOfPosts; j++) {
                    value += featureVectorList.get(j).get(i);
                }
                value /= numberOfPosts;
                featureVector.set(i, value);
            }
            alias.setFeatureVector(featureVector);
            featVectorForAllAliases.add(featureVector);
        }
        normalizeFeatureVector();
    }

    public void createFeatureVectors() {
        List<Float> featureVector;
        featVectorForAllAliases = new ArrayList<>();
        for (Alias alias : aliases) {
            int cnt = 0;
            alias.setFeatureVectorPosList(alias.initializeFeatureVectorPostList());
            // Calculate each part of the "feature vector" for each individual post
            for (String post : alias.getPosts()) {
                List<String> wordsInPost = extractWords(post);
                int placeInFeatureVector = 0;

                placeInFeatureVector = countFunctionWords(wordsInPost).size();

                alias.addToFeatureVectorPostList(countFunctionWords(wordsInPost), 0, cnt);
                alias.addToFeatureVectorPostList(countWordLengths(wordsInPost), placeInFeatureVector, cnt);

                placeInFeatureVector = placeInFeatureVector + countWordLengths(wordsInPost).size();
                alias.addToFeatureVectorPostList(countCharactersAZ(post), placeInFeatureVector, cnt);

                placeInFeatureVector = placeInFeatureVector + countSpecialCharacters(post).size();
                alias.addToFeatureVectorPostList(countSpecialCharacters(post), placeInFeatureVector, cnt);
                cnt++;
            }

            ArrayList<ArrayList<Float>> featureVectorList = alias.getFeatureVectorPosList();

            int numberOfPosts = alias.getPosts().size();
            int nrOfFeatures = featureVectorList.get(0).size();
            featureVector = new ArrayList<>(Collections.nCopies(nrOfFeatures, 0.0f));
            // Now we average over all posts to create a single feature vector for each alias
            for (int i = 0; i < nrOfFeatures; i++) {
                float value = 0.0f;
                for (int j = 0; j < numberOfPosts; j++) {
                    value += featureVectorList.get(j).get(i);
                }
                value /= numberOfPosts;
                featureVector.set(i, value);
            }
            alias.setFeatureVector(featureVector);
            featVectorForAllAliases.add(featureVector);
        }
        normalizeFeatureVector();
    }

    /**
     * Used for comparing two feature vectors
     *
     * @param featVector1
     * @param featVector2
     * @return
     */
    public double compareFeatureVectors(List<Float> featVector1, List<Float> featVector2) {
        List<Float> floatList = featVector1;
        float[] floatArray1 = new float[floatList.size()];

        for (int i = 0; i < floatList.size(); i++) {
            Float f = floatList.get(i);
            floatArray1[i] = (f != null ? f : Float.NaN);
        }

        List<Float> floatList2 = featVector2;
        float[] floatArray2 = new float[floatList2.size()];

        for (int i = 0; i < floatList2.size(); i++) {
            Float f = floatList2.get(i);
            floatArray2[i] = (f != null ? f : Float.NaN);
        }
        return calculateSimilarity(floatArray1, floatArray2);
    }

    /**
     * Calculates cosine similarity between two real vectors
     *
     * @param value1
     * @param value2
     * @return
     */
    public double calculateSimilarity(float[] value1, float[] value2) {
        float sum = 0.0f;
        float sum1 = 0.0f;
        float sum2 = 0.0f;
        for (int i = 0; i < value1.length; i++) {
            float v1 = value1[i];
            float v2 = value2[i];
            if ((!Float.isNaN(v1)) && (!Float.isNaN(v2))) {
                sum += v2 * v1;
                sum1 += v1 * v1;
                sum2 += v2 * v2;
            }
        }
        if ((sum1 > 0) && (sum2 > 0)) {
            double result = sum / (Math.sqrt(sum1) * Math.sqrt(sum2));
            // result can be > 1 (or -1) due to rounding errors for equal vectors, 
            //but must be between -1 and 1
            return Math.min(Math.max(result, -1d), 1d);
            //return result;
        } else if (sum1 == 0 && sum2 == 0) {
            return 1d;
        } else {
            return 0d;
        }
    }

    /**
     * Calculate similarity between all pairs of aliases (a lot of comparisons
     * if there are many aliases)
     */
    public void compareAllPairsOfAliases() {
        for (int i = 0; i < aliases.size(); i++) {
            for (int j = i + 1; j < aliases.size(); j++) {
                double sim = compareFeatureVectors(aliases.get(i).getFeatureVector(), aliases.get(j).getFeatureVector());
                System.out.println("Similarity between alias " + aliases.get(i).getUserID() + " and " + aliases.get(j).getUserID() + " is: " + sim);
            }
        }
    }

    /**
     * Find the index of the alias that is most similar to the selected alias.
     *
     * @param index
     * @return
     */
    public int findBestMatch(int index) {
        double highestSimilarity = -10.0;
        int indexMostSimilar = 0;
        for (int i = 0; i < aliases.size(); i++) {
            if (i != index) {
                double sim = compareFeatureVectors(aliases.
                        get(i).getFeatureVector(),
                        aliases.get(index).getFeatureVector());
                if (sim > highestSimilarity) {
                    highestSimilarity = sim;
                    indexMostSimilar = i;
                }
            }
        }
        return indexMostSimilar;
    }

    /**
     * Standardize/normalize the feature vectors for all aliases. Aim is mean 0
     * and variance 1 for each feature vector. Please note that this will result
     * in feature vectors that depend on the feature vectors of the other
     * aliases...
     */
    public void normalizeFeatureVector() {
        int nrOfFeatures = featVectorForAllAliases.get(0).size();
        List<Double> avgs = new ArrayList<>(nrOfFeatures);
        List<Double> stds = new ArrayList<>(nrOfFeatures);

        // Calculate avg (mean) for each feature
        for (int i = 0; i < nrOfFeatures; i++) {
            double sum = 0.0;
            for (int j = 0; j < aliases.size(); j++) {
                sum += featVectorForAllAliases.get(j).get(i);
            }
            avgs.add(sum / aliases.size());
        }

        // Calculate std for each feature
        for (int i = 0; i < nrOfFeatures; i++) {
            double avg = avgs.get(i);
            double tmp = 0.0;
            for (int j = 0; j < aliases.size(); j++) {
                tmp += (avg - featVectorForAllAliases.get(j).get(i)) * (avg - featVectorForAllAliases.get(j).get(i));
            }
            stds.add(Math.sqrt(tmp / aliases.size()));
        }

        // Do the standardization of the feature vectors
        for (int i = 0; i < nrOfFeatures; i++) {
            for (int j = 0; j < aliases.size(); j++) {
                if (stds.get(i) == 0) {
                    aliases.get(j).setFeatureValue(i, 0.0f);
                } else {
                    aliases.get(j).setFeatureValue(i, (float) ((featVectorForAllAliases.get(j).get(i) - avgs.get(i)) / stds.get(i)));
                }
            }
        }

    }

    /*public static void main(String args[]) throws SQLException, IOException {
        String text1 = "This is a litte test.";
        String text11 = "This is the second little text. I wonder if this will work out okay.";
        String text12 = "This is the second little text. I wonder if this will work out okay.";
        String text13 = "This is the second little text. I wonder if this will work out okay.";
        String text2 = "Hi, how are you? This is a test...";
        String text22 = "You, have you seen this video? Goooh!";
        String text23 = "You, have you seen this video? Goooh!";
        String text24 = "You, have you seen this video? Goooh!";
        List firstList = new ArrayList();
        List secondList = new ArrayList();
        firstList.add(text1);
        firstList.add(text11);
        firstList.add(text12);
        firstList.add(text13);
        secondList.add(text2);
        secondList.add(text22);
        secondList.add(text23);
        secondList.add(text24);

        StylometricAnalysisMain init = new StylometricAnalysisMain();
//        double stylo = init.returnStylo(firstList, secondList);
        List<Float> stylo = init.executePostAnalysis(firstList);
        System.out.println("Stylo: " + stylo);
    }*/
}
