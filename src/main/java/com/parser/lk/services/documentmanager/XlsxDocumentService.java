package com.parser.lk.services.documentmanager;

import com.parser.lk.dto.FileStatusEnum;
import com.parser.lk.entity.OrderExcelFileParam;
import com.parser.lk.entity.Vacancy;
import com.parser.lk.repository.OrderExcelFileParamRepository;
import com.parser.lk.repository.VacancyRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.parser.lk.entity.Order;
import com.parser.lk.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
public class XlsxDocumentService {

    private final VacancyRepository vacancyRepository;


    private final OrderRepository orderRepository;

    private final ResourceLoader resourceLoader;

    private final OrderExcelFileParamRepository orderExcelFileParamRepository;


    private final Logger logger = LoggerFactory.getLogger(XlsxDocumentService.class);

    @Value("${application.fileoutput.path}")
    private String outputPath;

    public XlsxDocumentService(VacancyRepository vacancyRepository, OrderRepository orderRepository, ResourceLoader resourceLoader, OrderExcelFileParamRepository orderExcelFileParamRepository) {
        this.vacancyRepository = vacancyRepository;
        this.orderRepository = orderRepository;
        this.resourceLoader = resourceLoader;
        this.orderExcelFileParamRepository = orderExcelFileParamRepository;
    }

    public void createXlsxDocumentByOrderId(String guid) {
        Optional<Order> orderOptional = this.orderRepository.findOneByGuid(guid);
        if (orderOptional.isEmpty()) {
            this.logger.error(String.format("order by id %s not found", guid));
            return;
        }
        Order order = orderOptional.get();

        int count = this.vacancyRepository.countByGuidAndProcessed(order.getGuid(), true);

        if (count < 1) {
            this.logger.error(String.format("vacancies by order not found (orderId:%s|guid:%s)", order.getId(), order.getGuid()));
            return;
        }

        if (!this.createExcelFile(order.getGuid())) {
            return;
        }

        OrderExcelFileParam fileParam = new OrderExcelFileParam();
        fileParam.setGuid(guid);
        fileParam.setFilename(guid + ".xlsx");
        fileParam.setStatus(FileStatusEnum.EMPTY);
        this.orderExcelFileParamRepository.save(fileParam);

        int currentPage = 0;
        while (true) {
            Pageable pageable = PageRequest.of(currentPage, 100);
            Page<Vacancy> vacanciesPage = vacancyRepository.findAllByGuidAndProcessed(order.getGuid(), true, pageable);
            if (vacanciesPage.isEmpty()) {
                break;
            }
            currentPage++;
            for (Vacancy vacancy : vacanciesPage.getContent()) {
                this.writeExcelFile(order.getGuid(), vacancy);
            }
        }
        //TODO нет смысла в методе так как подсчет идет в другом сервисе и храниться в бд
        //this.calculateFormula(order.getGuid());
        fileParam.setStatus(FileStatusEnum.READY);
        this.orderExcelFileParamRepository.save(fileParam);

    }


    //TODO метод не нужен так как данные считается в другом сервисе
    @Deprecated
    private void calculateFormula(String guid) {
        String filePath = String.format(
                "%s/%s.xlsx",
                this.outputPath,
                guid
        );

        try (FileInputStream fileIn = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileIn)) {

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();

            Row row = sheet.getRow(1);

            row.createCell(22).setCellValue("от");

            Cell cell0 = row.createCell(23);
            cell0.setCellFormula(String.format("MEDIAN(N2:N%s)", lastRowNum+1));

            Cell cell1 = row.createCell(24);
            cell1.setCellFormula(String.format("AVERAGE(N2:N%s)", lastRowNum+1));

            row = sheet.getRow(2);

            row.createCell(22).setCellValue("до");

            cell0 = row.createCell(23);
            cell0.setCellFormula(String.format("MEDIAN(O2:O%s)", lastRowNum+1));

            cell1 = row.createCell(24);
            cell1.setCellFormula(String.format("AVERAGE(O2:O%s)", lastRowNum+1));


            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                this.logger.info("Calculate finish");
            } catch (IOException e) {
                this.logger.error(e.toString());
            }

        } catch (IOException e) {
            this.logger.error(e.toString());
        }

    }

    private void writeExcelFile(String guid, Vacancy vacancy) {
        String filePath = String.format(
                "%s/%s.xlsx",
                this.outputPath,
                guid
        );

        try (FileInputStream fileIn = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileIn)) {

            Sheet sheet = workbook.getSheetAt(0); // Получаем первый лист
            int lastRowNum = sheet.getLastRowNum(); // Получаем номер последней строки

            Row row = sheet.createRow(lastRowNum + 1); // Создаем новую строку

            row.createCell(0).setCellValue(vacancy.getId());
            row.createCell(1).setCellValue("в работе");
            row.createCell(2).setCellValue(vacancy.getArea());
            row.createCell(3).setCellValue("в работе");
            row.createCell(4).setCellValue(vacancy.getExperience());
            row.createCell(5).setCellValue(vacancy.getGrade());
            row.createCell(6).setCellValue("в работе");
            row.createCell(7).setCellValue(vacancy.getSchedule());
            row.createCell(8).setCellValue("Тип занятости");
            row.createCell(9).setCellValue(vacancy.getEmployment());
            row.createCell(10).setCellValue(vacancy.getName());
            row.createCell(11).setCellValue(vacancy.getVacancyDescription());
            row.createCell(12).setCellValue(vacancy.getFunctionalDescription());
            row.createCell(13).setCellValue(vacancy.getSalaryFrom());
            row.createCell(14).setCellValue(vacancy.getSalaryTo());
            row.createCell(15).setCellValue(vacancy.getSalaryGross());
            row.createCell(16).setCellValue(vacancy.getCurrency());
            row.createCell(17).setCellValue(vacancy.getOriginalUrl());
            row.createCell(18).setCellValue(vacancy.getExternalId());

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                this.logger.info(String.format("Vacancy save in xlsx file (vacancy id:%s)", vacancy.getId()));
            } catch (IOException e) {
                this.logger.error("Error while save vacancy \n " + e.toString());
            }

        } catch (IOException e) {
            this.logger.error(e.toString());
        }
    }


    private boolean createExcelFile(String guid) {

        String filePath = String.format(
                "%s/%s.xlsx",
                this.outputPath,
                guid
        );

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Вакансии");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Регион");
            headerRow.createCell(2).setCellValue("Регион (ID)");
            headerRow.createCell(3).setCellValue("Опыт работы");
            headerRow.createCell(4).setCellValue("Опыт работы (Alias)");
            headerRow.createCell(5).setCellValue("Грейд");
            headerRow.createCell(6).setCellValue("График работы");
            headerRow.createCell(7).setCellValue("График работы (Alias)");
            headerRow.createCell(8).setCellValue("Тип занятости");
            headerRow.createCell(9).setCellValue("Тип занятости (Alias)");
            headerRow.createCell(10).setCellValue("Название вакансии");
            headerRow.createCell(11).setCellValue("Описание вакансии");
            headerRow.createCell(12).setCellValue("Функциональное описание вакансии");
            headerRow.createCell(13).setCellValue("Зарплата от");
            headerRow.createCell(14).setCellValue("Зарплата до");
            headerRow.createCell(15).setCellValue("До вычета налога (bool)");
            headerRow.createCell(16).setCellValue("Курс");
            headerRow.createCell(17).setCellValue("Ссылка на вакансию");
            headerRow.createCell(18).setCellValue("ID HH вакансии");
            headerRow.createCell(20).setCellValue("GUID:");
            headerRow.createCell(21).setCellValue(guid);


            headerRow.createCell(23).setCellValue("Медиана зарплата");
            headerRow.createCell(24).setCellValue("Средняя зарплата");

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            this.logger.error(String.format("Error while creating XLSX file (guid:%s) \n %s", guid, e));
            return false;
        }
        this.logger.info(String.format("File with name %s.xlsx created", guid));
        return true;
    }

}
