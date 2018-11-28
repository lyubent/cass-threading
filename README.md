## Readme

Usage: 

1. git clone https://github.com/lyubent/cass-threading
2. mvn package
3. Set env-vars in benchmark.sh
4. ./benchmark.sh


Assumptions:

Cluster has atleast one DC called "dc1" and nodes are configured to use GossipingPropertyFileSnitch
480 threads - hardcoded. Will be configurable in future