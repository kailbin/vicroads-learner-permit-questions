package io.github.kailbin.explain;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;

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

    public String getResponseWithImage(String userInput, String base64Image) {
        if (!StringUtils.hasText(base64Image)) {
            return getResponse(userInput);
        }

        byte[] data = java.util.Base64.getDecoder().decode(base64Image);

        ByteArrayResource resource = new ByteArrayResource(data);

        Media imageMedia = new Media(MimeTypeUtils.IMAGE_JPEG, resource);

        UserMessage userMessage = UserMessage.builder()
                .media(imageMedia)
                .text(userInput)
                .build();

        return chatModel.call(userMessage);
    }

}
