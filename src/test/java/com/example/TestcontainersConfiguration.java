package com.example;

import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.utility.DockerImageName;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	CassandraContainer<?> cassandraContainer() {
		return new CassandraContainer<>(
				DockerImageName.parse("scylladb/scylla").asCompatibleSubstituteFor("cassandra"));
		// return new CassandraContainer<>(DockerImageName.parse("cassandra:latest"));
	}

}
