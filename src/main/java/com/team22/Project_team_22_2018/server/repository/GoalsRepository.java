package com.team22.project_team_22_2018.server.repository;

import com.team22.project_team_22_2018.server.entity.Goals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * created by Greffort 10.05.2019
 */
@Repository
public interface GoalsRepository extends JpaRepository<Goals, String> {
}
