package com.demo.persistencia.demopersistencia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.persistencia.demopersistencia.entidades.User;

public interface UserRepositoy extends JpaRepository<User, Long> {User findByEmail(String email);
 
}
