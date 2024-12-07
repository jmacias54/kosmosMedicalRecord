package com.mx.medical.internal.repository;

import com.mx.medical.internal.entity.ConsultingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultingRoomRepository extends JpaRepository<ConsultingRoom, Long> {
}
