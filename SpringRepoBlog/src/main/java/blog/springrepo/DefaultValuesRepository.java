package blog.springrepo;

import blog.springrepo.entity.DefaultValues;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DefaultValuesRepository extends JpaRepository<DefaultValues, Long> {
    @Query(value = "select distinct switchOff from DefaultValues")
    List<String> getDistinctSwitchOff();
}