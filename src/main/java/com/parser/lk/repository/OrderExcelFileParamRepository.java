package com.parser.lk.repository;

import com.parser.lk.dto.FileStatusEnum;
import com.parser.lk.entity.OrderExcelFileParam;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderExcelFileParamRepository extends CrudRepository<OrderExcelFileParam, Long> {

    public Iterable<OrderExcelFileParam> findByStatus(FileStatusEnum fileStatusEnum);

    public Integer countByStatus(FileStatusEnum fileStatusEnum);

    public Optional<OrderExcelFileParam> findOneByGuid(String guid);

}
