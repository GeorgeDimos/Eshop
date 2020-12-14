package com.spring.eshop.cronjobs;

import com.spring.eshop.dao.ConfirmationTokenDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Transactional
public class PurgeConfirmationTokens {

	private final ConfirmationTokenDAO confirmationTokenDAO;

	@Autowired
	public PurgeConfirmationTokens(ConfirmationTokenDAO confirmationTokenDAO) {
		this.confirmationTokenDAO = confirmationTokenDAO;
	}

	//Run once per day at 00:00 and delete all tokens older than 24 hours
	//https://crontab.guru
	@Scheduled(cron = "0 0 * * * ?")
	public void deleteExpired() {
		Date yesterday = Date.from(Instant.now().minus(1, ChronoUnit.DAYS));
		confirmationTokenDAO.deleteAllByCreateDateIsLessThan(yesterday);
	}
}
