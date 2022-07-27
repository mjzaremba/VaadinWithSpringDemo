package org.vaadin.spring.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.spring.tutorial.Models.PrismaChartDocumentsOnPrinters;
import org.vaadin.spring.tutorial.Models.PrismaEntry;
import org.vaadin.spring.tutorial.Repositories.PrismaRepository;

import java.util.List;

@Service
public class PrismaService {
    private final PrismaRepository prismaRepository;

    @Autowired
    public PrismaService(PrismaRepository prismaRepository) {
        this.prismaRepository = prismaRepository;
    }

    List<PrismaEntry> findAll() {
        return prismaRepository.findAll();
    }

    void save(PrismaEntry prismaEntry) {
        prismaRepository.save(prismaEntry);
    }

    void saveAll(List<PrismaEntry> list) {
        prismaRepository.saveAll(list);
    }

    void clearDatabase() {
        prismaRepository.deleteAll();
    }

    List<String> getAllYears() {
        return prismaRepository.findAllYears();
    }

    List<PrismaChartDocumentsOnPrinters> getChartData() {
        return prismaRepository.getChartData();
    }
}
