package softuniblog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniblog.demo.entity.ModelPhone;

public interface ModelPhoneRepository
extends JpaRepository<ModelPhone,Integer>{
}
