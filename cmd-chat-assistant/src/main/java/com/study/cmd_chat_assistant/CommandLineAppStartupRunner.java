package com.study.cmd_chat_assistant;

import com.study.cmd_chat_assistant.services.CmdChatService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final CmdChatService cmdChatService;

    public CommandLineAppStartupRunner(CmdChatService cmdChatService) {
        this.cmdChatService = cmdChatService;
    }
    @Override
    public void run(String... args) throws Exception {
        cmdChatService.chat();
    }
}
