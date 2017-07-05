package com.chandlertu.energy.managing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chandlertu.mail.MailSender;

public class EnergyManagingMain {

	public static String PROPERTY_FILES = ".energy-managing";

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(EnergyManagingMain.class);

		while (true) {
			SqlSessionFactory sqlSessionFactory = MyBatisSqlSessionFactory.getSqlSessionFactory();
			try (SqlSession session = sqlSessionFactory.openSession()) {
				EnergyManagingMapper mapper = session.getMapper(EnergyManagingMapper.class);
				EnergyManaging energyManaging = mapper.selectEnergyManagingWhereEndtimeIsNull();

				if (energyManaging != null) {
					Date startTime = energyManaging.getStartTime();
					Date now = new Date();
					long minutes = (now.getTime() - startTime.getTime()) / 1000 / 60;
					if (minutes >= 25 && minutes % 5 == 0) {
						logger.info(String.valueOf(minutes));
						sendMail();
					}
				}
			}

			try {
				Thread.sleep(1000 * 60);
			} catch (InterruptedException e) {
				logger.error("", e);
			}
		}
	}

	public static void sendMail() {
		Properties props = new Properties();
		Path path = Paths.get(System.getProperty("user.home"), PROPERTY_FILES, "mail.properties");
		try (InputStreamReader inStream = new InputStreamReader(new FileInputStream(path.toFile()), "utf-8")) {
			props.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String account = props.getProperty("account");
		String password = props.getProperty("password");
		String smtp = props.getProperty("smtp");
		String to = props.getProperty("to");
		String subject = props.getProperty("subject");
		String text = props.getProperty("text");
		MailSender sender = new MailSender(account, password, smtp);
		sender.send(to, subject, text);
	}

}
