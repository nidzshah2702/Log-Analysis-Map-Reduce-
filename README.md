# Log-Analysis-Map-Reduce-
Analyze the log files of the web server and the task is to find out visits per hour using map reduce.

 Given the log files of the web server, the task is to find out visits per hour.  This helps us better understand the patterns of our users, which can be used to expand and contract your environment if you are running on an elastic platform. For example, if peak load is from 5pm-7pm but we have virtually no traffic from 3am-6am, then we can scale down our environment in the middle of the night to save costs and we can scale up at 5pm so that our environment can support our load.

Solution: In this, mapper creates keys for each hour and maps keys to observed pageview. Later, reducer computes the actual count of occurrences for each hour.

# IP				Time				URL               Status
10.128.2.1  [02/Mar/2018:15:46:12   GET /profile.php?user=ham05 HTTP/1.1      200
This is an example of a log written.


I have taken web server log dataset from https://www.kaggle.com/shawon10/web-log-dataset

 
