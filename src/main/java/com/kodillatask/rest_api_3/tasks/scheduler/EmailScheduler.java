package com.kodillatask.rest_api_3.tasks.scheduler;

import com.kodillatask.rest_api_3.tasks.config.AdminConfig;
import com.kodillatask.rest_api_3.tasks.domain.Mail;
import com.kodillatask.rest_api_3.tasks.repository.TaskRepository;
import com.kodillatask.rest_api_3.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private static final String SUBJECT = "Tasks: Once a day mail";

    @Autowired
    private SimpleEmailService simpleEmailService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AdminConfig adminConfig;


    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        simpleEmailService.send(new Mail(
                                    adminConfig.getAdminMail(),
                                    SUBJECT,
                                    "Currently in your database you have: " + size + (size == 1 ? " task." : " tasks."),
                                    "mail@mail"));
    }
}
