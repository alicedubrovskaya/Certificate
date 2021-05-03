package com.epam.esm.service.converter;

public interface DtoConverter<Entity, DtoEntity> {
    DtoEntity convertToDto(Entity entity);

    Entity convertToEntity(DtoEntity dtoEntity);
}
