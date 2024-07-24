package com.example;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;

import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration(proxyBeanMethods = false)
public class AppConfig {

	@Bean
	public IdGenerator idGenerator() {
		return new JdkIdGenerator();
	}

	@Bean
	public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
		loggingFilter.setIncludeHeaders(true);
		loggingFilter.setIncludeClientInfo(true);
		return loggingFilter;
	}

	@Bean
	@ConditionalOnProperty(name = "spring.cassandra.keyspace-name", matchIfMissing = true)
	public CqlSession cqlSession(CqlSessionBuilder cqlSessionBuilder, CassandraProperties properties) {
		if (StringUtils.hasText(properties.getKeyspaceName())) {
			return cqlSessionBuilder.build();
		}
		String keyspaceName = "demo";
		try (CqlSession session = cqlSessionBuilder.build()) {
			session.execute(
					"CREATE KEYSPACE IF NOT EXISTS %s WITH replication={'class':'NetworkTopologyStrategy', 'replication_factor':1}"
						.formatted(keyspaceName));
		}
		return cqlSessionBuilder.withKeyspace(keyspaceName).build();
	}

}
