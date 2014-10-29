<html>
    <head>
        <title>Alias Sensor</title>
        <link rel="stylesheet" type="text/css" href="utilities/reset.css">
        <link rel="stylesheet" type="text/css" href="utilities/body.css">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script type="text/javascript" src="utilities/highcharts.js"></script>
    </head>
    <body>
        <div>
            <nav>
                <ul>
                    <li><a href="index.jsp">Home</a></li>
                    <li><a href="English.jsp">English</a>
                        <ul>
                            <li><a href="WriteText.jsp">Write Text</a></li>
                            <li><a href="SelectUser.jsp">Select User</a></li>
                            <li><a href="CompareUser.jsp">Compare Users</a></li>
                            <li><a href="SplitUser.jsp">Split User</a></li>
                        </ul>
                    </li>
                    <li><a href="Swedish.jsp">Svenska</a>
                        <ul>
                            <li><a href="SweWriteText.jsp">Skriv Text</a></li>
                            <li><a href="SweSelectUser.jsp">Välja User</a></li>
                            <li><a href="SweCompareUser.jsp">Jämfor Users</a></li>
                            <li><a href="SweSplitUser.jsp">Sträck User</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
            <div id="container">
                <p>
                    <img id="logo" src = "utilities/LogoHome1.png" alt="">
                <h1>Alias Sensor</h1>
                Alias Sensor is an interactive information visualization prototype. 
                The prototype is capable of visualizing and analyzing user's posts and post time in a discussion board.
                Monitoring and analysis of web forums is becoming important for intelligence analysts around the globe 
                since terrorists and extremists are using forums for 
                spreading propaganda and communicating with each other. Various tools for analyzing the content of forum 
                postings and identifying aliases that need further inspection by analysts have been proposed throughout 
                literature, but a problem related to this is that individuals can make use of several aliases. 
                
                <h2>Stylometry Analysis</h2>
                Stylometry refers to the statistical analysis of writing style. With this technique, 
                the author's writing style is analyzed by constructing a "writeprint", which in many ways resemble 
                how fingerprints can be used. In this prototype, user written post are converted into feature vectors 
                and represented in graph.
                
                <h2>Time Analysis</h2>    
                Looking at the point in time when various aliases have created their forum posts can give important clues to 
                whether two different aliases refer to one and the same individual or not. 
                In this prototype,  the posts written by users are converted into feature vectors 
                and represented in graph.
            </div>
        </div>
    </body>
</html>