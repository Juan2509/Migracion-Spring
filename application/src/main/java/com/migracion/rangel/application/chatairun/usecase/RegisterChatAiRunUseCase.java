package com.migracion.rangel.application.chatairun.usecase;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.domain.chatmessage.port.repository.ChatMessageRepository;
import com.migracion.rangel.application.chatmessage.exception.ChatMessageNotFoundApplicationException;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import com.migracion.rangel.domain.airunstatus.port.repository.AiRunStatusRepository;
import com.migracion.rangel.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.application.chatairun.dto.ChatAiRunResponse;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import com.migracion.rangel.application.chatairun.command.RegisterChatAiRunCommand;
import com.migracion.rangel.domain.chatairun.model.aggregate.ChatAiRun;
public class RegisterChatAiRunUseCase {
    private final ChatAiRunRepository repository;
    private final ChatConversationRepository conversations;
    private final ChatMessageRepository messages;
    private final AiModelRepository models;
    private final AiRunStatusRepository statuses;

    public RegisterChatAiRunUseCase(ChatAiRunRepository repository, ChatConversationRepository conversations, ChatMessageRepository messages, AiModelRepository models, AiRunStatusRepository statuses) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.conversations = java.util.Objects.requireNonNull(conversations);
        this.messages = java.util.Objects.requireNonNull(messages);
        this.models = java.util.Objects.requireNonNull(models);
        this.statuses = java.util.Objects.requireNonNull(statuses);
    }
    public ChatAiRunResponse execute(RegisterChatAiRunCommand command) {
        var aggregate = ChatAiRun.register(command.conversationId(), command.messageId(), command.modelId(), command.aiRunStatusId());
        conversations.findById(command.conversationId()).orElseThrow(() -> new ChatConversationNotFoundApplicationException(command.conversationId()));
        messages.findById(command.messageId()).orElseThrow(() -> new ChatMessageNotFoundApplicationException(command.messageId()));
        models.findById(command.modelId()).orElseThrow(() -> new AiModelNotFoundApplicationException(command.modelId()));
        statuses.findById(command.aiRunStatusId()).orElseThrow(() -> new AiRunStatusNotFoundApplicationException(command.aiRunStatusId()));
        return ChatAiRunResponse.from(repository.save(aggregate));
    }
}

