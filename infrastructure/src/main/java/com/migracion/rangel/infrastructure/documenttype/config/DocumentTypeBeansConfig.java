package com.migracion.rangel.infrastructure.documenttype.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.infrastructure.documenttype.adapters.out.persistence.repositories.DocumentTypeJpaRepository;
import com.migracion.rangel.infrastructure.documenttype.adapters.out.persistence.repositories.DocumentTypeRepositoryAdapter;
import com.migracion.rangel.infrastructure.documenttype.adapters.out.persistence.mappers.DocumentTypePersistenceMapper;
import com.migracion.rangel.application.documenttype.usecase.RegisterDocumentTypeUseCase;
import com.migracion.rangel.application.documenttype.usecase.GetDocumentTypeByIdUseCase;
import com.migracion.rangel.application.documenttype.usecase.ListDocumentTypeUseCase;
import com.migracion.rangel.application.documenttype.usecase.UpdateDocumentTypeUseCase;
import com.migracion.rangel.application.documenttype.usecase.DeleteDocumentTypeUseCase;
@Configuration
public class DocumentTypeBeansConfig {
    @Bean
    public DocumentTypePersistenceMapper documentTypePersistenceMapper() { return new DocumentTypePersistenceMapper(); }
    @Bean
    public DocumentTypeRepository documentTypeRepository(DocumentTypeJpaRepository repository, DocumentTypePersistenceMapper mapper) {
        return new DocumentTypeRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterDocumentTypeUseCase registerDocumentTypeUseCase(DocumentTypeRepository repository) {
        return new RegisterDocumentTypeUseCase(repository);
    }
    @Bean
    public GetDocumentTypeByIdUseCase getDocumentTypeByIdUseCase(DocumentTypeRepository repository) {
        return new GetDocumentTypeByIdUseCase(repository);
    }
    @Bean
    public ListDocumentTypeUseCase listDocumentTypeUseCase(DocumentTypeRepository repository) {
        return new ListDocumentTypeUseCase(repository);
    }
    @Bean
    public UpdateDocumentTypeUseCase updateDocumentTypeUseCase(DocumentTypeRepository repository) {
        return new UpdateDocumentTypeUseCase(repository);
    }
    @Bean
    public DeleteDocumentTypeUseCase deleteDocumentTypeUseCase(DocumentTypeRepository repository) {
        return new DeleteDocumentTypeUseCase(repository);
    }
}
