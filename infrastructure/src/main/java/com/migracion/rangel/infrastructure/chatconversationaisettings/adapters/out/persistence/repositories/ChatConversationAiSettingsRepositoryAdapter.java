package com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.migracion.rangel.domain.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;
import com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.out.persistence.mappers.ChatConversationAiSettingsPersistenceMapper;
public class ChatConversationAiSettingsRepositoryAdapter implements ChatConversationAiSettingsRepository {
    private final ChatConversationAiSettingsJpaRepository repository;
    private final ChatConversationAiSettingsPersistenceMapper mapper;
    public ChatConversationAiSettingsRepositoryAdapter(ChatConversationAiSettingsJpaRepository repository, ChatConversationAiSettingsPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ChatConversationAiSettings save(ChatConversationAiSettings aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<ChatConversationAiSettings> findById(ChatConversationAiSettingsId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<ChatConversationAiSettings> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(ChatConversationAiSettings aggregate) { repository.deleteById(aggregate.id().value()); }
}

