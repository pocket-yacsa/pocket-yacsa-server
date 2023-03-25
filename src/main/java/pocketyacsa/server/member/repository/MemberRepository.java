package pocketyacsa.server.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pocketyacsa.server.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

  public boolean existsMemberByEmailAndDeletedFalse(String Email);

  Optional<Member> findByEmailAndDeletedFalse(String email);

  @Modifying
  @Query("UPDATE Member m SET m.deleted = true, m.updatedAt = current_timestamp WHERE m.id = :id")
  void delete(@Param("id") int id);
}
