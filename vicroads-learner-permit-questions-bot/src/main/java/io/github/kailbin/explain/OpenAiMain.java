package io.github.kailbin.explain;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

public class OpenAiMain {

    OpenAiChatModel chatModel;

    public OpenAiMain() {
        // 1. 初始化底层 API 包装器
        // 参数：BaseUrl (可选), ApiKey
        OpenAiApi openAiApi = OpenAiApi.builder()
                // .baseUrl("http://127.0.0.1:8000/") // oMLX
                .baseUrl("http://127.0.0.1:11434/") // ollama
                .apiKey("none")
                .build();

        // 2. 配置 ChatOptions
        // 重点：在这里关闭“Thinking”模式（针对支持该模式的模型，如 o1/o3）
        var options = OpenAiChatOptions.builder()
                 .model("kimi-k2.5:cloud")
//                 .model("Qwen3.5-9B-MLX-4bit")
//                 .model("Qwen3.5-27B-4bit")
                // .model("Qwen3.5-27B-Claude-4.6-Opus-Distilled-MLX-4bit") // 使用不支持/不开启推理模式的标准模型
                .build();

        // 3. 手动构造 ChatModel
        this.chatModel = OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(options)
                .build();
    }

    public String getResponse(String userInput) {
        // 4. 发送请求
        return chatModel.call(userInput);
    }

    public static void main(String[] args) {
        OpenAiMain client = new OpenAiMain();
        System.out.println(client.getResponse("你好，请简单介绍下你自己。 /no_think"));
    }

}
