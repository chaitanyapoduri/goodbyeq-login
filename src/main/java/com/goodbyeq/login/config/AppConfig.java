package com.goodbyeq.login.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.bouncycastle.crypto.CryptoException;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.goodbyeq.login.encryption.KeyChain;
import com.goodbyeq.login.encryption.KeyChainEntries;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class AppConfig {
	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

	@Autowired
	private Environment environment;

	@Autowired
	private ApplicationContext context;

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	/*
	 * Configure ContentNegotiatingViewResolver
	 */
	/*@Bean
	public ViewResolver contentNegotiatingViewResolver() {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();

		// Define all possible view resolvers
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();

		resolvers.add(jsonViewResolver());
		resolvers.add(jspViewResolver());

		resolver.setViewResolvers(resolvers);
		return resolver;
	}*/

	/*
	 * Configure View resolver to provide JSON output using JACKSON library to
	 * convert object in JSON format.
	 */
	/*@Bean
	public ViewResolver jsonViewResolver() {
		return new JsonViewResolver();
	}
*/
	/*
	 * Configure View resolver to provide HTML output This is the default format in
	 * absence of any type suffix.
	 */
	/*@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}*/

	@Bean(name = "appDBProperties")
	public ApplicationDBProperties appProperties() {
		ApplicationDBProperties bean = new ApplicationDBProperties();
		bean.setConnectionHost(environment.getProperty("mysql.host"));
		bean.setConnectionClass(environment.getProperty("mysql.driver"));
		bean.setConnectionPort(environment.getProperty("mysql.port"));
		bean.setConnectionUser(environment.getProperty("mysql.user"));
		bean.setConnectionPassword(environment.getProperty("mysql.password"));
		return bean;
	}

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getProperty("mysql.driver"));
		dataSource.setUsername(environment.getProperty("mysql.user"));
		dataSource.setPassword(environment.getProperty("mysql.password"));
		dataSource.setUrl((null != environment.getProperty("mysql.url")) ? environment.getProperty("mysql.url")
				: getDatabaseURL());

		// Connection Pool configuration
		dataSource.setMinIdle(Integer.parseInt(environment.getProperty("connection.apachedcp.minIdle")));
		dataSource.setMaxIdle(Integer.parseInt(environment.getProperty("connection.apachedcp.maxIdle")));
		dataSource.setMaxTotal(Integer.parseInt(environment.getProperty("connection.apachedcp.maxTotal")));
		dataSource.setMaxWaitMillis(Integer.parseInt(environment.getProperty("connection.apachedcp.maxWaitMillis")));
		dataSource.setRemoveAbandonedOnBorrow(
				Boolean.valueOf(environment.getProperty("connection.apachedcp.removeAbandoned")));
		dataSource.setInitialSize(Integer.parseInt(environment.getProperty("connection.apachedcp.initialSize")));
		dataSource.setValidationQuery(environment.getProperty("connection.apachedcp.validationQuery"));
		dataSource.setValidationQueryTimeout(
				Integer.parseInt(environment.getProperty("connection.apachedcp.validationQueryTimeout")));

		dataSource.setTestOnCreate(true);
		dataSource.setTestOnReturn(true);
		dataSource.setTestOnBorrow(true);

		return dataSource;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		HibernateJpaVendorAdapter hibernateJpa = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		hibernateJpa.setDatabasePlatform(environment.getProperty("hibernate.dialect"));
		hibernateJpa.setShowSql(environment.getProperty("hibernate.show_sql", Boolean.class));
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
		try {
			emf.setDataSource(getDataSource());
			emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);
			emf.setPackagesToScan("com.goodbyeq.user.db.bo");
			emf.setPersistenceUnitName("Hibernate");
			emf.setJpaProperties(jpaProperties);
		} catch (Exception e) {
			logger.error("Exception while initializing database entity manager", e);
		}
		return emf;
	}

	@Bean
	public JpaTransactionManager geJpaTransactionManager() {
		JpaTransactionManager txnMgr = new JpaTransactionManager();
		txnMgr.setEntityManagerFactory(entityManagerFactory().getObject());
		return txnMgr;
	}

	private String getDatabaseURL() {
		String url = "jdbc:mysql://" + environment.getProperty("mysql.host") + ":"
				+ environment.getProperty("mysql.port") + "/" + environment.getProperty("mysql.schema")
				+ "?autoReconnectForPools=true";
		return url;
	}
	
	/*@Value("${keychain.file:billlive_key.xml}")
	private String keyChainFile = "billlive_key.xml";
	
	@Resource(name = "keyChain")
	private KeyChain keyChain;
	
	 @Bean
		public KeyChainEntries keyChainEntries() {
	    	fixKeyLength();
			KeyChainEntries keyChainEntries = null;
			try {
				// ENC
				// Load the googleKMS encrypted file.
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				URL resource = loader.getResource(keyChainFile);
				// File billliveFile = new File(resource.toURI());
				// InputStream encryptedStream = new FileInputStream(billliveFile);
				StringBuilder contentBuilder = new StringBuilder();

				try (Stream<String> stream = Files.lines(Paths.get(resource.toURI()), StandardCharsets.UTF_8)) {
					stream.forEach(s -> contentBuilder.append(s).append("\n"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				byte[] fileInBytes = contentBuilder.toString().getBytes();
				InputStream plainTextStream = new ByteArrayInputStream(fileInBytes);
				keyChainEntries = keyChain.loadKeyChain(plainTextStream);
			} catch (URISyntaxException | CryptoException e) {
				logger.info("Error while encrypting");
			}
			return keyChainEntries;
		}*/

	    private void fixKeyLength() {
		    String errorString = "Failed manually overriding key-length permissions.";
		    int newMaxKeyLength;
		    try {
		        if ((newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES")) < 256) {
		            Class c = Class.forName("javax.crypto.CryptoAllPermissionCollection");
		            Constructor con = c.getDeclaredConstructor();
		            con.setAccessible(true);
		            Object allPermissionCollection = con.newInstance();
		            Field f = c.getDeclaredField("all_allowed");
		            f.setAccessible(true);
		            f.setBoolean(allPermissionCollection, true);

		            c = Class.forName("javax.crypto.CryptoPermissions");
		            con = c.getDeclaredConstructor();
		            con.setAccessible(true);
		            Object allPermissions = con.newInstance();
		            f = c.getDeclaredField("perms");
		            f.setAccessible(true);
		            ((Map) f.get(allPermissions)).put("*", allPermissionCollection);

		            c = Class.forName("javax.crypto.JceSecurityManager");
		            f = c.getDeclaredField("defaultPolicy");
		            f.setAccessible(true);
		            Field mf = Field.class.getDeclaredField("modifiers");
		            mf.setAccessible(true);
		            mf.setInt(f, f.getModifiers() & ~Modifier.FINAL);
		            f.set(null, allPermissions);

		            newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES");
		        }
		    } catch (Exception e) {
		        throw new RuntimeException(errorString, e);
		    }
		    if (newMaxKeyLength < 256)
		        throw new RuntimeException(errorString); // hack failed
		}
}
