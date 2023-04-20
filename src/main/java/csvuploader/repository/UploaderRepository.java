package csvuploader.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import csvuploader.model.DataModel;

@Repository
public interface UploaderRepository extends JpaRepository<DataModel, String> {
    Optional<DataModel> findByCode(String code);
}
