package com.migracion.rangel.infrastructure.relationshiptype.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.relationshiptype.port.repository.RelationshipTypeRepository;
import com.migracion.rangel.infrastructure.relationshiptype.adapters.out.persistence.repositories.RelationshipTypeJpaRepository;
import com.migracion.rangel.infrastructure.relationshiptype.adapters.out.persistence.repositories.RelationshipTypeRepositoryAdapter;
import com.migracion.rangel.infrastructure.relationshiptype.adapters.out.persistence.mappers.RelationshipTypePersistenceMapper;
import com.migracion.rangel.application.relationshiptype.usecase.RegisterRelationshipTypeUseCase;
import com.migracion.rangel.application.relationshiptype.usecase.GetRelationshipTypeByIdUseCase;
import com.migracion.rangel.application.relationshiptype.usecase.ListRelationshipTypeUseCase;
import com.migracion.rangel.application.relationshiptype.usecase.UpdateRelationshipTypeUseCase;
import com.migracion.rangel.application.relationshiptype.usecase.DeleteRelationshipTypeUseCase;
@Configuration
public class RelationshipTypeBeansConfig {
    @Bean
    public RelationshipTypePersistenceMapper relationshiptypePersistenceMapper() { return new RelationshipTypePersistenceMapper(); }
    @Bean
    public RelationshipTypeRepository relationshiptypeRepository(RelationshipTypeJpaRepository repository, RelationshipTypePersistenceMapper mapper) {
        return new RelationshipTypeRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterRelationshipTypeUseCase registerRelationshipTypeUseCase(RelationshipTypeRepository repository) {
        return new RegisterRelationshipTypeUseCase(repository);
    }
    @Bean
    public GetRelationshipTypeByIdUseCase getRelationshipTypeByIdUseCase(RelationshipTypeRepository repository) {
        return new GetRelationshipTypeByIdUseCase(repository);
    }
    @Bean
    public ListRelationshipTypeUseCase listRelationshipTypeUseCase(RelationshipTypeRepository repository) {
        return new ListRelationshipTypeUseCase(repository);
    }
    @Bean
    public UpdateRelationshipTypeUseCase updateRelationshipTypeUseCase(RelationshipTypeRepository repository) {
        return new UpdateRelationshipTypeUseCase(repository);
    }
    @Bean
    public DeleteRelationshipTypeUseCase deleteRelationshipTypeUseCase(RelationshipTypeRepository repository) {
        return new DeleteRelationshipTypeUseCase(repository);
    }
}
