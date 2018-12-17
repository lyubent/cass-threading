## Readme

Usage: 

1. git clone https://github.com/lyubent/cass-threading
2. mvn package
3. Set env-vars in benchmark.sh
4. ./benchmark.sh

Config
- parallelism - number of parallel threads / session to execute during the benchmark
- nodes - yaml list of cluster nodes
- benchmark - thread / session


Assumptions:

1. Cluster has atleast one DC called "datacenter1"