package com.max.repositories;

import com.max.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mbondarenko on 14.03.14.
 */
public interface RecordRepository extends JpaRepository<Record, Integer> {

}
