package com.chandlertu.energy.managing;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlDataSourceFactory {

	private static final PooledDataSource dataSource;

	private static final Logger logger = LoggerFactory.getLogger(MysqlDataSourceFactory.class);

	static {
		Properties properties = new Properties();
		Path path = Paths.get(System.getProperty("user.home"), EnergyManagingMain.PROPERTY_FILES, "mysql.properties");
		try (InputStream input = new FileInputStream(path.toFile())) {
			properties.load(input);
		} catch (Exception e) {
			logger.error("", e);
		}

		String driver = "com.mysql.jdbc.Driver";
		String host = properties.getProperty("host");
		String port = properties.getProperty("port");
		String database = properties.getProperty("database");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=true";
		dataSource = new PooledDataSource(driver, url, username, password);
		// dataSource.setPoolPingEnabled(true);
		// dataSource.setPoolPingQuery("SELECT 1");
	}

	public static DataSource getMysqlDataSource() {
		return dataSource;
	}

}
