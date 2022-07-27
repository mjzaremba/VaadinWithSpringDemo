package org.vaadin.spring.tutorial.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PrismaChartDocumentsOnPrinters {
    private String year;
    private String printer;
    private Long documentsPrinted;
}
