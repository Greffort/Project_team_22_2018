package com.team22.project_team_22_2018.server.repository;

import com.team22.project_team_22_2018.server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * created by Greffort 10.05.2019
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    //метод поиска объекта по UNAME.
    Users findByName(String name);
}
