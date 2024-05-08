package kz.iitu.iitu.repository;

import kz.iitu.iitu.entity.CreditCard;
import kz.iitu.iitu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<CreditCard> findCreditCardsById(Long id);
}
