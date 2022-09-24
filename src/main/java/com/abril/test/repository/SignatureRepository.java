package com.abril.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abril.test.domain.Signature;


@Repository
public interface SignatureRepository extends JpaRepository<Signature, Long>  {

	@Query("SELECT s FROM Signature s JOIN SignatureAddress sa ON s.id=sa.signature.id JOIN Product p on s.product.id=p.id where cep=:cep and p.name=:product")
	Optional<List<Signature>> findByCepAndProduct(@Param("cep") Long cep, @Param("product") String product);
	
	
}