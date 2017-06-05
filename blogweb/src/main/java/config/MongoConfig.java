package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.uniquenotion.repository.RepositoryPackage;

@Configuration
@EnableMongoRepositories(basePackageClasses = RepositoryPackage.class)
public class MongoConfig extends AbstractMongoConfiguration {

	@Value("${spring.data.mongodb.database}")
	private String dbName;

	@Value("${spring.data.mongodb.uri}")
	private String mongoURI;

	@Override
	protected String getDatabaseName() {
		return dbName;
	}

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(mongo(), getDatabaseName());
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), mappingMongoConverter());

		return mongoTemplate;

	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		MongoClientURI uri = new MongoClientURI(mongoURI);

		return new MongoClient(uri);
	}

}