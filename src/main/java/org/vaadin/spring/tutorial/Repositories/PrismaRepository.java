package org.vaadin.spring.tutorial.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.vaadin.spring.tutorial.Models.PrismaChartDocumentsOnPrinters;
import org.vaadin.spring.tutorial.Models.PrismaEntry;

import java.util.List;

@Service
public interface PrismaRepository extends JpaRepository<PrismaEntry, Integer> {
    @Query("SELECT DISTINCT year FROM tblPrismaEntry")
    List<String> findAllYears();

    @Query("SELECT " + "new org.vaadin.spring.tutorial.Models.PrismaChartDocumentsOnPrinters(t.year, t.printer, COUNT(t.documentsCount))" + " FROM tblPrismaEntry t GROUP BY t.printer")
    List<PrismaChartDocumentsOnPrinters> getChartData();
}
