package com.pupr.Master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedCollectionRepo extends JpaRepository<DeletedCollection, Long> {

}