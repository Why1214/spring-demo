package com.why.demo.spring.ai.controller;

import com.why.demo.spring.ai.tool.DateTimeTools;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author DuXin
 * @date 2025/04/18 08:48
 */
@RestController
public class MyAIController {

    @Resource
    private OpenAiChatModel chatModel;

    @Resource
    private ChatClient chatClient;

    @GetMapping(value = "/deepseek/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt)
                .map(ChatResponse::getResult)
                .map(Generation::getOutput)
                .map(AssistantMessage::getText);
    }

    @GetMapping(value = "/deepseek/chat/call/tool/search")
    public String callToolForSearch() {
        return ChatClient.create(chatModel)
                .prompt("What day is tomorrow?")
                .tools(new DateTimeTools())
                .call()
                .content();
    }

    @GetMapping(value = "/deepseek/chat/call/tool/action")
    public String callToolForAction() {
        return ChatClient.create(chatModel)
                .prompt("Can you set an alarm 10 minutes from now?")
                .tools(new DateTimeTools())
                .call()
                .content();
    }

    @GetMapping(value = "/deepseek/chat/test")
    public String test(@RequestParam(value = "message") String message, @RequestParam(value = "param", required = false) String param) {
//        return ChatClient.create(chatModel)
//                .prompt("查询一下NBA历史得分前十的球员")
//                .call()
//                .entity(new ParameterizedTypeReference<List<Player>>() {});

//        return chatClient
//                .prompt(message)
//                .tools(new DateTimeTools())
//                .call()
//                .content();

//        return chatClient
//                .prompt()
//                .system(ps -> ps.param("param", param))
//                .user(message)
//                .call()
//                .content();

        return ChatClient.create(chatModel)
                .prompt(message)
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }

    record Player(String name, Long score) {}
}
