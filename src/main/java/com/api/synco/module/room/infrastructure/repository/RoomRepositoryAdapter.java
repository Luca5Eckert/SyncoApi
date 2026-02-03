package com.api.synco.module.room.infrastructure.repository;

import com.api.synco.module.room.domain.RoomEntity;
import com.api.synco.module.room.domain.filter.RoomFilter;
import com.api.synco.module.room.domain.filter.RoomPage;
import com.api.synco.module.room.domain.port.RoomRepository;
import com.api.synco.module.room.infrastructure.specification.RoomFilterProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoomRepositoryAdapter implements RoomRepository {

    private final RoomRepositoryJpa roomRepositoryJpa;

    private final RoomFilterProvider roomFilterProvider;

    public RoomRepositoryAdapter(RoomRepositoryJpa roomRepositoryJpa, RoomFilterProvider roomFilterProvider) {
        this.roomRepositoryJpa = roomRepositoryJpa;
        this.roomFilterProvider = roomFilterProvider;
    }

    @Override
    public boolean existByNumber(int number) {
        return roomRepositoryJpa.existsByNumber(number);
    }

    @Override
    public void save(RoomEntity room) {
        roomRepositoryJpa.save(room);
    }

    @Override
    public void deleteById(long roomId) {
        roomRepositoryJpa.deleteById(roomId);
    }

    @Override
    public Optional<RoomEntity> findById(long roomId) {
        return roomRepositoryJpa.findById(roomId);
    }

    @Override
    public boolean existsById(long roomId) {
        return roomRepositoryJpa.existsById(roomId);
    }

    @Override
    public Page<RoomEntity> findAll(RoomFilter roomFilter, RoomPage roomPage) {
        Specification<RoomEntity> specificationRoom = roomFilterProvider.of(roomFilter);

        PageRequest pageRequest = PageRequest.of(
                roomPage.pageNumber(),
                roomPage.pageSize()
        );

        return roomRepositoryJpa.findAll(specificationRoom, pageRequest);
    }

}
