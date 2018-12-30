## Readme

Usage: 

1. git clone https://github.com/lyubent/cass-threading
2. configure *cassthrd.yaml*
3. mvn package
4. java -jar c-3-0-14-client-<version>.jar

Config
- parallelism - number of parallel threads / session to execute during the benchmark
- nodes - yaml list of cluster nodes
- port - Port used by native clients
- benchmark - thread / session


Assumptions:

1. Cluster has atleast one DC called "datacenter1"