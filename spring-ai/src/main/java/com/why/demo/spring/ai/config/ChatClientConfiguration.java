package com.why.demo.spring.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author DuXin
 * @date 2025/04/20 11:57
 */
@Configuration
public class ChatClientConfiguration {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
//        return builder.defaultSystem("你是一个非常悲观的经济评论专家！").build();

//        return builder.defaultSystem("你是一个非常乐观的经济评论专家！").build();

//        return builder.defaultSystem("你是一个非常{param}的经济评论专家！").build();
        setBuilder(builder);
        return builder.build();
    }

    public void setBuilder(ChatClient.Builder builder) {
        builder.defaultOptions(ChatOptions.builder().temperature(0.0).build());
    }
}
