package io.github.lyubent.client;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import io.github.lyubent.query.Query;

public class ClientBuilder {

    private String[] contactPoints;
    private int port;

    public ClientBuilder(String[] contactPoints, int port) {
        this.contactPoints = contactPoints;
        this.port = port;
    }

    public Cluster getCluster() {
        DCAwareRoundRobinPolicy dcAwareRoundRobinPolicy = DCAwareRoundRobinPolicy.builder()
                .withLocalDc(Query.getLocalDC())
                .withUsedHostsPerRemoteDc(1)
                .build();
        Cluster cluster = Cluster.builder().withLoadBalancingPolicy(new TokenAwarePolicy(dcAwareRoundRobinPolicy))
                .addContactPoints(contactPoints)
                .withPort(port)
                .build();

        return cluster;
    }
}
