package jp.kobespiral.matsuba.todo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.matsuba.todo.entity.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, String>{
    List<Member> findAll();
}