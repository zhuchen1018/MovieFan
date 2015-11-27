11.22 MileStone 4

local testing:
1 cd server/
2 sh run.sh
3 it will build servlet.war and use jetty-runner to setup the localhost server
4 goto http://localhost:8080/

Features:

==User: register, login, logoff, mypage and send twitter(status)

==Movie:
search movie page: 
    ==search movie by name(eg: The lord of the rings, support fuzzy search like ignorecase, redundant spaces)
    ==advanced movie search(you can identify the genre of the movie and select the order kind)

movie page:
    ==Search Result: An abstact of each movies within the query 
    ==Click movie name or movie poster, you can jump to its movie page and see details.

